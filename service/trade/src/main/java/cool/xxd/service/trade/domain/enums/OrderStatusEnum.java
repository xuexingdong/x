package cool.xxd.service.trade.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum implements StringEnum {
    WAIT_ACCEPT("WAIT_ACCEPT", "待接单"),
    ACCEPTED("ACCEPTED", "已接单"),
    PREPARED("PREPARED", "已出餐"),
    CANCELED("CANCELED", "已取消"),
    COMPLETED("COMPLETED", "已完成"),
    ;

    private final String code;
    private final String name;
}
