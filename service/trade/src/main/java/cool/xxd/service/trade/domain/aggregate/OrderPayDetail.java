package cool.xxd.service.trade.domain.aggregate;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderPayDetail {
    private Long id;
    private Long orderId;
    private String detailNo;
    private OrderPayDetailTypeEnum detailType;
    private BigDecimal payAmount;
    private BigDecimal refundedAmount;
    private String payTypeCode;
    private String payTypeName;
    private OrderPayDetailPayStatus payStatus;
    private String thirdTradeNo;
    private String payMchid;
    private LocalDateTime payTime;
    private Long storeId;
    private String storeName;
    private Long merchantId;
    private String merchantName;
    private LocalDateTime createTime;

    public BigDecimal getRefundableAmount() {
        return payAmount.subtract(refundedAmount);
    }

    public void startPay() {
        if (payStatus != OrderPayDetailPayStatus.INIT) {
            throw new BusinessException("订单支付详情状态错误");
        }
        payStatus = OrderPayDetailPayStatus.PROCESSING;
    }

    public void updatePayMchid(String payMchid) {
        this.payMchid = payMchid;
    }

    public void handlePayResult(OrderPayDetailPayResult orderPayDetailPayResult) {
        if (payStatus != OrderPayDetailPayStatus.PROCESSING) {
            throw new BusinessException("订单支付详情状态错误");
        }
        if (orderPayDetailPayResult.getPayStatus() == null
                || orderPayDetailPayResult.getPayStatus() == OrderPayDetailPayStatus.INIT) {
            throw new BusinessException("订单支付详情状态错误");
        }
        if (StringUtils.isNotBlank(orderPayDetailPayResult.getPayTypeCode())) {
            payTypeCode = orderPayDetailPayResult.getPayTypeCode();
        }
        if (StringUtils.isNotBlank(orderPayDetailPayResult.getPayTypeName())) {
            payTypeName = orderPayDetailPayResult.getPayTypeName();
        }
        if (StringUtils.isNotBlank(orderPayDetailPayResult.getThirdTradeNo())) {
            thirdTradeNo = orderPayDetailPayResult.getThirdTradeNo();
        }
        payStatus = orderPayDetailPayResult.getPayStatus();
        if (orderPayDetailPayResult.getPayStatus() == OrderPayDetailPayStatus.SUCCESS) {
            payTime = LocalDateTime.now();
        }
    }

    public void payFailed() {
        if (payStatus != OrderPayDetailPayStatus.PROCESSING) {
            throw new BusinessException("订单支付详情状态错误");
        }
        payStatus = OrderPayDetailPayStatus.FAILED;
    }

    public void refund(BigDecimal refundAmount) {
        refundedAmount = refundedAmount.add(refundAmount);
    }
}
