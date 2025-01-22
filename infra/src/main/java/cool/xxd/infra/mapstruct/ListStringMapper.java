package cool.xxd.infra.mapstruct;

import com.alibaba.fastjson2.JSON;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ListStringMapper {

    default String stringList2string(List<String> list) {
        if (list == null) {
            return null;
        }
        return JSON.toJSONString(list);
    }

    default List<String> string2stringList(String s) {
        if (s == null) {
            return null;
        }
        return JSON.parseArray(s, String.class);
    }


    default String longList2String(List<Long> list) {
        if (list == null) {
            return null;
        }
        return JSON.toJSONString(list);
    }

    default List<Long> string2longList(String s) {
        if (s == null) {
            return null;
        }
        return JSON.parseArray(s, Long.class);
    }

}
