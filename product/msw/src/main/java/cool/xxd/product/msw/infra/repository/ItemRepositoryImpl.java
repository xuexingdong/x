package cool.xxd.product.msw.infra.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.msw.domain.aggregate.Item;
import cool.xxd.product.msw.domain.query.ItemQuery;
import cool.xxd.product.msw.domain.repository.ItemRepository;
import cool.xxd.product.msw.infra.converter.ItemConverter;
import cool.xxd.product.msw.infra.mapper.ItemMapper;
import cool.xxd.product.msw.infra.model.ItemDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemMapper itemMapper;

    @Override
    public List<Item> query(ItemQuery itemQuery) {
        var queryWrapper = Wrappers.lambdaQuery(ItemDO.class)
                .like(ItemDO::getName, itemQuery.getName());
        var itemDOList = itemMapper.selectList(queryWrapper);
        return ItemConverter.INSTANCE.do2domain(itemDOList);
    }

    @Override
    public List<Item> findByCodes(List<String> itemCodes) {
        if (itemCodes.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(ItemDO.class)
                .in(ItemDO::getCode, itemCodes);
        var itemDOList = itemMapper.selectList(queryWrapper);
        return ItemConverter.INSTANCE.do2domain(itemDOList);
    }
}
