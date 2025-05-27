package cool.xxd.service.trade.domain.aggregate;

import cool.xxd.service.trade.domain.enums.OrderStatusEnum;
import cool.xxd.service.trade.domain.valueobject.TradeCost;
import cool.xxd.service.trade.domain.valueobject.Trader;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Trade {
    private Long id;
    private String orderNo;
    private String orderType;
    private LocalDateTime orderTime;
    private BigDecimal orderAmount;
    private OrderStatusEnum orderStatus;
    private Trader buyer;
    private Trader seller;
    private TradeCost cost;
    private BigDecimal quantity;

//    private Long id;
//    private String orderNo;
//    private String dayNo;
//    private OrderTypeEnum orderType;
//    // 用餐方式，可能为空
//    private DiningMethodEnum diningMethod;
//    private BigDecimal totalCost;
//    private BigDecimal totalQuantity;
//    private OrderStatusEnum orderStatus;
//    private LocalDateTime orderTime;
//    private OrderSourceEnum orderSource;
//
//    // 支付信息
//    private BigDecimal originalTotalAmount;
//    private BigDecimal totalAmount;
//    // 优惠金额
//    private BigDecimal discountAmount;
//    // 实付金额 = 总金额-优惠金额
//    private BigDecimal actualPayAmount;
//    // 已付金额
//    private BigDecimal paidAmount;
//    private BigDecimal refundedAmount;
//    // 支付过期时间
//    private LocalDateTime payExpireTime;
//    private String payTypeCode;
//    private String payTypeName;
//    private PayStatusEnum payStatus;
//    private LocalDateTime payTime;
//
//    // 配送信息
//    private Long addressId;
//    private BigDecimal deliveryFee;
//    private DeliveryStatusEnum deliveryStatus;
//    // 期望送达开始时间
//    private LocalDateTime expectedDeliveryStartTime;
//    // 期望送达截止时间
//    private LocalDateTime expectedDeliveryEndTime;
//
//    // 关联信息
//    // 客户类型，默认散客
//    private CustomerTypeEnum customerType;
//    private Long customerId;
//    private String customerName;
//    private Long storeTableId;
//    private String storeTableName;
//    private Integer dinerCount;
//    // 下单员工
//    private Long orderByStoreEmployeeId;
//    private String orderByStoreEmployeeName;
//    private Long storeId;
//    private String storeName;
//    private Long merchantId;
//    private String merchantName;
//    private String remark;
//    private LocalDateTime pickUpTime;
//    private Long creatorId;
//    private BigDecimal boxFee; // 餐盒费
}