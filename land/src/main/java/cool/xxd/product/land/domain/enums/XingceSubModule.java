package cool.xxd.product.land.domain.enums;

import cool.xxd.infra.enums.IntegerEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum XingceSubModule implements IntegerEnum {
    LUOJITIANKONG(1, "逻辑填空"),
    ;

    private final Integer code;
    private final String name;
}
