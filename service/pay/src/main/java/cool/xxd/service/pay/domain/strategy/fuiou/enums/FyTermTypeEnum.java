package cool.xxd.service.pay.domain.strategy.fuiou.enums;

import cool.xxd.infra.enums.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FyTermTypeEnum implements CommonEnum<String> {
    OTHER("0", "其他"),
    FY_TERMINAL("1", "富友终端"),
    POS("2", "POS机"),
    TAI_KA("3", "台卡"),
    PC_SOFT_WARE("4", "PC软件");
    ;

    private final String code;
    private final String name;
}
