package cool.xxd.product.land.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndexStatus implements StringEnum {
    UN_INDEXED("UN_INDEXED", "未索引"),
    INDEXING("INDEXING", "索引中"),
    INDEXED("INDEXED", "已索引"),
    ;

    private final String code;
    private final String name;
}
