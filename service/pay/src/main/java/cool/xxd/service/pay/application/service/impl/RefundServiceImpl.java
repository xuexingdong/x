package cool.xxd.service.pay.application.service.impl;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.infra.lock.LockTemplate;
import group.hckj.pay.application.service.RefundService;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.command.RefundCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.domainservice.RefundDomainService;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.factory.RefundOrderFactory;
import cool.xxd.service.pay.domain.query.RefundOrderQuery;
import cool.xxd.service.pay.domain.repository.AppRepository;
import cool.xxd.service.pay.domain.repository.PayOrderRepository;
import cool.xxd.service.pay.domain.repository.RefundOrderRepository;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategyFactory;
import cool.xxd.service.pay.domain.validator.RefundValidateContext;
import cool.xxd.service.pay.domain.validator.ValidationHandler;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final AppRepository appRepository;
    private final PayOrderRepository payOrderRepository;
    private final RefundOrderRepository refundOrderRepository;
    private final RefundOrderFactory refundOrderFactory;
    private final PayChannelStrategyFactory payChannelStrategyFactory;
    private final List<ValidationHandler> validationHandlerList;
    private final RefundDomainService refundDomainService;
    private final LockTemplate lockTemplate;

    @Override
    public Long refund(RefundCommand refundCommand) {
        var app = appRepository.getByAppid(refundCommand.getAppid());
        var payOrder = payOrderRepository.findByPayOrderNoAndOutTradeNo(refundCommand.getAppid(), refundCommand.getPayOrderNo(), refundCommand.getOutTradeNo()).orElseThrow(() -> new BusinessException("原支付单不存在"));
        // TODO 这个校验可能要加锁
        validateRefundCommand(refundCommand, payOrder);
        var refundOrder = refundOrderFactory.create(app, payOrder, refundCommand);
        refundOrderRepository.save(refundOrder);
        refundDomainService.startRefund(refundOrder);
        var merchantPayChannel = payChannelStrategyFactory.routePayChannel(refundOrder.getMchid(), refundOrder.getPayTypeCode());
        var payChannelStrategy = payChannelStrategyFactory.getStrategy(refundOrder.getPayChannelCode());
        RefundResult refundResult;
        try {
            refundResult = payChannelStrategy.refund(app, merchantPayChannel, payOrder, refundOrder);
        } catch (Exception e) {
            log.error("退款失败, 退款单号-{}", refundOrder.getRefundOrderNo(), e);
            refundDomainService.fail(refundOrder);
            return refundOrder.getId();
        }
        updateRefundResult(refundOrder.getId(), refundResult);
        return refundOrder.getId();
    }

    @Override
    public void syncRefundStatus() {
        var now = LocalDateTime.now();
        var refundOrderQuery = new RefundOrderQuery();
        refundOrderQuery.setRefundStatus(RefundStatusEnum.PROCESSING);
        refundOrderQuery.setToPollingStartTime(now);
        var refundOrders = refundOrderRepository.query(refundOrderQuery);
        var appids = refundOrders.stream()
                .map(RefundOrder::getAppid)
                .distinct()
                .toList();
        var appMap = appRepository.findByAppids(appids).stream()
                .collect(Collectors.toMap(App::getAppid, Function.identity()));
        for (var refundOrder : refundOrders) {
            var app = appMap.get(refundOrder.getAppid());
            try {
                var merchantPayChannel = payChannelStrategyFactory.routePayChannel(refundOrder.getMchid(), refundOrder.getPayTypeCode());
                var payChannelStrategy = payChannelStrategyFactory.getStrategy(refundOrder.getPayChannelCode());
                var refundResult = payChannelStrategy.queryRefund(app, merchantPayChannel, refundOrder);
                updateRefundResult(refundOrder.getId(), refundResult);
            } catch (Exception e) {
                log.error("查询退款单异常，id-{}", refundOrder.getId(), e);
            }
        }
    }

    @Override
    public void updateRefundResult(String refundOrderNo, RefundResult refundResult) {
        refundOrderRepository.findByRefundOrderNo(refundOrderNo)
                .ifPresent(refundOrder -> updateRefundResult(refundOrder.getId(), refundResult));
    }

    @Override
    public void updateRefundResult(Long refundOrderId, RefundResult refundResult) {
        var lockKey = String.format(CacheKeys.REFUND_ORDER_UPDATE_LOCK, refundOrderId);
        lockTemplate.executeWithoutResult(lockKey, () -> refundDomainService.updateRefundResult(refundOrderId, refundResult));
    }

    private void validateRefundCommand(RefundCommand refundCommand, PayOrder payOrder) {
        var refundValidateContext = new RefundValidateContext();
        refundValidateContext.setRefundCommand(refundCommand);
        refundValidateContext.setPayOrder(payOrder);
        for (var validationHandler : validationHandlerList) {
            validationHandler.handle(refundValidateContext);
        }
    }
}
