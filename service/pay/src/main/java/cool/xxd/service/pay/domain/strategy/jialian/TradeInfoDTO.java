package cool.xxd.service.pay.domain.strategy.jialian;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TradeInfoDTO {
    private String tradeNo;
    private String outTradeNo;
    private Integer payStatus;
    private LocalDateTime orderTime;
    private LocalDateTime payTime;
    private LocalDateTime finishTime;
    private Integer payAmount;
    private Integer refundAmount;
    private Integer shopId;
    private String bodyId;
    private String clientIp;
    private String clientType;
    private TradePayConfigDTO payConfig;
    private Integer actualPayAmount;
    private Integer discountAmount;
    private List<ChannelCouponInfoDTO> couponInfos;

    @Data
    public static class ChannelCouponInfoDTO {
        @NotNull
        private String promotionId;
        @NotNull
        private String name;
        private String scope;
        private String type;
        private Integer amount;
        private Integer wxpayContribute;
        private Integer merchantContribute;
        private Integer otherContribute;
    }
}
