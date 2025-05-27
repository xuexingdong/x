package cool.xxd.service.pay.domain.strategy.jialian;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTradeRequest {
    @NotNull
    private String outTradeNo;
    @NotNull
    private Integer payAmount;
    @NotNull
    private String clientIp;
    @NotNull
    private String settlementBodyId;
    @NotNull
    private TradePayParam tradePayParam;
    private String outNotifyUrl;

    @Data
    public static class TradePayParam {
        @NotNull
        private String payType;
        private String authCode;
        private String clientAppId;
        private String clientUserId;
        private String qrCodePlatform;
        private String attach;
        private String clientShopInfo;
        private String clientDeviceInfo;
    }
}
