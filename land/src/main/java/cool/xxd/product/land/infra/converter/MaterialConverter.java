package cool.xxd.product.land.infra.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.aggregate.Material;
import cool.xxd.product.land.infra.model.MaterialDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface MaterialConverter extends ObjectConverter<Material, MaterialDO> {
    MaterialConverter INSTANCE = Mappers.getMapper(MaterialConverter.class);
}
