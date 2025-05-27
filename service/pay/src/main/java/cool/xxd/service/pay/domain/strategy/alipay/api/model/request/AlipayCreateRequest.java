package cool.xxd.service.pay.domain.strategy.alipay.api.model.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.SignParams;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AlipayCreateRequest extends AlipayCommonRequest {
    @NotNull
    private String opAppId;
    private String buyerId;
    private String buyerOpenId;
    private String opBuyerOpenId;
    private String body;
    private String timeExpire;
    private String timeoutExpress;
    private String passbackParams;
    private String discountableAmount;
    private String undiscountableAmount;
    private String storeId;
    private String alipayStoreId;
    private String enablePayChannels;
    private String disablePayChannels;
    private List<String> queryOptions;
    private SignParams agreementSignParams;
}
