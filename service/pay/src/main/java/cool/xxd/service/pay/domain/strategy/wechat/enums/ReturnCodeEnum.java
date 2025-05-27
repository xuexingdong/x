package cool.xxd.service.pay.domain.strategy.wechat.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReturnCodeEnum implements CommonEnum<String> {
    SUCCESS("SUCCESS", "通信成功"),
    FAIL("FAIL", "通信失败"),
    ;

    private final String code;
    private final String name;
}
