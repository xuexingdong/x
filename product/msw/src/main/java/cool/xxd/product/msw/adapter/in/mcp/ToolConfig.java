package cool.xxd.product.msw.adapter.in.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider dropTools(DropMcpService dropMcpService) {
        return MethodToolCallbackProvider.builder().toolObjects(dropMcpService).build();
    }

    @Bean
    public ToolCallbackProvider marketTools(MarketMcpService marketMcpService) {
        return MethodToolCallbackProvider.builder().toolObjects(marketMcpService).build();
    }

    @Bean
    public ToolCallbackProvider accTools(AccService accService) {
        return MethodToolCallbackProvider.builder().toolObjects(accService).build();
    }
}
