package cool.xxd.service.pay.domain.strategy.jialian.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JialianPayStatusEnum implements CommonEnum<Integer> {
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    CANCELED(2, "已取消"),
    PARTIALLY_REFUNDED(3, "部分退款"),
    REFUNDED(4, "全额退款"),
    ;

    private final Integer code;
    private final String name;
}
