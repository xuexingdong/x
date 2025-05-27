package cool.xxd.service.pay.domain.strategy.alipay.api.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SceneEnum implements CommonEnum<String> {
    BAR_CODE("bar_code", "当面付条码支付场景"),
    SECURITY_CODE("security_code", "当面付刷脸支付场景"),
    ;

    private final String code;
    private final String name;
}
