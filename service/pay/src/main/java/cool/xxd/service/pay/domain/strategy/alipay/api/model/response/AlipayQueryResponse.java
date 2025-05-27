package cool.xxd.service.pay.domain.strategy.alipay.api.model.response;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.GoodsDetail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AlipayQueryResponse {
    @NotNull
    private String outTradeNo;
    @NotNull
    private String tradeStatus;
    @NotNull
    private String totalAmount;
    @NotNull
    private List<GoodsDetail> reqGoodsDetail;
    @NotNull
    private String periodScene;
    private String transCurrency;
    private String settleCurrency;
    private String settleAmount;
    private String payCurrency;
    private String payAmount;
    private String settleTransRate;
    private String transPayRate;
    private String buyerPayAmount;
    private String pointAmount;
    private String invoiceAmount;
    private String sendPayDate;
    private String receiptAmount;
    private String storeId;
    private String terminalId;
    private String storeName;
    private String buyerUserId;
    private String buyerOpenId;
    private String buyerUserType;
    private String tradeNo;
    private String buyerLogonId;
    private String passbackParams;
}
