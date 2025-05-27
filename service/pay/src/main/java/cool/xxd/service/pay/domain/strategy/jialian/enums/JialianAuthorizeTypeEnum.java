package cool.xxd.service.pay.domain.strategy.jialian.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JialianAuthorizeTypeEnum implements CommonEnum<String> {
    AUTH_CODE("authCode", "authCode"),
    // REFRESH_TOKEN("refreshToken", "refreshToken"),
    ;

    private final String code;
    private final String name;
}
