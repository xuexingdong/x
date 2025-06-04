package cool.xxd.product.msw.infra.converter;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.aggregate.MobItem;
import cool.xxd.product.msw.infra.model.MobItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MobItemConverter {
    MobItemConverter INSTANCE = Mappers.getMapper(MobItemConverter.class);

    MobItemDO domain2do(MobItem mobItem);

    List<MobItemDO> domain2do(List<MobItem> mobItems);

    Mob do2domain(MobItemDO mobItemDO);

    List<MobItem> do2domain(List<MobItemDO> mobItemDOList);
}
