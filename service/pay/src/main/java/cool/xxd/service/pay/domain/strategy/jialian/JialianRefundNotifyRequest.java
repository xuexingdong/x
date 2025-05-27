package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JialianRefundNotifyRequest {
    private String msgTag;
    private Long groupID;
    private Integer shopID;
    private String data;

    @Data
    public static class RefundNotifyData {
        private String refundNo;
        private String outRefundNo;
        private String tradeNo;
        private String outTradeNo;
        private Integer refundStatus;
        private Integer refundType;
        private String refundAmount;
        private LocalDateTime startTime;
        private LocalDateTime refundTime;
        private LocalDateTime finishTime;
        private String bodyId;
        private String payType;
    }
}
