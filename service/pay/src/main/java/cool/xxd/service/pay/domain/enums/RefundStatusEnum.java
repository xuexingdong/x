package cool.xxd.service.pay.domain.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RefundStatusEnum implements CommonEnum<String> {
    INIT("INIT", "未退款"),
    PROCESSING("PROCESSING", "退款中"),
    SUCCESS("SUCCESS", "退款成功"),
    FAILED("FAILED", "退款失败"),
    ;

    private final String code;
    private final String name;

}

