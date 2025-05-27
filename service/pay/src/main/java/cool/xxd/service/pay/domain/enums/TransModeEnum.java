package cool.xxd.service.pay.domain.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransModeEnum implements CommonEnum<String> {
    SCAN("SCAN", "主扫"),
    PASSIVE_SCAN("PASSIVE_SCAN", "被扫"),
    JSAPI("JSAPI", "小程序"),
    ;

    private final String code;
    private final String name;
}

