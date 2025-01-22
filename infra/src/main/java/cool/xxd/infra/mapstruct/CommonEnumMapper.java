package cool.xxd.infra.mapstruct;

import cool.xxd.infra.enums.IntegerEnum;
import cool.xxd.infra.enums.StringEnum;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

@Mapper
public interface CommonEnumMapper {
    default <E extends IntegerEnum> E integer2Enum(Integer code, @TargetType Class<E> clazz) {
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

    default <E extends IntegerEnum> Integer enum2Integer(E e) {
        if (e == null) {
            return null;
        }
        return e.getCode();
    }

    default <E extends StringEnum> E string2Enum(String code, @TargetType Class<E> clazz) {
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

    default <E extends StringEnum> String enum2String(E e) {
        if (e == null) {
            return null;
        }
        return e.getCode();
    }
}
