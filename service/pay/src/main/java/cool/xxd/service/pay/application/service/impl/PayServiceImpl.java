package cool.xxd.service.pay.application.service.impl;

import cool.xxd.infra.enums.CommonEnum;
import cool.xxd.infra.lock.LockTemplate;
import cool.xxd.infra.page.PageRequest;
import cool.xxd.service.pay.application.service.PayService;
import cool.xxd.service.pay.domain.aggregate.*;
import cool.xxd.service.pay.domain.command.CloseCommand;
import cool.xxd.service.pay.domain.command.PayCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.domainservice.PayDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import cool.xxd.service.pay.domain.exceptions.PayException;
import cool.xxd.service.pay.domain.factory.PayOrderFactory;
import cool.xxd.service.pay.domain.query.PayOrderQuery;
import cool.xxd.service.pay.domain.repository.*;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategyFactory;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private static final int QUERY_SIZE = 50;
    private final AppRepository appRepository;
    private final MerchantRepository merchantRepository;
    private final PayOrderRepository payOrderRepository;
    private final LockTemplate lockTemplate;
    private final PayOrderFactory payOrderFactory;
    private final PayChannelStrategyFactory payChannelStrategyFactory;
    private final PayChannelRepository payChannelRepository;
    private final MerchantPayChannelRepository merchantPayChannelRepository;
    private final PayDomainService payDomainService;

    @Override
    public Long pay(PayCommand payCommand) {
        validatePayCommand(payCommand);
        var merchant = merchantRepository.findByMchid(payCommand.getMchid())
                .orElseThrow(() -> new PayException("商户不存在"));
        var app = appRepository.findByAppid(merchant.getMchid(), payCommand.getAppid())
                .orElseThrow(() -> new PayException("应用不存在"));
        var payType = getPayType(payCommand);
        var merchantPayChannel = payChannelStrategyFactory.routePayChannel(payCommand.getMchid(), payType.getCode());
        var payChannel = payChannelRepository.findByCode(merchantPayChannel.getPayChannelCode())
                .orElseThrow(() -> new PayException("商户支付渠道不存在"));
        var payOrder = createPayOrder(merchant, app, payType, payChannel, merchantPayChannel, payCommand);
        payDomainService.startPay(payOrder);
        PayResult payResult;
        try {
            var payChannelStrategy = payChannelStrategyFactory.getStrategy(payOrder.getPayChannelCode());
            payResult = payChannelStrategy.pay(merchantPayChannel, payOrder);
        } catch (Exception e) {
            log.error("支付失败, 支付单号-{}", payOrder.getPayOrderNo(), e);
            // TODO 如果更新订单本身这一步失败，或者没有执行，得有监控或者次日对账同步补偿
            payDomainService.fail(payOrder);
            return payOrder.getId();
        }
        updatePayResult(payOrder.getMchid(), payOrder.getId(), payResult);
        return payOrder.getId();
    }

    @Override
    public void syncPayStatus() {
        var merchants = merchantRepository.findAll();
        var now = LocalDateTime.now();
        merchants.forEach(merchant -> {
            var payOrderQuery = new PayOrderQuery();
            payOrderQuery.setMchid(merchant.getMchid());
            payOrderQuery.setPayStatusList(List.of(PayStatusEnum.PAYING));
            // 筛选出过期时间大于等于现在的，即未过期
            payOrderQuery.setFromTimeExpire(now);
            // 开始轮询时间小于当前时间，即现在需要开始轮询了
            payOrderQuery.setToPollingStartTime(now);
            var payOrderPageResult = payOrderRepository.query(payOrderQuery, PageRequest.of(1, QUERY_SIZE));
            var payOrders = payOrderPageResult.getData();
            if (!payOrders.isEmpty()) {
                for (var payOrder : payOrders) {
                    try {
                        var merchantPayChannel = merchantPayChannelRepository.findByMchidAndPayChannelCode(payOrder.getMchid(), payOrder.getPayChannelCode())
                                .orElseThrow(() -> new PayException("商户支付渠道不存在"));
                        var payChannelStrategy = payChannelStrategyFactory.getStrategy(merchantPayChannel.getPayChannelCode());
                        var payResult = payChannelStrategy.query(merchantPayChannel, payOrder);
                        updatePayResult(payOrder.getMchid(), payOrder.getId(), payResult);
                    } catch (Exception e) {
                        log.error("查询支付单异常，mchid-{}, id-{}", payOrder.getMchid(), payOrder.getId(), e);
                    }
                }
            }
        });
    }

    @Override
    public void close(CloseCommand closeCommand) {
        var payOrder = payOrderRepository.findByPayOrderNoAndOutTradeNo(closeCommand.getAppid(), closeCommand.getPayOrderNo(), closeCommand.getOutTradeNo())
                .orElseThrow(() -> new PayException("支付单不存在"));
        var merchantPayChannel = merchantPayChannelRepository.findByMchidAndPayChannelCode(payOrder.getMchid(), payOrder.getPayChannelCode())
                .orElseThrow(() -> new PayException("商户支付渠道不存在"));
        var payChannelStrategy = payChannelStrategyFactory.getStrategy(merchantPayChannel.getPayChannelCode());
        payChannelStrategy.close(merchantPayChannel, payOrder);
        payDomainService.close(payOrder);
    }

    @Override
    public void batchExpire() {
        var now = LocalDateTime.now();
        var payOrderQuery = new PayOrderQuery();
        payOrderQuery.setPayStatusList(List.of(PayStatusEnum.UNPAID, PayStatusEnum.PAYING));
        // 筛选出过期时间小于等于现在的，即已过期
        payOrderQuery.setToTimeExpire(now);
        var payOrders = payOrderRepository.query(payOrderQuery);
        for (var payOrder : payOrders) {
            payDomainService.close(payOrder);
        }
    }

    @Override
    public void timeout() {
        var payOrderQuery = new PayOrderQuery();
        payOrderQuery.setFromTimeExpire();
        payOrderRepository.query(payOrderQuery)
    }

    private PayTypeEnum getPayType(PayCommand payCommand) {
        PayTypeEnum payTypeEnum;
        // 扫码付优先级最高，后台自动判断支付方式
        if (payCommand.getTransMode() == TransModeEnum.PASSIVE_SCAN) {
            payTypeEnum = payDomainService.getPayType(payCommand.getAuthCode());
        } else if (payCommand.getPayTypeCode() != null && !payCommand.getPayTypeCode().isEmpty()) {
            var payTypeCode = payCommand.getPayTypeCode();
            payTypeEnum = CommonEnum.of(PayTypeEnum.class, payTypeCode);
        } else {
            throw new PayException("未知的支付方式");
        }
        return payTypeEnum;
    }

    private PayOrder createPayOrder(
            Merchant merchant,
            App app,
            PayTypeEnum payType,
            PayChannel payChannel,
            MerchantPayChannel merchantPayChannel,
            PayCommand payCommand
    ) {
        var lockKey = String.format(CacheKeys.MERCHANT_PAY_LOCK, payCommand.getMchid(), payCommand.getOutTradeNo());
        return lockTemplate.execute(lockKey, () -> {
            payOrderRepository.findByOutTradeNo(payCommand.getMchid(), payCommand.getOutTradeNo())
                    .ifPresent(payOrder -> {
                        throw new PayException("商户订单号不可重复");
                    });
            // TODO 对subOpenId加锁，避免短时间重复下单
            var payOrder = payOrderFactory.create(merchant, app, payType, payChannel, merchantPayChannel, payCommand);
            payOrderRepository.save(payOrder);
            return payOrder;
        });
    }

    @Override
    public void updatePayResult(String mchid, String payOrderNo, PayResult payResult) {
        payOrderRepository.findByPayOrderNo(mchid, payOrderNo)
                .ifPresent(payOrder -> updatePayResult(mchid, payOrder.getId(), payResult));
    }

    @Override
    public void updatePayResult(String mchid, Long payOrderId, PayResult payResult) {
        var lockKey = String.format(CacheKeys.PAY_ORDER_UPDATE_LOCK, payOrderId);
        lockTemplate.executeWithoutResult(lockKey, () -> payDomainService.updatePayResult(mchid, payOrderId, payResult));
    }

    private void validatePayCommand(PayCommand payCommand) {
        if (payCommand.getOutTradeNo() == null) {
            throw new PayException("商户订单号不能为空");
        }
        if (payCommand.getOutTradeNo().length() < 6
                || payCommand.getOutTradeNo().length() > 32) {
            throw new PayException("商户订单号长度不符合规范");
        }
        if (payCommand.getTotalAmount() == null || payCommand.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PayException("订单总金额必须大于0");
        }
        if (payCommand.getPayTypeCode() == null || payCommand.getPayTypeCode().isEmpty()) {
            throw new PayException("支付方式编码不能为空");
        }
        if (payCommand.getTransMode() == null) {
            throw new PayException("支付模式不能为空");
        }
        if (payCommand.getExpireMinutes() != null && payCommand.getExpireMinutes() <= 0) {
            throw new PayException("超时分钟数必须大于0");
        }
        if (payCommand.getSubject() == null || payCommand.getSubject().isEmpty()) {
            throw new PayException("订单标题不能为空");
        }
        if (payCommand.getSubject().length() > 128) {
            throw new PayException("订单标题长度不能超过128");
        }
        if (payCommand.getDescription() != null && payCommand.getDescription().length() > 256) {
            throw new PayException("订单描述长度不能超过256");
        }
        if (payCommand.getGoodsDetail() != null && payCommand.getGoodsDetail().length() > 1024) {
            throw new PayException("商品详情长度不能超过1024");
        }
        if (payCommand.getCustomData() != null && payCommand.getCustomData().length() > 256) {
            throw new PayException("自定义数据长度不能超过256");
        }
    }
}
