package cool.xxd.product.msw.application.service;

import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.aggregate.MobItem;
import cool.xxd.product.msw.domain.command.AddItemCommand;
import cool.xxd.product.msw.domain.factory.ItemFactory;
import cool.xxd.product.msw.domain.query.ItemQuery;
import cool.xxd.product.msw.domain.repository.ItemRepository;
import cool.xxd.product.msw.domain.repository.MobItemRepository;
import cool.xxd.product.msw.domain.repository.MobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemFactory itemFactory;
    private final ItemRepository itemRepository;
    private final MobRepository mobRepository;
    private final MobItemRepository mobItemRepository;

    public void addItems(List<AddItemCommand> addItemCommands) {
        itemRepository.deleteAll();
        // 获取所有要处理的item代码
        var itemCodes = addItemCommands.stream()
                .map(AddItemCommand::getCode)
                .distinct()
                .toList();

        // 查询数据库中已存在的item
        var existItems = itemRepository.findByCodes(itemCodes);

        // 获取已存在的item代码列表
        var existItemCodes = existItems.stream()
                .map(Item::getCode)
                .distinct()
                .toList();

        // 根据是否存在分区命令
        var partitionedCommands = addItemCommands.stream()
                .collect(Collectors.partitioningBy(command -> existItemCodes.contains(command.getCode())));

        // 处理更新操作 - 将存在的命令转换为Map便于查找
        var existingCommandMap = partitionedCommands.get(true).stream()
                .collect(Collectors.toMap(AddItemCommand::getCode, Function.identity()));

        // 更新已存在的item
        for (Item existItem : existItems) {
            var addItemCommand = existingCommandMap.get(existItem.getCode());
            existItem.update(addItemCommand);
        }

        // 处理新增操作
        var newCommands = partitionedCommands.get(false);
        var newItems = itemFactory.createItem(newCommands);
        checkDuplicateIds(newItems);
        // 保存新创建的item
        itemRepository.saveAll(newItems);

        // 更新已存在的item
        for (Item existItem : existItems) {
            itemRepository.update(existItem);
        }
    }

    // 在 ItemService 类中添加这个方法
    private void checkDuplicateIds(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        // 统计每个ID出现的次数
        var idCounts = items.stream()
                .collect(Collectors.groupingBy(Item::getId, Collectors.counting()));

        // 找出重复的ID
        var duplicateIds = idCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        if (!duplicateIds.isEmpty()) {
            System.err.println("❌ 发现重复的ID: " + duplicateIds);
            System.err.println("重复ID的Items:");
            items.stream()
                    .filter(item -> duplicateIds.contains(item.getId()))
                    .forEach(item -> System.err.println("  ID: " + item.getId() + ", Code: " + item.getCode() + ", Name: " + item.getName()));
            throw new RuntimeException("发现重复的ID，无法继续插入数据");
        }

        System.out.println("✅ 没有发现重复的ID");
    }

    public List<Item> queryItems(ItemQuery itemQuery) {
        return itemRepository.query(itemQuery);
    }

    public Map<String, List<Mob>> matchMobs(List<Item> items) {
        if (items.isEmpty()) {
            return Map.of();
        }
        var itemCodes = items.stream().map(Item::getCode).distinct().toList();
        var mobItems = mobItemRepository.findByItemCodes(itemCodes);
        var mobCodes = mobItems.stream()
                .map(MobItem::getMobCode)
                .distinct()
                .toList();
        var mobMap = mobRepository.findByCodes(mobCodes).stream()
                .collect(Collectors.toMap(Mob::getCode, Function.identity()));
        var itemMobsMap = mobItems.stream()
                .collect(Collectors.groupingBy(MobItem::getItemCode,
                        Collectors.mapping(mobItem -> mobMap.get(mobItem.getMobCode()),
                                Collectors.toList())));
        // 需要从items重新处理，因为itemMobsMap只会包含存在mobItem关系的数据
        return items.stream()
                .collect(Collectors.toMap(
                        Item::getCode,
                        item -> itemMobsMap.getOrDefault(item.getCode(), List.of())
                ));
    }
}
