package cool.xxd.product.msw.infra.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.query.MobQuery;
import cool.xxd.product.msw.domain.repository.MobRepository;
import cool.xxd.product.msw.infra.converter.MobConverter;
import cool.xxd.product.msw.infra.mapper.MobMapper;
import cool.xxd.product.msw.infra.model.MobDO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MobRepositoryImpl implements MobRepository {

    private final MobMapper mobMapper;

    @Override
    public void saveAll(List<Mob> mobs) {
        var mobDOList = MobConverter.INSTANCE.domain2do(mobs);
        mobMapper.insert(mobDOList);
    }

    @Override
    public void deleteAll() {
        mobMapper.delete(null);
    }

    @Override
    public void update(Mob mob) {
        var mobDO = MobConverter.INSTANCE.domain2do(mob);
        mobMapper.updateById(mobDO);
    }

    @Override
    public List<Mob> query(MobQuery mobQuery) {
        var queryWrapper = Wrappers.lambdaQuery(MobDO.class)
                .like(StringUtils.isNoneBlank(mobQuery.getName()), MobDO::getName, mobQuery.getName());
        var mobDOList = mobMapper.selectList(queryWrapper);
        return MobConverter.INSTANCE.do2domain(mobDOList);
    }

    @Override
    public List<Mob> findByCodes(List<String> mobCodes) {
        if (mobCodes.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(MobDO.class)
                .in(MobDO::getCode, mobCodes);
        var mobDOList = mobMapper.selectList(queryWrapper);
        return MobConverter.INSTANCE.do2domain(mobDOList);
    }

    @Override
    public List<Mob> findByNames(List<String> mobNames) {
        if (mobNames.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(MobDO.class)
                .in(MobDO::getName, mobNames);
        var mobDOList = mobMapper.selectList(queryWrapper);
        return MobConverter.INSTANCE.do2domain(mobDOList);
    }
}
