package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JialianPayNotifyRequest {
    private String msgTag;
    private Long groupID;
    private Integer shopID;
    private String data;

    @Data
    public static class PayNotifyData {
        private String tradeNo;
        private String outTradeNo;
        private Integer payStatus;
        private String payType;
        private LocalDateTime orderTime;
        private LocalDateTime payTime;
        private LocalDateTime finishTime;
        private Integer payAmount;
        private Integer refundAmount;
        private Integer shopId;
        private String bodyId;
        private String scanAuthPlatform;
        private String clientIp;
        private String clientType;
        private Integer actualPayAmount;
        private Integer discountAmount;
        private String scanAuthPlatformName;
        private String attach;
        private String channelTransNo;
    }
}
