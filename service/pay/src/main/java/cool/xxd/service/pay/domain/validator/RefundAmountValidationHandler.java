package cool.xxd.service.pay.domain.validator;

import cool.xxd.infra.exceptions.BusinessException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class RefundAmountValidationHandler implements ValidationHandler {
    private ValidationHandler next;

    @Override
    public void setNext(ValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(RefundValidateContext refundValidateContext) {
        var refundCommand = refundValidateContext.getRefundCommand();
        var payOrder = refundValidateContext.getPayOrder();
        if (refundCommand.getRefundAmount().compareTo(payOrder.getRefundableAmount()) > 0) {
            throw new BusinessException("退款金额超过原支付单可退金额");
        }
        if (next != null) {
            next.handle(refundValidateContext);
        }
    }
}