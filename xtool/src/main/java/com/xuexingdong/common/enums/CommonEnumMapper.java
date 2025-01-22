package cool.xxd.mapstruct.enums;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

@Mapper
public interface CommonEnumMapper {
    default <E extends CommonEnum<Integer>> E integer2Enum(Integer code, @TargetType Class<E> clazz) {
        if (code == null) {
            return null;
        }
        for (E enumConstant : clazz.getEnumConstants()) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }

    default <E extends CommonEnum<Integer>> Integer enum2Integer(E e) {
        if (e == null) {
            return null;
        }
        return e.getCode();
    }

    default <E extends CommonEnum<String>> E string2Enum(String code, @TargetType Class<E> clazz) {
        if (code == null) {
            return null;
        }
        for (E enumConstant : clazz.getEnumConstants()) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }


    default <E extends CommonEnum<String>> String enum2String(E e) {
        if (e == null) {
            return null;
        }
        return e.getCode();
    }
}
