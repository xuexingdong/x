package cool.xxd.service.pay.domain.strategy.alipay.api.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettleCurrencyEnum implements CommonEnum<String> {
    GBP("GBP", "英镑"),
    HKD("HKD", "港币"),
    USD("USD", "美元"),
    SGD("SGD", "新加坡元"),
    JPY("JPY", "日元"),
    CAD("CAD", "加拿大元"),
    AUD("AUD", "澳元"),
    EUR("EUR", "欧元"),
    NZD("NZD", "新西兰元"),
    KRW("KRW", "韩元"),
    THB("THB", "泰铢"),
    CHF("CHF", "瑞士法郎"),
    SEK("SEK", "瑞典克朗"),
    DKK("DKK", "丹麦克朗"),
    NOK("NOK", "挪威克朗"),
    MYR("MYR", "马来西亚林吉特"),
    IDR("IDR", "印尼卢比"),
    PHP("PHP", "菲律宾比索"),
    MUR("MUR", "毛里求斯卢比"),
    ILS("ILS", "以色列新谢克尔"),
    LKR("LKR", "斯里兰卡卢比"),
    RUB("RUB", "俄罗斯卢布"),
    AED("AED", "阿联酋迪拉姆"),
    CZK("CZK", "捷克克朗"),
    ZAR("ZAR", "南非兰特"),
    CNY("CNY", "人民币"),
    TWD("TWD", "新台币"),
    ;

    private final String code;
    private final String name;
}
