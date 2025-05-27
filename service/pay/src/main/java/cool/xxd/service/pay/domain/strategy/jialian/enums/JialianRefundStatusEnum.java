package cool.xxd.service.pay.domain.strategy.jialian.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JialianRefundStatusEnum implements CommonEnum<Integer> {
    INIT(0, "已发起"),
    SUCCESS(1, "退款成功"),
    FAILED(2, "退款失败"),
    ;

    private final Integer code;
    private final String name;
}
