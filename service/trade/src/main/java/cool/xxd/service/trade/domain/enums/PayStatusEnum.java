package cool.xxd.service.trade.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayStatusEnum implements StringEnum {
    UNPAID("UNPAID", "待支付"),
    PARTIALLY_PAID("PARTIALLY_PAID", "部分支付"),
    PAID("PAID", "已支付"),
    PARTIALLY_REFUNDED("PARTIALLY_REFUNDED", "部分退款"),
    REFUNDED("REFUNDED", "已退款"),
    CLOSED("CLOSED", "已关闭"),
    ;

    private final String code;
    private final String name;
}
