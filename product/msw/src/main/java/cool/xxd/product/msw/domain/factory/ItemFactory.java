package cool.xxd.product.msw.domain.factory;

import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.command.AddItemCommand;

import java.util.List;

public interface ItemFactory {
    List<Item> createItem(List<AddItemCommand> addItemCommands);
}
