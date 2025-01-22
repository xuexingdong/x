package cool.xxd.product.land.infra.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.infra.model.PaperDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface PaperConverter extends ObjectConverter<Paper, PaperDO> {
    PaperConverter INSTANCE = Mappers.getMapper(PaperConverter.class);
}
