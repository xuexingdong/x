package cool.xxd.service.user.domain.enums;

import cool.xxd.infra.enums.IntegerEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType implements IntegerEnum {
    USERNAME(1, "账号"),
    EMAIL(2, "邮箱"),
    ;
    private final Integer code;
    private final String name;
}
