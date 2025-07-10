package cool.xxd.infra.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface CommonEnum<C> {
    C getCode();

    /**
     * 根据code获取枚举实例
     *
     * @param enumClass 枚举类
     * @param code      编码
     * @param <E>       枚举类型
     * @param <C>       编码类型
     * @return 枚举实例，未找到返回null
     */
    static <E extends CommonEnum<C>, C> E of(Class<E> enumClass, C code) {
        requireNonNull(enumClass);
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(code, e.getCode()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 批量根据code获取枚举实例
     *
     * @param enumClass 枚举类
     * @param codes     编码列表
     * @param <E>       枚举类型
     * @param <C>       编码类型
     * @return 枚举实例列表，永不为null
     */
    static <E extends CommonEnum<C>, C> List<E> of(Class<E> enumClass, List<C> codes) {
        Objects.requireNonNull(enumClass, "enumClass cannot be null");
        return Optional.ofNullable(codes)
                .map(list -> list.stream()
                        .map(code -> CommonEnum.of(enumClass, code))
                        .filter(Objects::nonNull)
                        .toList())
                .orElse(List.of());
    }

    private static <E extends CommonEnum<C>, C> void requireNonNull(Class<E> enumClass) {
        Objects.requireNonNull(enumClass, "enumClass cannot be null");
    }
}