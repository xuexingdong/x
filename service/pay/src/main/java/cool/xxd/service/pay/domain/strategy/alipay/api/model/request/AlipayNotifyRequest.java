package cool.xxd.service.pay.domain.strategy.alipay.api.model.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AlipayNotifyRequest {
    // 通知时间
    private String notifyTime;

    // 通知类型
    private String notifyType;

    // 通知校验 ID
    private String notifyId;

    // 签名类型（RSA2 或 RSA）
    private String signType;

    // 签名
    private String sign;

    // 支付宝交易号
    private String tradeNo;

    // 开发者的 app_id
    private String appId;

    // 开发者的授权 app_id
    private String authAppId;

    // 商户订单号
    private String outTradeNo;

    // 商家业务号（退款通知中的退款流水号）
    private String outBizNo;

    // 买家支付宝用户号
    private String buyerId;

    // 买家支付宝账号
    private String buyerLogonId;

    // 卖家支付宝用户号
    private String sellerId;

    // 卖家支付宝账号
    private String sellerEmail;

    // 交易状态
    private String tradeStatus;

    // 订单金额（人民币元）
    private String totalAmount;

    // 实收金额（人民币元）
    private String receiptAmount;

    // 开票金额（人民币元）
    private String invoiceAmount;

    // 付款金额（人民币元）
    private String buyerPayAmount;

    // 集分宝金额（人民币元）
    private String pointAmount;

    // 总退款金额（人民币元）
    private String refundFee;

    // 实际退款金额（人民币元）
    private String sendBackFee;

    // 订单标题
    private String subject;

    // 商品描述
    private String body;

    // 交易创建时间
    private String gmtCreate;

    // 交易付款时间
    private String gmtPayment;

    // 交易退款时间
    private String gmtRefund;

    // 交易结束时间
    private String gmtClose;

    // 支付金额信息
    private String fundBillList;

    // 优惠券信息
    private String voucherDetailList;

    // 账期结算标识
    private String bizSettleMode;

}
