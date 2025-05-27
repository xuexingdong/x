package cool.xxd.service.pay.domain.strategy.fuiou.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderTypeEnum implements CommonEnum<String> {
    ALIPAY("ALIPAY", "统一下单、条码支付、服务窗支付"),
    WECHAT("WECHAT", "统一下单、条码支付、公众号支付、小程序"),
    UNIONPAY("UNIONPAY", "云闪付"),
    BESTPAY("BESTPAY", "翼支付"),
    PY68("PY68", "银联分期-商户贴息"),
    PY69("PY69", "银联分期-持卡人贴息"),
    WXXS("WXXS", "下单类型为LPXS时使用"),
    JDBT("JDBT", "京东白条"),
    ;

    private final String code;
    private final String name;
}
