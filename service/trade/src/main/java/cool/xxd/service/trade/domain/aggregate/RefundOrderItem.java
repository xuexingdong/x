package cool.xxd.service.trade.domain.aggregate;

import cloud.huizeng.oc.domain.enums.ItemActiveStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundOrderItem {
    private Long id;
    private Long refundOrderId;
    // 已退数量
    private BigDecimal refundQuantity;
    private BigDecimal refundAmount;  //  退款金额，不包含餐盒费
    // 退款的优惠金额
    private BigDecimal refundDiscountAmount;
    // 实退金额=退款金额-退款的优惠金额
    private BigDecimal actualRefundAmount;
    private LocalDateTime refundTime;
    private ItemActiveStatusEnum activeStatus;

    // 原订单项信息
    private Long orderItemId;
    private Long skuId;
    private String skuName;
    private Long specId;
    private String specName;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private BigDecimal cost;
    private BigDecimal totalCost;
    private Integer spuType;
    private Long spuId;
    private String spuName;
    private Long categoryId;
    private String categoryName;
    private Long spuCoverImgFileId;
    private Long unitId;
    private String unitName;
    private BigDecimal refundBoxAmount;  // 退款的餐盒费
    private LocalDateTime orderTime;
    private Long storeId;
    private String storeName;
    private Long merchantId;
    private String merchantName;

    public void active(LocalDateTime refundTime) {
        this.activeStatus = ItemActiveStatusEnum.ACTIVE;
        this.refundTime = refundTime;
    }
}
