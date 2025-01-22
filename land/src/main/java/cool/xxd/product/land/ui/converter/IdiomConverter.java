package cool.xxd.product.land.ui.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.infra.mapstruct.ObjectConverter;
import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.command.AddIdiomCommand;
import cool.xxd.product.land.ui.request.AddIdiomRequest;
import cool.xxd.product.land.ui.vo.IdiomCardVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(config = CommonMapperConfig.class)
public interface IdiomConverter extends ObjectConverter<Idiom, IdiomCardVO> {
    IdiomConverter INSTANCE = Mappers.getMapper(IdiomConverter.class);

    AddIdiomCommand request2command(AddIdiomRequest addIdiomRequest);

    IdiomCardVO toVO(Idiom idiom);
}
