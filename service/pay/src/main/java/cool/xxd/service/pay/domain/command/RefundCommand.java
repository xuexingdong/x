package cool.xxd.service.pay.domain.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundCommand extends BaseCommand {
    // 原支付单对应的商户订单号，与payOrderNo二选一
    private String outTradeNo;
    // 原支付单对应的支付中心订单号，与outTradeNo二选一，优先取值payOrderNo
    private String payOrderNo;
    // 商户退款单号
    private String outRefundNo;
    // 退款金额，正数，单位为元，两位小数
    private BigDecimal refundAmount;
    private String refundReason;
    private String notifyUrl;
}
