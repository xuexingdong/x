package cool.xxd.service.pay.domain.strategy.alipay.api.model.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AlipayPayRequest extends AlipayCommonRequest {
    private String authCode;
    @NotNull
    private String scene;
    // TODO promo_params
    private String storeId;
    private String operatorId;
    private String terminalId;
    private List<String> queryOptions;
}
