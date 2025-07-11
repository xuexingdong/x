package cool.xxd.product.msw.application.service;

import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.aggregate.MobItem;
import cool.xxd.product.msw.domain.command.AddMobCommand;
import cool.xxd.product.msw.domain.command.AddMobItemCommand;
import cool.xxd.product.msw.domain.factory.MobFactory;
import cool.xxd.product.msw.domain.factory.MobItemFactory;
import cool.xxd.product.msw.domain.query.MobQuery;
import cool.xxd.product.msw.domain.repository.ItemRepository;
import cool.xxd.product.msw.domain.repository.MobItemRepository;
import cool.xxd.product.msw.domain.repository.MobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MobService {

    private final MobFactory mobFactory;
    private final MobItemFactory mobItemFactory;
    private final MobRepository mobRepository;
    private final MobItemRepository mobItemRepository;
    private final ItemRepository itemRepository;
    private final TransactionTemplate transactionTemplate;

    public void addMobs(List<AddMobCommand> addMobCommands) {
        var mobCodes = addMobCommands.stream().map(AddMobCommand::getCode).distinct().toList();
        var existMobs = mobRepository.findByCodes(mobCodes);

        var existMobCodes = existMobs.stream()
                .map(Mob::getCode)
                .distinct()
                .toList();
        var partitionedCommands = addMobCommands.stream()
                .collect(Collectors.partitioningBy(command -> existMobCodes.contains(command.getCode())));

        var existingCommandMap = partitionedCommands.get(true).stream()
                .collect(Collectors.toMap(AddMobCommand::getCode, Function.identity()));
        for (Mob existMob : existMobs) {
            var addMobCommand = existingCommandMap.get(existMob.getCode());
            existMob.update(addMobCommand);
        }
        var newCommands = partitionedCommands.get(false);
        var mobs = mobFactory.createMob(newCommands);
        mobRepository.saveAll(mobs);
        for (Mob existMob : existMobs) {
            mobRepository.update(existMob);
        }
    }

    public List<Mob> queryMobs(MobQuery mobQuery) {
        return mobRepository.query(mobQuery);
    }

    public Map<String, List<Item>> matchItems(List<Mob> mobs) {
        if (mobs.isEmpty()) {
            return Map.of();
        }
        var mobCodes = mobs.stream().map(Mob::getCode).distinct().toList();
        var mobItems = mobItemRepository.findByMobCodes(mobCodes);
        var itemCodes = mobItems.stream()
                .map(MobItem::getItemCode)
                .distinct()
                .toList();
        var itemMap = itemRepository.findByCodes(itemCodes).stream()
                .collect(Collectors.toMap(Item::getCode, Function.identity()));
        var mobItemsMap = mobItems.stream()
                .collect(Collectors.groupingBy(MobItem::getMobCode,
                        Collectors.mapping(mobItem -> itemMap.get(mobItem.getItemCode()),
                                Collectors.toList())));
        // 需要从mobs重新处理，因为mobItemsMap只会包含存在mobItem关系的数据
        return mobs.stream()
                .collect(Collectors.toMap(
                        Mob::getCode,
                        mob -> mobItemsMap.getOrDefault(mob.getCode(), List.of())
                ));
    }

    public void addMobItems(List<AddMobItemCommand> addMobItemCommands) {
        var mobNames = addMobItemCommands.stream()
                .map(AddMobItemCommand::getMobName)
                .distinct()
                .toList();
        var itemNames = addMobItemCommands.stream()
                .flatMap(addMobItemCommand -> addMobItemCommand.getItemName().stream())
                .distinct().toList();
        var mobNameMap = mobRepository.findByNames(mobNames)
                .stream().collect(Collectors.toMap(Mob::getName, Function.identity()));
        var itemNameMap = itemRepository.findByNames(itemNames)
                .stream().collect(Collectors.toMap(Item::getName, Function.identity()));
        for (AddMobItemCommand addMobItemCommand : addMobItemCommands) {
            var mob = mobNameMap.get(addMobItemCommand.getMobName());
            var itemCodes = addMobItemCommand.getItemName().stream()
                    .map(itemName -> {
                        if (itemNameMap.containsKey(itemName)) {
                            return itemNameMap.get(itemName).getCode();
                        }
                        return null;
                    }).filter(Objects::nonNull).toList();
            if (mob != null) {
                var mobItems = mobItemFactory.createMobItems(mob.getCode(), itemCodes);
                mobItemRepository.saveAll(mobItems);
            }
        }
    }
}
