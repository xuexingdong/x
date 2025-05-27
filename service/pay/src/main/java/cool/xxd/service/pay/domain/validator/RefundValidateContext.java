package cool.xxd.service.pay.domain.validator;

import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.command.RefundCommand;
import lombok.Data;

@Data
public class RefundValidateContext {
    private RefundCommand refundCommand;
    private PayOrder payOrder;
}
