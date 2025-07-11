package cool.xxd.product.msw.application.dto.request;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
public class QueryMobRequest {
    @ToolParam(description = "怪物名称，为空查询全部")
    private String name;
}
