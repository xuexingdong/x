package cool.xxd.service.pay.domain.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MerchantApplyStatusEnum implements StringEnum {

    PENDING("PENDING", "待审核"),
    APPROVED("APPROVED", "已通过"),
    REJECTED("REJECTED", "已拒绝"),
    ;

    private final String code;
    private final String name;

}

