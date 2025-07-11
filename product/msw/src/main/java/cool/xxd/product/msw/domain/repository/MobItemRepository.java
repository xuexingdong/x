package cool.xxd.product.msw.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.msw.domain.aggregate.MobItem;

import java.util.List;

public interface MobItemRepository extends BaseRepository<MobItem, Long> {

    void saveAll(List<MobItem> mobItems);

    void deleteAll();

    List<MobItem> findByMobCodes(List<String> mobCodes);

    List<MobItem> findByItemCodes(List<String> itemCodes);
}
