package cool.xxd.service.pay.application.converter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import cool.xxd.service.pay.application.model.AppDO;
import cool.xxd.service.pay.domain.aggregate.App;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper(uses = {CommonEnumMapper.class})
public interface AppConverter {
    AppConverter INSTANCE = Mappers.getMapper(AppConverter.class);

    App do2domain(AppDO appDO);

    List<App> do2domain(List<AppDO> appDOList);

    AppDO domain2do(App app);

    List<AppDO> domain2do(List<App> apps);

    default Map<String, String> string2map(String value) {
        if (value == null) {
            return null;
        }
        return JSON.parseObject(value, new TypeReference<>() {
        });
    }

    default String map2string(Map<String, String> value) {
        if (value == null) {
            return null;
        }
        return JSON.toJSONString(value);
    }
}
