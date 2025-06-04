package cool.xxd.product.land.infra.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.infra.model.QuestionDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface QuestionConverter extends ObjectConverter<Question, QuestionDO> {
    QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);
}
