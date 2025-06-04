package cool.xxd.product.land.domain.enums;

import cool.xxd.infra.enums.IntegerEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdiomEmotion implements IntegerEnum {
    POSITIVE(1, "褒义"),
    NEGATIVE(2, "贬义"),
    NEUTRAL(3, "中性"),
    ;

    private final Integer code;
    private final String name;
}
