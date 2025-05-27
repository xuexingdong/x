package cool.xxd.service.pay.domain.strategy.wechat.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCodeEnum implements CommonEnum<String> {
    SUCCESS("SUCCESS", "支付成功"),
    FAIL("FAIL", "支付失败"),
    ;

    private final String code;
    private final String name;
}
