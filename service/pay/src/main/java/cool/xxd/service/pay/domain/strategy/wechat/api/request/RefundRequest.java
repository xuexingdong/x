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
public class RefundRequest {
    @NotNull
    private String subMchid;
    /**
     * transactionId, outTradeNo 二选一
     */
    private String transactionId;
    private String outTradeNo;
    @NotNull
    private String outRefundNo;
    private String reason;
    private String notifyUrl;
    private String fundsAccount;
    @NotNull
    private Amount amount;
    private List<GoodsDetail> goodsDetail;

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Amount {
        @NotNull
        private Integer refund;
        private List<From> from;
        @NotNull
        private Integer total;
        @NotNull
        private String currency;
    }

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class From {
        @NotNull
        private String account;
        @NotNull
        private Integer amount;
    }

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
        private Integer unitPrice;
        @NotNull
        private Integer refundAmount;
        @NotNull
        private Integer refundQuantity;
    }
}
