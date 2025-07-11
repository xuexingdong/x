package cool.xxd.product.msw.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemType implements StringEnum {
    EQUIP("EQUIP", "装备"),
    USE("USE", "消耗品"),
    ETC("ETC", "其他"),
    OTHER("OTHER", "未分类"),
    ;

    private final String code;
    private final String name;
}

