package cool.xxd.service.pay.domain.validator;

import cool.xxd.service.pay.domain.exceptions.PayException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RefundCommandValidationHandler implements ValidationHandler {
    private ValidationHandler next;

    @Override
    public void setNext(ValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(RefundValidateContext refundValidateContext) {
        var refundCommand = refundValidateContext.getRefundCommand();
        if (StringUtils.isAllBlank(refundCommand.getOutTradeNo(), refundCommand.getPayOrderNo())) {
            throw new PayException("商户订单号和支付中心订单号不能同时为空");
        }
        var outRefundNo = refundCommand.getOutRefundNo();
        if (StringUtils.isBlank(outRefundNo)) {
            throw new PayException("商户退款单号不能为空");
        }
        if (outRefundNo.length() < 6
                || outRefundNo.length() > 32) {
            throw new PayException("商户退款单号长度不符合规范");
        }
        if (next != null) {
            next.handle(refundValidateContext);
        }
    }
}