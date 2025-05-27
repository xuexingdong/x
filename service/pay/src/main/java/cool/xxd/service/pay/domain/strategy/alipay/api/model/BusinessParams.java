package cool.xxd.service.pay.domain.strategy.alipay.api.model;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BusinessParams {
    private String enterprisePayInfo;
    private String enterprisePayAmount;
    private String mcCreateTradeIp;
    private String tinyAppMerchantBizType;
}
