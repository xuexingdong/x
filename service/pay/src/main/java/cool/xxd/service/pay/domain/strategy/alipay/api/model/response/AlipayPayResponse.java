package cool.xxd.service.pay.domain.strategy.alipay.api.model.response;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AlipayPayResponse {
    @NotNull
    private String outTradeNo;
    @NotNull
    private String totalAmount;
    @NotNull
    private String receiptAmount;
    @NotNull
    private String gmtPayment;
    // TODO fund_bill_list
    private String buyerUserId;
    private String buyerOpenId;
    private String mdiscountAmount;
    private String discountAmount;
    private String tradeNo;
    private String buyerLogonId;
    private String buyerPayAmount;
    private String pointAmount;
    private String invoiceAmount;
    private String storeName;
    private String discountGoodsDetail;
    // TODO voucher_detail_list
}
