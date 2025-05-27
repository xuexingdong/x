package cool.xxd.service.pay.domain.strategy.alipay.api.model;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class ExtendParams {
    private String sysServiceProviderId;
    private String hbFqNum;
    private String hbFqSellerPercent;
    private String specifiedSellerName;
    private String cardType;
    private String tradeComponentOrderId;
}