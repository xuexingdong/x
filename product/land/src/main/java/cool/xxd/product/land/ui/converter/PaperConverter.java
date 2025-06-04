package cool.xxd.product.land.ui.converter;

import cool.xxd.infra.mapstruct.CommonMapperConfig;
import cool.xxd.product.land.domain.command.AddPaperCommand;
import cool.xxd.product.land.ui.request.AddPaperRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface PaperConverter {
    PaperConverter INSTANCE = Mappers.getMapper(PaperConverter.class);

    AddPaperCommand request2command(AddPaperRequest addPaperRequest);
}
