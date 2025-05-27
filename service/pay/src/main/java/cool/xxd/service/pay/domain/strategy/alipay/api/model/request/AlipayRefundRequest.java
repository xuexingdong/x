package cool.xxd.service.pay.domain.strategy.alipay.api.model.request;

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
public class AlipayRefundRequest {
    @NotNull
    private String refundAmount;
    private String outTradeNo;
    private String tradeNo;
    @NotNull
    private String refundReason;
    private String outRequestNo;
    // TODO refund_goods_detail
    // TODO refund_royalty_parameters
    private List<String> queryOptions;
    private String relatedSettleConfirmNo;
}
