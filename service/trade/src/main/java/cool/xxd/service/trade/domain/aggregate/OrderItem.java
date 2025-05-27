package cool.xxd.service.trade.domain.aggregate;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderItem implements Serializable {
    private Long id;
    private Long orderId;
    private Long parentOrderItemId;
    private Long skuId;
    private String skuName;
    private Long specId;
    private String specName;
    private BigDecimal originalPrice;
    private BigDecimal price;
    // 成本-可能为空
    private BigDecimal cost;
    private BigDecimal quantity;
    // 已退数量
    private BigDecimal refundedQuantity;
    private BigDecimal amount;
    private BigDecimal discountAmount;
    // 实付金额 = 金额-优惠金额
    private BigDecimal actualPayAmount;
    // 已退金额(不包含餐盒费)
    private BigDecimal refundedAmount;
    // 已退优惠金额(不包含餐盒费)
    private BigDecimal refundedDiscountAmount;
    // 已退实付金额(不包含餐盒费)
    private BigDecimal refundedActualPayAmount;
    // 总成本-可能为空
    private BigDecimal totalCost;
    // 已退总成本-可能为空
    private BigDecimal refundedTotalCost;
    private Integer spuType;
    private Long spuId;
    private String spuName;
    private Long categoryId;
    private String categoryName;
    private Long spuCoverImgFileId;
    private Long unitId;
    private String unitName;
    private BigDecimal unitBoxFee; // 单个餐盒费用
    private BigDecimal boxFee;  // 总餐盒费用
    private BigDecimal refundedBoxFee;  // 已退款餐盒费用
    private List<OrderSubItem> subItems;
    private List<OrderItemCookingMethodGroup> cookingMethodGroups;
    private LocalDateTime orderTime;
    private ItemActiveStatusEnum activeStatus;
    private Long storeId;
    private String storeName;
    private Long merchantId;
    private String merchantName;

    public void active() {
        this.activeStatus = ItemActiveStatusEnum.ACTIVE;
    }

    public void refund(RefundOrderItem refundOrderItem) {
        if (quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("逆向订单项不可退");
        }
        if (refundedQuantity.add(refundOrderItem.getRefundQuantity()).compareTo(quantity) > 0) {
            throw new BusinessException("退款数量超过可退数量");
        }
        this.refundedQuantity = this.refundedQuantity.add(refundOrderItem.getRefundQuantity());
        this.refundedAmount = this.refundedAmount.add(refundOrderItem.getRefundAmount());
        this.refundedDiscountAmount = this.refundedDiscountAmount.add(refundOrderItem.getRefundDiscountAmount());
        this.refundedActualPayAmount = this.refundedActualPayAmount.add(refundOrderItem.getActualRefundAmount());
        this.refundedBoxFee = this.refundedBoxFee.add(refundOrderItem.getRefundBoxAmount());
    }

    public BigDecimal getRemainingCost() {
        if (totalCost == null) {
            return null;
        }
        return totalCost.subtract(refundedTotalCost);
    }

    // https://developers.weixin.qq.com/community/develop/article/doc/0004acb9054af0bb209b474e756413
    public RefundAmountCalcResult calcRefundAmount(BigDecimal refundQuantity) {
        if (refundedQuantity.add(refundQuantity).compareTo(quantity) > 0) {
            throw new BusinessException("不可超退");
        }
        var refundAmountCalcResult = new RefundAmountCalcResult();
        // 检查是否为最后一次退款
        if (refundedQuantity.add(refundQuantity).compareTo(quantity) == 0) {
            var remainingAmount = amount.subtract(refundedAmount);
            var remainingDiscountAmount = discountAmount.subtract(refundedDiscountAmount);
            refundAmountCalcResult.setRefundAmount(remainingAmount);
            refundAmountCalcResult.setRefundDiscountAmount(remainingDiscountAmount);
            refundAmountCalcResult.setActualRefundAmount(remainingAmount.subtract(remainingDiscountAmount));
        } else {
            // 计算退款比例
            var refundRatio = refundQuantity.divide(quantity, 2, RoundingMode.HALF_UP);
            // 按比例计算退款金额
            var refundAmount = refundRatio.multiply(amount).setScale(2, RoundingMode.HALF_UP);
            // 按比例计算应退优惠金额
            var refundDiscountAmount = refundRatio.multiply(discountAmount).setScale(2, RoundingMode.HALF_UP);
            // 按比例计算实际退款金额
            var actualRefundAmount = refundAmount.subtract(refundDiscountAmount);
            refundAmountCalcResult.setRefundAmount(refundAmount);
            refundAmountCalcResult.setRefundDiscountAmount(refundDiscountAmount);
            refundAmountCalcResult.setActualRefundAmount(actualRefundAmount);
        }
        return refundAmountCalcResult;
    }

    public RefundAmountCalcResult calcRefundBoxFeeAmount(BigDecimal refundQuantity) {
        RefundAmountCalcResult refundAmountCalcResult = new RefundAmountCalcResult();
        refundAmountCalcResult.setRefundDiscountAmount(BigDecimal.ZERO);
        // 检查是否为最后一次退款
        if (refundedQuantity.add(refundQuantity).compareTo(quantity) == 0) {
            var remainingAmount = boxFee.subtract(refundedBoxFee);
            refundAmountCalcResult.setRefundAmount(remainingAmount);
            refundAmountCalcResult.setActualRefundAmount(remainingAmount);
        } else {
            // 按数量进行退款
            var refundAmount = unitBoxFee.multiply(refundQuantity).setScale(2, RoundingMode.HALF_UP);
            refundAmountCalcResult.setRefundAmount(refundAmount);
            refundAmountCalcResult.setActualRefundAmount(refundAmount);
        }
        return refundAmountCalcResult;
    }

    public BigDecimal getRefundableQuantity() {
        return quantity.subtract(refundedQuantity);
    }
}
