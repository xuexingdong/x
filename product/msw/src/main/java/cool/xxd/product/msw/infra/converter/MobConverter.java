package cool.xxd.product.msw.infra.converter;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.infra.model.MobDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MobConverter {
    MobConverter INSTANCE = Mappers.getMapper(MobConverter.class);

    MobDO domain2do(Mob mob);

    List<MobDO> domain2do(List<Mob> mobs);

    Mob do2domain(MobDO mobDO);

    List<Mob> do2domain(List<MobDO> mobDOList);
}
