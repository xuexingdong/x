package cool.xxd.service.pay.domain.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayStatusEnum implements CommonEnum<String> {
    UNPAID("UNPAID", "待支付"),
    PAID("PAID", "已支付"),
    FAILED("FAILED", "支付失败"),
    PAYING("PAYING", "支付中"),
    CLOSED("CLOSED", "关闭"),
    PARTIALLY_REFUNDED("PARTIALLY_REFUNDED", "部分退款"),
    REFUNDED("REFUNDED", "已退款"),
    REVOKED("REVOKED", "已撤销"),
    ;

    private final String code;
    private final String name;

}

