package cool.xxd.product.msw.domain.factory;

import cool.xxd.product.msw.domain.aggregate.MobItem;

import java.util.List;

public interface MobItemFactory {
    List<MobItem> createMobItems(String code, List<String> itemCodes);
}
