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
public class AlipayRefundQueryResponse {
    private String tradeNo;
    private String outTradeNo;
    private String outRequestNo;
    private String totalAmount;
    private String refundAmount;
    @NotNull
    private String refundStatus;
    // TODO refund_royaltys
    private String gmtRefundPay;
    // TODO refund_detail_item_list
    private String sendBackFee;
    // TODO deposit_back_info
    // TODO refund_voucher_detail_list
    private String preAuthCancelFee;
    private String refundHybAmount;
    // TODO refund_charge_info_list
    // TODO deposit_back_info_list
}
