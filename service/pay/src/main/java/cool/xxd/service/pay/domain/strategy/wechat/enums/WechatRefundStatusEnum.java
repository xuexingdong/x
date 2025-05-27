package cool.xxd.service.pay.domain.strategy.wechat.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WechatRefundStatusEnum implements CommonEnum<String> {
    SUCCESS("SUCCESS", "退款成功"),
    CLOSED("CLOSED", "退款关闭"),
    PROCESSING("PROCESSING", "退款处理中"),
    ABNORMAL("ABNORMAL", "退款异常"),
    ;

    private final String code;
    private final String name;
}
