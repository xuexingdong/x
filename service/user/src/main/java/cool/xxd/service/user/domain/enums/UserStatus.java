package cool.xxd.service.user.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements StringEnum {
    UNVERIFIED("UNVERIFIED", "未验证"),
    ACTIVE("ACTIVE", "正常"),
    LOCKED("LOCKED", "锁定"),
    DISABLED("DISABLED", "禁用"),
    ;
    private final String code;
    private final String name;
}
