package cool.xxd.product.land.ui.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.ui.vo.QuestionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface QuestionConverter extends ObjectConverter<Question, QuestionVO> {
    QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);
}
