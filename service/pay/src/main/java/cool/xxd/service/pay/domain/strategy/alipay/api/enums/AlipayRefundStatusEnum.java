package cool.xxd.service.pay.domain.strategy.alipay.api.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlipayRefundStatusEnum implements CommonEnum<String> {
    REFUND_SUCCESS("REFUND_SUCCESS", "退款处理成功"),
    ;

    private final String code;
    private final String name;
}
