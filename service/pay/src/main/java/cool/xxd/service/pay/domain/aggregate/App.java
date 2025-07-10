package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

import java.util.Map;

@Data
public class App {
    private Long id;
    private String mchid;
    private String appid;
    private String notifyUrl;
    // 只有富友使用，后续迁移到config
    private String publicKey;
    // 只有富友使用，后续迁移到config
    private String privateKey;
    private Map<String, String> config;
}
