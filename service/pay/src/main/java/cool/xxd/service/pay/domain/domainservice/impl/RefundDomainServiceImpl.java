package cool.xxd.service.pay.domain.domainservice.impl;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.domainservice.RefundDomainService;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.repository.PayOrderRepository;
import cool.xxd.service.pay.domain.repository.RefundOrderRepository;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundDomainServiceImpl implements RefundDomainService {

    private final PayOrderRepository payOrderRepository;
    private final RefundOrderRepository refundOrderRepository;

    @Override
    public void startRefund(RefundOrder refundOrder) {
        refundOrder.startRefund();
        refundOrderRepository.updateRefundResult(refundOrder, RefundStatusEnum.INIT);
    }

    @Override
    public void updateRefundResult(String mchid, Long refundOrderId, RefundResult refundResult) {
        refundOrderRepository.findByRefundOrderId(mchid, refundOrderId);
        var refundOrder = refundOrderRepository.getById(refundOrderId);
        if (refundOrder.isFinalRefundStatus()
                && refundOrder.getRefundStatus() == refundResult.getRefundStatus()) {
            log.info("退款单已完成，幂等处理");
            return;
        }
        refundOrder.handleRefundResult(refundResult);
        var updateSuccess = refundOrderRepository.updateRefundResult(refundOrder, RefundStatusEnum.PROCESSING);
        if (!updateSuccess) {
            log.error("更新退款单退款结果失败，退款单号-{}", refundOrder.getRefundOrderNo());
            throw new BusinessException("更新退款单退款结果失败");
        }
        if (refundResult.getRefundStatus() == RefundStatusEnum.SUCCESS) {
            payOrderRepository.findByPayOrderNo(refundOrder.getPayOrderNo())
                    .ifPresent(payOrder -> {
                        payOrder.refund(refundOrder.getRefundAmount());
                        payOrderRepository.updateRefundedAmount(payOrder);
                    });
        }
    }

    @Override
    public void fail(RefundOrder refundOrder) {
        refundOrder.fail();
        refundOrderRepository.updateRefundResult(refundOrder, RefundStatusEnum.PROCESSING);
    }
}
