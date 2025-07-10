package cool.xxd.service.trade.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType implements StringEnum {
    GOODS("GOODS", "商品"),
    SERVICE("SERVICE", "服务"),
    ;

    private final String code;
    private final String name;
}
