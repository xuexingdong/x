package cool.xxd.product.msw.adapter.in.mcp;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.application.dto.response.ItemQueryResponse;
import cool.xxd.product.msw.application.dto.response.ItemResponse;
import cool.xxd.product.msw.domain.aggregate.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface ItemConverter {
    ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);

    ItemResponse domain2response(Item item);

    List<ItemResponse> domain2response(List<Item> items);

    ItemQueryResponse domain2queryResponse(Item item);

    List<ItemQueryResponse> domain2queryResponse(List<Item> items);
}

