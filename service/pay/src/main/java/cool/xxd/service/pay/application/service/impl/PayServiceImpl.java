package cool.xxd.service.pay.application.service.impl;

import cool.xxd.infra.enums.CommonEnum;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.infra.lock.LockTemplate;
import cool.xxd.service.pay.application.service.PayService;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.command.CloseCommand;
import cool.xxd.service.pay.domain.command.PayCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.domainservice.PayDomainService;
import cool.xxd.service.pay.domain.enums.PayChannelEnum;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import cool.xxd.service.pay.domain.exceptions.PayException;
import cool.xxd.service.pay.domain.factory.PayOrderFactory;
import cool.xxd.service.pay.domain.query.PayOrderQuery;
import cool.xxd.service.pay.domain.repository.AppRepository;
import cool.xxd.service.pay.domain.repository.MerchantRepository;
import cool.xxd.service.pay.domain.repository.PayOrderRepository;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategyFactory;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final AppRepository appRepository;
    private final MerchantRepository merchantRepository;
    private final PayOrderRepository payOrderRepository;
    private final LockTemplate lockTemplate;
    private final PayOrderFactory payOrderFactory;
    private final PayChannelStrategyFactory payChannelStrategyFactory;
    private final PayDomainService payDomainService;

    @Override
    public Long pay(PayCommand payCommand) {
        validatePayCommand(payCommand);
        var app = appRepository.getByAppid(payCommand.getAppid());
        var merchant = merchantRepository.findByAppidAndMchid(payCommand.getAppid(), payCommand.getMchid())
                .orElseThrow(() -> new BusinessException("商户不存在"));
        var payType = getPayType(payCommand);
        var merchantPayChannel = payChannelStrategyFactory.routePayChannel(payCommand.getMchid(), payType.getCode());
        var payOrder = createPayOrder(payCommand, app, merchant, payType, CommonEnum.of(PayChannelEnum.class, merchantPayChannel.getPayChannelCode()));
        payDomainService.startPay(payOrder);
        PayResult payResult;
        try {
            var payChannelStrategy = payChannelStrategyFactory.getStrategy(payOrder.getPayChannelCode());
            payResult = payChannelStrategy.pay(app, merchantPayChannel, payOrder);
        } catch (Exception e) {
            log.error("支付失败, 支付单号-{}", payOrder.getPayOrderNo(), e);
            // TODO 如果更新订单本身这一步失败，或者没有执行，得有监控或者次日对账同步补偿
            payDomainService.fail(payOrder);
            return payOrder.getId();
        }
        updatePayResult(payOrder.getId(), payResult);
        return payOrder.getId();
    }

    @Override
    public void syncPayStatus() {
        var now = LocalDateTime.now();
        var payOrderQuery = new PayOrderQuery();
        payOrderQuery.setPayStatusList(List.of(PayStatusEnum.PAYING));
        // 筛选出过期时间大于等于现在的，即未过期
        payOrderQuery.setFromTimeExpire(now);
        // 开始轮询时间小于当前时间，即现在需要开始轮询了
        payOrderQuery.setToPollingStartTime(now);
        var payOrders = payOrderRepository.query(payOrderQuery);
        var appids = payOrders.stream()
                .map(PayOrder::getAppid)
                .distinct()
                .toList();
        var appMap = appRepository.findByAppids(appids).stream()
                .collect(Collectors.toMap(App::getAppid, Function.identity()));
        for (var payOrder : payOrders) {
            var app = appMap.get(payOrder.getAppid());
            try {
                var merchantPayChannel = payChannelStrategyFactory.routePayChannel(payOrder.getMchid(), payOrder.getPayTypeCode());
                var payChannelStrategy = payChannelStrategyFactory.getStrategy(payOrder.getPayChannelCode());
                var payResult = payChannelStrategy.query(app, merchantPayChannel, payOrder);
                updatePayResult(payOrder.getId(), payResult);
            } catch (Exception e) {
                log.error("查询支付单异常，id-{}", payOrder.getId(), e);
            }
        }
    }

    @Override
    public void close(CloseCommand closeCommand) {
        var payOrder = payOrderRepository.findByPayOrderNoAndOutTradeNo(closeCommand.getAppid(), closeCommand.getPayOrderNo(), closeCommand.getOutTradeNo())
                .orElseThrow(() -> new BusinessException("支付单不存在"));
        payDomainService.close(payOrder);
        var app = appRepository.getByAppid(payOrder.getAppid());
        var merchantPayChannel = payChannelStrategyFactory.routePayChannel(payOrder.getMchid(), payOrder.getPayTypeCode());
        var payChannelStrategy = payChannelStrategyFactory.getStrategy(payOrder.getPayChannelCode());
        payChannelStrategy.close(app, merchantPayChannel, payOrder);
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

    private PayTypeEnum getPayType(PayCommand payCommand) {
        PayTypeEnum payTypeEnum;
        // 扫码付优先级最高，后台自动判断支付方式
        if (payCommand.getTransMode() == TransModeEnum.PASSIVE_SCAN) {
            payTypeEnum = payDomainService.getPayType(payCommand.getAuthCode());
        } else if (StringUtils.isNotBlank(payCommand.getPayTypeCode())) {
            var payTypeCode = payCommand.getPayTypeCode();
            payTypeEnum = CommonEnum.of(PayTypeEnum.class, payTypeCode);
        } else {
            throw new BusinessException("未知的支付方式");
        }
        return payTypeEnum;
    }

    private PayOrder createPayOrder(PayCommand payCommand, App app, Merchant merchant, PayTypeEnum payType, PayChannelEnum payChannel) {
        var lockKey = String.format(CacheKeys.APP_PAY_LOCK, payCommand.getAppid(), payCommand.getOutTradeNo());
        return lockTemplate.execute(lockKey, () -> {
            payOrderRepository.findByOutTradeNo(payCommand.getAppid(), payCommand.getOutTradeNo())
                    .ifPresent(payOrder -> {
                        throw new BusinessException("商户订单号不可重复");
                    });
            // TODO 对subOpenId加锁，避免短时间重复下单
            var payOrder = payOrderFactory.create(app, merchant, payCommand, payType, payChannel);
            payOrderRepository.save(payOrder);
            return payOrder;
        });
    }

    @Override
    public void updatePayResult(String payOrderNo, PayResult payResult) {
        payOrderRepository.findByPayOrderNo(payOrderNo)
                .ifPresent(payOrder -> updatePayResult(payOrder.getId(), payResult));
    }

    @Override
    public void updatePayResult(Long payOrderId, PayResult payResult) {
        var lockKey = String.format(CacheKeys.PAY_ORDER_UPDATE_LOCK, payOrderId);
        lockTemplate.executeWithoutResult(lockKey, () -> payDomainService.updatePayResult(payOrderId, payResult));
    }

    private void validatePayCommand(PayCommand payCommand) {
        validateOutTradeNo(payCommand.getOutTradeNo());
    }

    private void validateOutTradeNo(String outTradeNo) {
        if (outTradeNo == null) {
            throw new PayException("商户订单号不能为空");
        }
        // TODO 优化校验器
        if (outTradeNo.length() < 6
                || outTradeNo.length() > 32) {
            throw new PayException("商户订单号长度不符合规范");
        }
    }
}
