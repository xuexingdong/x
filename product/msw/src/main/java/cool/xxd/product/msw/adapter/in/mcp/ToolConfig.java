package cool.xxd.product.msw.adapter.in.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider userTools(MswMcpService mswMcpService) {
        return MethodToolCallbackProvider.builder().toolObjects(mswMcpService).build();
    }
}
