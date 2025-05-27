package cool.xxd.service.pay.domain.strategy.jialian.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JialianRefundTypeEnum implements CommonEnum<Integer> {
    FULL_REFUND(1, "全额退款"),
    PARTIAL_REFUND(2, "部分退款"),
    ;

    private final Integer code;
    private final String name;
}
