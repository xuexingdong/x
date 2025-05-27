package cool.xxd.service.pay.domain.strategy.wechat.api.response;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class WechatPayNotifyResource {
    @NotNull
    private String spMchid;
    @NotNull
    private String subMchid;
    @NotNull
    private String spAppid;
    private String subAppid;
    @NotNull
    private String outTradeNo;
    @NotNull
    private String transactionId;
    @NotNull
    private String tradeType;
    @NotNull
    private String tradeState;
    @NotNull
    private String tradeStateDesc;
    @NotNull
    private String bankType;
    private String attach;
    @NotNull
    private String successTime;
    @NotNull
    private Payer payer;
    @NotNull
    private Amount amount;
    private SceneInfo sceneInfo;
    private List<PromotionDetail> promotionDetail;

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Payer {
        @NotNull
        private String spOpenid;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Amount {
        @NotNull
        private Integer total;
        @NotNull
        private Integer payerTotal;
        @NotNull
        private String currency;
        @NotNull
        private String payerCurrency;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class SceneInfo {
        @NotNull
        private String deviceId;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class PromotionDetail {
        @NotNull
        private String couponId;
        @NotNull
        private String name;
        private String scope;
        private String type;
        @NotNull
        private Integer amount;
        private String stockId;
        private Integer wechatpayContribute;
        private Integer merchantContribute;
        private Integer otherContribute;
        private String currency;
        private List<GoodsDetail> goodsDetail;

        @JSONType(naming = PropertyNamingStrategy.SnakeCase)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @Data
        public static class GoodsDetail {
            @NotNull
            private String goodsId;
            @NotNull
            private Integer quantity;
            @NotNull
            private Integer unitPrice;
            @NotNull
            private Integer discountAmount;
            private String goodsRemark;
        }
    }
}
