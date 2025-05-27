package cool.xxd.service.pay.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayTypeEnum implements StringEnum {
    WECHAT_PAY("WECHAT_PAY", "微信支付"),
    ALIPAY("ALIPAY", "支付宝"),
    // TODO 转现金未完善，比如微信支付转现金，逆订单的payType和channel都需要变更
    CASH("CASH", "现金"),
    ;

    private final String code;
    private final String name;
}

