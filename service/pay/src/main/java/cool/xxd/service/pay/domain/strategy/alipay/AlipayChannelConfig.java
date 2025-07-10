package cool.xxd.service.pay.domain.strategy.alipay;

import lombok.Data;

@Data
public class AlipayChannelConfig {
    private String app_auth_token;
    private String alipay_notify_url;
}
