package cool.xxd.service.pay.domain.validator;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.repository.RefundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class RefundStatusValidationHandler implements ValidationHandler {
    private ValidationHandler next;

    private final RefundOrderRepository refundOrderRepository;

    @Override
    public void setNext(ValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(RefundValidateContext refundValidateContext) {
        var payOrder = refundValidateContext.getPayOrder();
        var refundOrders = refundOrderRepository.findByPayOrderNo(payOrder.getPayOrderNo());
        if (refundOrders.stream()
                .anyMatch(refundOrder -> !refundOrder.isFinalRefundStatus())) {
            throw new BusinessException("一笔支付单同时只能有一个退款中的退订单");
        }
        if (next != null) {
            next.handle(refundValidateContext);
        }
    }
}