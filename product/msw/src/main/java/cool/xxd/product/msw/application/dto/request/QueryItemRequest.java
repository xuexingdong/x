package cool.xxd.product.msw.application.dto.request;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
public class QueryItemRequest {
    @ToolParam(description = "物品名称，为空查询全部")
    private String name;
}
