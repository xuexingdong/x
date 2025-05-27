package cool.xxd.service.pay.domain.aggregate;

import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayOrder {
    private Long id;
    private String appid;
    private String mchid;
    private String payOrderNo;
    /**
     * 注意退订单的outTradeNo是正订单的outTradeNo
     */
    private String outTradeNo;
    private BigDecimal totalAmount;
    private BigDecimal refundedAmount;
    private TransModeEnum transMode;
    private String authCode;
    private String subAppid;
    private String subOpenid;
    private String payTypeCode;
    private String payTypeName;
    private String payChannelCode;
    private String payChannelName;
    // 交易时间，即业务时间，外部传入
    private LocalDateTime tradeTime;
    // 过期时间，超过此时间订单过期
    private LocalDateTime timeExpire;
    private String subject;
    private String description;
    private String customData;
    private PayStatusEnum payStatus;
    private LocalDateTime pollingStartTime;
    // 支付时间，即第三方支付的时间
    private LocalDateTime payTime;
    // 三方支付平台的流水号
    private String thirdTradeNo;
    // 二维码
    private String qrCode;
    // 客户端唤起支付参数
    private String clientPayInvokeParams;

    public void handlePayResult(PayResult payResult) {
        if (isFinalPayStatus()) {
            return;
        }
        if (payResult.getThirdTradeNo() != null) {
            thirdTradeNo = payResult.getThirdTradeNo();
        }
        if (payResult.getQrCode() != null) {
            qrCode = payResult.getQrCode();
        }
        if (payResult.getClientPayInvokeParams() != null) {
            clientPayInvokeParams = payResult.getClientPayInvokeParams();
        }
        switch (payResult.getPayStatus()) {
            case PAID -> {
                payStatus = PayStatusEnum.PAID;
                if (payResult.getPayTime() != null) {
                    payTime = payResult.getPayTime();
                } else {
                    payTime = LocalDateTime.now();
                }
            }
            case FAILED -> fail();
            case CLOSED -> payStatus = PayStatusEnum.CLOSED;
            case REVOKED -> payStatus = PayStatusEnum.REVOKED;
        }
    }

    public void fail() {
        if (payStatus != PayStatusEnum.PAYING) {
            throw new BusinessException("支付状态错误");
        }
        payStatus = PayStatusEnum.FAILED;
    }

    public boolean isClosed() {
        return payStatus == PayStatusEnum.CLOSED;
    }

    public void close() {
        payStatus = PayStatusEnum.CLOSED;
    }

    public void startPay() {
        if (payStatus != PayStatusEnum.UNPAID) {
            throw new BusinessException("支付状态错误");
        }
        payStatus = PayStatusEnum.PAYING;
        pollingStartTime = LocalDateTime.now().plusSeconds(Constants.POLLING_DELAY_SECONDS);
    }

    public boolean isFinalPayStatus() {
        return payStatus == PayStatusEnum.PAID
                || payStatus == PayStatusEnum.FAILED
                || payStatus == PayStatusEnum.CLOSED
                || payStatus == PayStatusEnum.REFUNDED;
    }

    public BigDecimal getRefundableAmount() {
        return totalAmount.subtract(refundedAmount);
    }

    public void refund(BigDecimal refundAmount) {
        // TODO 校验，不能超额
        refundedAmount = refundedAmount.add(refundAmount);
    }
}
