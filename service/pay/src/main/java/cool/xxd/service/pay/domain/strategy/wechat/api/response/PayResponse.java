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
public class PayResponse {
    @NotNull
    private String spAppid;
    @NotNull
    private String spMchid;
    private String subAppid;
    @NotNull
    private String subMchid;
    @NotNull
    private String outTradeNo;
    private String transactionId;
    private String tradeType;
    @NotNull
    private String tradeState;
    @NotNull
    private String tradeStateDesc;
    private String bankType;
    private String attach;
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
        private String openid;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Amount {
        private Integer total;

        private Integer payerTotal;

        private String currency;

        private String payerCurrency;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class SceneInfo {
        private String deviceId;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class PromotionDetail {
        @NotNull
        private String couponId;
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
