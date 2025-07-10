package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_pay_order")
public class PayOrderDO extends XBaseDO {
    private String mchid;
    private String appid;
    private String payOrderNo;
    private String outTradeNo;
    private BigDecimal totalAmount;
    private BigDecimal refundedAmount;
    private String transMode;
    private String authCode;
    private String subAppid;
    private String subOpenid;
    private String payTypeCode;
    private String payTypeName;
    private String payChannelCode;
    private String payChannelName;
    private LocalDateTime tradeTime;
    private LocalDateTime timeExpire;
    private String subject;
    private String description;
    private String customData;
    private String payStatus;
    private LocalDateTime pollingStartTime;
    private LocalDateTime payTime;
    private String thirdTradeNo;
    private String qrCode;
    private String clientPayInvokeParams;
}
