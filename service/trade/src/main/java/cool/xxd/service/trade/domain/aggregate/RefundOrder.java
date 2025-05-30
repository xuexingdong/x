package cool.xxd.service.trade.domain.aggregate;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.trade.domain.enums.CustomerTypeEnum;
import cool.xxd.service.trade.domain.enums.OrderSourceEnum;
import cool.xxd.service.trade.domain.enums.RefundOrderStatusEnum;
import cool.xxd.service.trade.domain.enums.RefundTypeEnum;
import cool.xxd.service.trade.domain.utils.BigDecimalUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundOrder {
    private Long id;
    private String refundOrderNo;
    private BigDecimal totalRefundQuantity;
    private BigDecimal totalRefundAmount;
    private RefundTypeEnum refundType;
    // 退款的优惠金额
    private BigDecimal refundDiscountAmount;
    // 实退金额=退款金额-退款的优惠金额
    private BigDecimal actualRefundAmount;
    private BigDecimal refundBoxAmount; // 退款的餐盒费
    // 已退金额(包含餐盒费)
    private BigDecimal refundedAmount;
    private String payTypeCode;
    private String payTypeName;
    private LocalDateTime applyTime;
    private String refundReasonCode;
    private String refundReason;
    private RefundOrderStatusEnum refundOrderStatus;
    private LocalDateTime refundTime;

    // 原订单信息
    private Long orderId;
    private OrderSourceEnum orderSource;
    private CustomerTypeEnum customerType;
    private Long customerId;
    private String customerName;
    private Long storeTableId;
    private String storeTableName;
    private Long storeId;
    private String storeName;
    private Long merchantId;
    private String merchantName;

    public void refund(BigDecimal refundAmount, String payTypeCode, String payTypeName) {
        BigDecimalUtils.validateIsTwoDecimalPlacesOrLess(refundAmount);
        if (refundOrderStatus == RefundOrderStatusEnum.REFUNDED) {
            throw new BusinessException("无法重复退款");
        }
        if (refundedAmount.add(refundAmount).compareTo(actualRefundAmount) > 0) {
            throw new BusinessException("退款金额超过实退金额");
        }
        refundedAmount = refundedAmount.add(refundAmount);
        if (refundedAmount.compareTo(actualRefundAmount) == 0) {
            this.payTypeCode = payTypeCode;
            this.payTypeName = payTypeName;
            refunded();
        } else {
            refundOrderStatus = RefundOrderStatusEnum.PARTIALLY_REFUNDED;
        }
    }

    public void refunded() {
        refundOrderStatus = RefundOrderStatusEnum.REFUNDED;
        refundTime = LocalDateTime.now();
    }

    public void cancelRefund() {
        if (refundOrderStatus != RefundOrderStatusEnum.WAIT_AUDIT) {
            throw new BusinessException("退款单状态错误");
        }
        refundOrderStatus = RefundOrderStatusEnum.CANCELED;
    }

    public void agreeRefund() {
        if (refundOrderStatus != RefundOrderStatusEnum.WAIT_AUDIT) {
            throw new BusinessException("退款单状态错误");
        }
        refundOrderStatus = RefundOrderStatusEnum.WAIT_REFUND;
    }

    public void rejectRefund() {
        if (refundOrderStatus != RefundOrderStatusEnum.WAIT_AUDIT) {
            throw new BusinessException("退款单状态错误");
        }
        refundOrderStatus = RefundOrderStatusEnum.REJECTED;
    }

    public void startRefund() {
        if (refundOrderStatus != RefundOrderStatusEnum.WAIT_REFUND &&
                refundOrderStatus != RefundOrderStatusEnum.PARTIALLY_REFUNDED) {
            throw new BusinessException("退款单状态错误");
        }
        refundOrderStatus = RefundOrderStatusEnum.REFUNDING;
    }

    public boolean isRefunded() {
        return refundOrderStatus == RefundOrderStatusEnum.REFUNDED;
    }
}
