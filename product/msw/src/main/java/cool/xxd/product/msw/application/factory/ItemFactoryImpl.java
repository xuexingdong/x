package cool.xxd.product.msw.application.factory;

import cool.xxd.infra.X;
import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.command.AddItemCommand;
import cool.xxd.product.msw.domain.factory.ItemFactory;
import cool.xxd.product.msw.infra.model.ItemDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemFactoryImpl implements ItemFactory {
    @Override
    public List<Item> createItem(List<AddItemCommand> addItemCommands) {
        return addItemCommands.stream()
                .map(addItemCommand -> {
                    var id = X.id.nextId(ItemDO.class);
                    var item = new Item();
                    item.setId(id);
                    item.setCode(addItemCommand.getCode());
                    item.setName(addItemCommand.getName());
                    item.setItemType(addItemCommand.getItemType());
                    item.setItemTypeName(addItemCommand.getItemTypeName());
                    return item;
                }).toList();
    }
}
