package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

import java.util.Map;

@Data
public class App {
    private Long id;
    private String appid;
    // 订单号前缀-只有富友使用，后续迁移到config
    private String orderNoPrefix;
    // 只有富友使用，后续迁移到config
    private String notifyUrl;
    // 只有富友使用，后续迁移到config
    private String publicKey;
    // 只有富友使用，后续迁移到config
    private String privateKey;
    private Map<String, String> config;
}
