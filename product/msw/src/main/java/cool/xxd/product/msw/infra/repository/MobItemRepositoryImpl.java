package cool.xxd.product.msw.infra.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.msw.domain.aggregate.MobItem;
import cool.xxd.product.msw.domain.repository.MobItemRepository;
import cool.xxd.product.msw.infra.converter.MobItemConverter;
import cool.xxd.product.msw.infra.mapper.MobItemMapper;
import cool.xxd.product.msw.infra.model.MobItemDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MobItemRepositoryImpl implements MobItemRepository {

    private final MobItemMapper mobItemMapper;

    @Override
    public void saveAll(List<MobItem> mobItems) {
        var mobItemDOList = MobItemConverter.INSTANCE.domain2do(mobItems);
        mobItemMapper.insert(mobItemDOList);
    }

    @Override
    public void deleteAll() {
        mobItemMapper.delete(null);
    }

    @Override
    public List<MobItem> findByMobCodes(List<String> mobCodes) {
        if (mobCodes.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(MobItemDO.class)
                .in(MobItemDO::getMobCode, mobCodes);
        var mobItemDOList = mobItemMapper.selectList(queryWrapper);
        return MobItemConverter.INSTANCE.do2domain(mobItemDOList);
    }

    @Override
    public List<MobItem> findByItemCodes(List<String> itemCodes) {
        if (itemCodes.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(MobItemDO.class)
                .in(MobItemDO::getItemCode, itemCodes);
        var mobItemDOList = mobItemMapper.selectList(queryWrapper);
        return MobItemConverter.INSTANCE.do2domain(mobItemDOList);
    }
}
