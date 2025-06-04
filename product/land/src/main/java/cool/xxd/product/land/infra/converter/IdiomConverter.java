package cool.xxd.product.land.infra.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.infra.model.IdiomDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface IdiomConverter extends ObjectConverter<Idiom, IdiomDO> {
    IdiomConverter INSTANCE = Mappers.getMapper(IdiomConverter.class);
}
