package cool.xxd.service.pay.domain.strategy.alipay.api.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCodeEnum implements CommonEnum<String> {
    JSAPI_PAY("JSAPI_PAY", "JSAPI_PAY"),
    OFFLINE_PAYMENT("OFFLINE_PAYMENT,", "当面付快捷版"),
    FACE_TO_FACE_PAYMENT("FACE_TO_FACE_PAYMENT", "其它支付宝当面付产品"),
    ;

    private final String code;
    private final String name;
}
