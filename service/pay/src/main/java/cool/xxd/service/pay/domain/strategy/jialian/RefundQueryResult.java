package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RefundQueryResult {
    private String tradeNo;
    private String outTradeNo;
    private Integer payAmount;
    private Integer refundedAmount;
    private List<RefundDetailDTO> refundDetails;

    @Data
    public static class RefundDetailDTO {
        private String refundNo;
        private String outRefundNo;
        private String refundAmount;
        private String refundRemark;
        private LocalDateTime startTime;
        private LocalDateTime refundTime;
        private LocalDateTime finishTime;
        private Integer shopId;
        private String bodyId;
        private Integer refundType;
        private Integer refundStatus;
        private String tradeNo;
        private String outTradeNo;
        private String payType;
        private String clientIp;
        private String clientType;
    }
}
