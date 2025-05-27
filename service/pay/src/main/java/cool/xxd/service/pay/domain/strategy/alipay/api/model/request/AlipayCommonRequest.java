package cool.xxd.service.pay.domain.strategy.alipay.api.model.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.BusinessParams;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.ExtendParams;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.GoodsDetail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public abstract class AlipayCommonRequest {
    @NotNull
    private String outTradeNo;
    @NotNull
    private String totalAmount;
    @NotNull
    private String subject;
    private String notifyUrl;
    @NotNull
    private String productCode;
    private String sellerId;
    private List<GoodsDetail> goodsDetail;
    private ExtendParams extendParams;
    private BusinessParams businessParams;
}
