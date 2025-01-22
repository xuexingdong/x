package cool.xxd.mapstruct.enums;

import java.util.Arrays;

public interface CommonEnum<Code> {
    Code getCode();

    static <E extends CommonEnum<Code>, Code> E of(Class<E> enumClass, Code code) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);  // 如果找不到则返回 null
    }
}