package cool.xxd.service.pay.domain.strategy.alipay.api.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundChargeEnum implements CommonEnum<String> {
    Y("Y", "退款成功"),
    N("N", "退款状态未知"),
    ;

    private final String code;
    private final String name;
}
