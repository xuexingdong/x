package cool.xxd.service.pay.domain.strategy.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayConfig {
    private String baseUrl;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String encryptKey;
}
