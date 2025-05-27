package cool.xxd.service.pay.domain.strategy.wechat.api.response;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class WechatRefundNotifyResource {
    @NotNull
    private String spMchid;
    @NotNull
    private String subMchid;
    @NotNull
    private String outTradeNo;
    @NotNull
    private String transactionId;
    @NotNull
    private String outRefundNo;
    @NotNull
    private String refundId;
    @NotNull
    private String refund_status;
    private String successTime;
    @NotNull
    private String userReceivedAccount;
    @NotNull
    private Amount amount;

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Amount {
        @NotNull
        private Integer total;
        @NotNull
        private Integer refund;
        @NotNull
        private Integer payerTotal;
        @NotNull
        private Integer payerRefund;
    }
}
