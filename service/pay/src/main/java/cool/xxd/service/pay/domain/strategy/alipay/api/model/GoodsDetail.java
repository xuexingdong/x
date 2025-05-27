package cool.xxd.service.pay.domain.strategy.alipay.api.model;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class GoodsDetail {
    @NotNull
    private String goodsId;
    @NotNull
    private String goodsName;
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private BigDecimal price;
    private String outItemId;
    private String outSkuId;
    private String alipayGoodsId;
    private String goodsCategory;
    private String categoriesTree;
    private String body;
    private String showUrl;
}
