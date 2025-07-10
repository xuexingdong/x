package cool.xxd.product.msw.adapter.in.http;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.application.dto.request.AddItemRequest;
import cool.xxd.product.msw.domain.command.AddItemCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface ItemConverter {
    ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);

    AddItemCommand request2command(AddItemRequest addItemRequest);

    List<AddItemCommand> request2command(List<AddItemRequest> addItemRequests);
}

