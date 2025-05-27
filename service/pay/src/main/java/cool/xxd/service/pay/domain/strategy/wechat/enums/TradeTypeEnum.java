package cool.xxd.service.pay.domain.strategy.wechat.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradeTypeEnum implements CommonEnum<String> {
    MICROPAY("MICROPAY", "扫码支付"),
    ;

    private final String code;
    private final String name;
}
