package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_refund_order")
public class RefundOrderDO extends XBaseDO {
    private String mchid;
    private String appid;
    private String payOrderNo;
    private String outTradeNo;
    private String refundOrderNo;
    private String outRefundNo;
    private BigDecimal totalAmount;
    private BigDecimal refundAmount;
    private String refundReason;
    private String payTypeCode;
    private String payTypeName;
    private String payChannelCode;
    private String payChannelName;
    private String refundStatus;
    private LocalDateTime pollingStartTime;
    private LocalDateTime refundTime;
    private String thirdTradeNo;
}
