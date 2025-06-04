package cool.xxd.product.land.domain.enums;

import cool.xxd.infra.enums.IntegerEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum XingceModule implements IntegerEnum {
    ZHENGZHI(1, "政治理论"),
    CHANGSHI(2, "常识判断"),
    YANYU(3, "言语理解与表达"),
    SHULIANG(4, "数量关系"),
    PANDUAN(5, "判断推理"),
    ZILIAO(6, "资料分析"),
    ;

    private final Integer code;
    private final String name;

    public static XingceModule fromName(String name) {
        for (XingceModule module : XingceModule.values()) {
            if (module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }
}
