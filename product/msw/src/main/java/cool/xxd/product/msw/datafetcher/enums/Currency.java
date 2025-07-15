package cool.xxd.product.msw.datafetcher.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency implements StringEnum {
    MESO("meso", "楓幣"),
    SNOW("snow", "雪花"),
    ;

    private final String code;
    private final String name;
}

