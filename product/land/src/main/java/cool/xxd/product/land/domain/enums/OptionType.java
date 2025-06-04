package cool.xxd.product.land.domain.enums;

import cool.xxd.infra.enums.IntegerEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OptionType implements IntegerEnum {
    TEXT(101, "普通文本选项"),
    // 比如根号三这种需要使用latex和图片的选项
    RICH_TEXT(102, "富文本选项"),
    ;

    private final Integer code;
    private final String name;
}
