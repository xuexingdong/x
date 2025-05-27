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
public class RefundResponse {
    @NotNull
    private String refundId;
    @NotNull
    private String outRefundNo;
    @NotNull
    private String transactionId;
    @NotNull
    private String outTradeNo;
    @NotNull
    private String channel;
    @NotNull
    private String userReceivedAccount;
    private String successTime;
    @NotNull
    private String createTime;
    @NotNull
    private String status;
    private String fundsAccount;
    @NotNull
    private Amount amount;
    private List<PromotionDetail> promotionDetail;

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Amount {
        @NotNull
        private Integer total;
        @NotNull
        private Integer refund;
        private List<From> from;
        @NotNull
        private Integer payerTotal;
        @NotNull
        private Integer payerRefund;
        @NotNull
        private Integer settlementRefund;
        @NotNull
        private Integer settlementTotal;
        @NotNull
        private Integer discountRefund;
        @NotNull
        private String currency;
        private Integer refundFee;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class From {
        @NotNull
        private String account;
        @NotNull
        private Integer amount;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class PromotionDetail {
        @NotNull
        private String promotionId;
        @NotNull
        private String scope;
        @NotNull
        private String type;
        @NotNull
        private Integer amount;
        @NotNull
        private Integer refundAmount;
        private List<GoodsDetail> goodsDetail;

        @JSONType(naming = PropertyNamingStrategy.SnakeCase)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @Data
        public static class GoodsDetail {
            @NotNull
            private String merchantGoodsId;
            private String wechatpayGoodsId;
            private String goodsName;
            @NotNull
            private Integer unitPrice;
            @NotNull
            private Integer refundAmount;
            @NotNull
            private Integer refundQuantity;
        }
    }
}
