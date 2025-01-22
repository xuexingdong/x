package cool.xxd.product.land.infra.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;
import cool.xxd.product.land.infra.model.IdiomQuestionRelationDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface IdiomQuestionRelationConverter extends ObjectConverter<IdiomQuestionRelation, IdiomQuestionRelationDO> {
    IdiomQuestionRelationConverter INSTANCE = Mappers.getMapper(IdiomQuestionRelationConverter.class);
}
