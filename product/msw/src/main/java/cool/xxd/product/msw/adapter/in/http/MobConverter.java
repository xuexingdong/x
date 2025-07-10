package cool.xxd.product.msw.adapter.in.http;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.application.dto.request.AddMobItemRequest;
import cool.xxd.product.msw.application.dto.request.AddMobRequest;
import cool.xxd.product.msw.domain.command.AddMobCommand;
import cool.xxd.product.msw.domain.command.AddMobItemCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MobConverter {
    MobConverter INSTANCE = Mappers.getMapper(MobConverter.class);

    AddMobCommand request2command(AddMobRequest addMobRequest);

    List<AddMobCommand> request2command(List<AddMobRequest> addMobRequests);

    AddMobItemCommand mobItemsRequest2command(AddMobItemRequest addMobItemRequest);

    List<AddMobItemCommand> mobItemsRequest2command(List<AddMobItemRequest> addMobItemRequest);
}

