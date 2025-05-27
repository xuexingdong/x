package cool.xxd.service.pay.domain.strategy.jialian.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JialianPayTypeEnum implements CommonEnum<String> {
    ACTIVE_SCAN("activeScan", "聚合支付主扫"),
    PASSIVE_SCAN("passiveScan", "聚合支付被扫"),
    ALI_PAY("aliPay", "支付宝支付"),
    WECHAT_PAY("wechatPay", "微信支付"),
    ;

    private final String code;
    private final String name;
}
