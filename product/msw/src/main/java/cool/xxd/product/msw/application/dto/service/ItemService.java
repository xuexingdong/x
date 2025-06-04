package cool.xxd.product.msw.application.dto.service;

import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.aggregate.MobItem;
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

    private final MobRepository mobRepository;
    private final MobItemRepository mobItemRepository;
    private final ItemRepository itemRepository;

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
