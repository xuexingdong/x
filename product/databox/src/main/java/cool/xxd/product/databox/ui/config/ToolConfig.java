package cool.xxd.product.databox.ui.config;

import cool.xxd.product.databox.adapter.in.mcp.UserMcpService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider userTools(UserMcpService userMcpService) {
        return MethodToolCallbackProvider.builder().toolObjects(userMcpService).build();
    }
}
