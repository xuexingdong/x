package cool.xxd.service.pay.domain.strategy.wechat.api.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PayRequest {
    @NotNull
    private String spAppid;

    @NotNull
    private String spMchid;

    private String subAppid;

    @NotNull
    private String subMchid;

    @NotNull
    private String description;

    @NotNull
    private String outTradeNo;

    private String timeExpire;

    private String attach;

    @NotNull
    private String notifyUrl;

    private String goodsTag;

    private Boolean supportFapiao;

    private SettleInfo settleInfo;

    @NotNull
    private Amount amount;

    @NotNull
    private Payer payer;

    private Detail detail;

    private SceneInfo sceneInfo;

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Amount {
        @NotNull
        private Integer total;
        private String currency;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Payer {
        private String spOpenid;
        private String subOpenid;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Detail {
        private Integer costPrice;
        private String invoiceId;

        private List<GoodsDetail> goodsDetail;

        @JSONType(naming = PropertyNamingStrategy.SnakeCase)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class GoodsDetail {
            @NotNull
            private String merchantGoodsId;
            private String wechatpayGoodsId;
            private String goodsName;
            @NotNull
            private Integer quantity;
            @NotNull
            private Integer unitPrice;
        }
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class SceneInfo {
        @NotNull
        private String payerClientIp;
        private String deviceId;
        private StoreInfo storeInfo;

        @JSONType(naming = PropertyNamingStrategy.SnakeCase)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class StoreInfo {
            @NotNull
            private String id;
            private String name;
            private String areaCode;
            private String address;
        }
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class SettleInfo {
        private Boolean profitSharing;
    }
}
