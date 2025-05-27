package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefundCreateResult {
    private String refundNo;
    private String outRefundNo;
    private String tradeNo;
    private String outTradeNo;
    private Integer refundStatus;
    private Integer refundType;
    private LocalDateTime startTime;
    private LocalDateTime refundTime;
    private LocalDateTime finishTime;
    private String bodyId;
    private String payType;
}
