package cool.xxd.product.msw.infra.converter;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.infra.model.ItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface ItemConverter {
    ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);

    ItemDO domain2do(Item item);

    List<ItemDO> domain2do(List<Item> items);

    Item do2domain(ItemDO itemDO);

    List<Item> do2domain(List<ItemDO> itemDOList);
}
