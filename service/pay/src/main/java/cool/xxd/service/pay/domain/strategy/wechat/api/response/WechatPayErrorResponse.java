package cool.xxd.service.pay.domain.strategy.wechat.api.response;

import lombok.Data;

@Data
public class WechatPayErrorResponse {
    private String code;
    private String message;
}
