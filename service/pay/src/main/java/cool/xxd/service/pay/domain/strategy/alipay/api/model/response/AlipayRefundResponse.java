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
public class AlipayRefundResponse {
    @NotNull
    private String tradeNo;
    @NotNull
    private String outTradeNo;
    @NotNull
    private String buyerLogonId;
    private String refundFee;
    // TODO refund_detail_item_list
    private String storeName;
    private String buyerUserId;
    private String buyerOpenId;
    private String sendBackFee;
    private String preAuthCancelFee;
    private String fundChange;
    private String refundHybAmount;
    // TODO refund_charge_info_list
    // TODO refund_voucher_detail_list
}
