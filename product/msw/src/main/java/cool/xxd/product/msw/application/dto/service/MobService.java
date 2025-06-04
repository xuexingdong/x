package cool.xxd.product.msw.application.dto.service;

import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.aggregate.MobItem;
import cool.xxd.product.msw.domain.query.MobQuery;
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
public class MobService {

    private final MobRepository mobRepository;
    private final MobItemRepository mobItemRepository;
    private final ItemRepository itemRepository;

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
}
