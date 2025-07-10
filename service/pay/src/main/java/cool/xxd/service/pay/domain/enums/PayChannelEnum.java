package cool.xxd.service.pay.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayChannelEnum implements StringEnum {
    FUIOU("FUIOU", "富友支付"),
    JIALIAN("JIALIAN", "嘉联支付"),
    WECHAT("WECHAT", "微信支付"),
    ALIPAY("ALIPAY", "支付宝"),
    CASH("CASH", "现金"),
    ;

    private final String code;
    private final String name;

}

