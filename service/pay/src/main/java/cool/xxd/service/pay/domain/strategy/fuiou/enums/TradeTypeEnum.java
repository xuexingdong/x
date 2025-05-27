package cool.xxd.service.pay.domain.strategy.fuiou.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradeTypeEnum implements CommonEnum<String> {
    JSAPI("JSAPI", "公众号支付"),
    FWC("FWC", "支付宝服务窗、支付宝小程序"),
    LETPAY("LETPAY", "微信小程序"),
    BESTPAY("BESTPAY", "翼支付js"),
    MPAY("MPAY", "云闪付小程序（控件支付）"),
    UNIONPAY("UNIONPAY", "云闪付扫码"),
    UPBXJS("UPBXJS", "云闪付保险缴费"),
    LPXS("LPXS", "小程序线上"),
    JDBT("JDBT", "京东白条"),
    ;

    private final String code;
    private final String name;

}
