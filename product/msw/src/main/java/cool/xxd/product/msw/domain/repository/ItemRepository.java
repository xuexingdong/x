package cool.xxd.product.msw.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.query.ItemQuery;

import java.util.List;

public interface ItemRepository extends BaseRepository<Item, Long> {

    void saveAll(List<Item> items);

    List<Item> query(ItemQuery itemQuery);

    List<Item> findByCodes(List<String> itemCodes);

    List<Item> findByNames(List<String> itemNames);
}
