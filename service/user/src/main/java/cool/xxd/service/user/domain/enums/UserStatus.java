package cool.xxd.service.user.domain.enums;

import cool.xxd.infra.enums.IntegerEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements IntegerEnum {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    ;
    private final Integer code;
    private final String name;
}
