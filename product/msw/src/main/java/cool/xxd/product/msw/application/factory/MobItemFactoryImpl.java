package cool.xxd.product.msw.application.factory;

import cool.xxd.infra.X;
import cool.xxd.product.msw.domain.aggregate.MobItem;
import cool.xxd.product.msw.domain.factory.MobItemFactory;
import cool.xxd.product.msw.infra.model.MobItemDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MobItemFactoryImpl implements MobItemFactory {
    @Override
    public List<MobItem> createMobItems(String code, List<String> itemCodes) {
        if (itemCodes.isEmpty()) {
            return List.of();
        }
        return itemCodes.stream()
                .map(itemCode -> {
                    var id = X.id.nextId(MobItemDO.class);
                    var mobItem = new MobItem();
                    mobItem.setId(id);
                    mobItem.setMobCode(code);
                    mobItem.setItemCode(itemCode);
                    return mobItem;
                }).toList();
    }
}
