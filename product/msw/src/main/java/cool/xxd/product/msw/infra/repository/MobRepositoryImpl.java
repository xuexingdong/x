package cool.xxd.product.msw.infra.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.query.MobQuery;
import cool.xxd.product.msw.domain.repository.MobRepository;
import cool.xxd.product.msw.infra.converter.MobConverter;
import cool.xxd.product.msw.infra.mapper.MobMapper;
import cool.xxd.product.msw.infra.model.MobDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MobRepositoryImpl implements MobRepository {

    private final MobMapper mobMapper;

    @Override
    public List<Mob> query(MobQuery mobQuery) {
        var queryWrapper = Wrappers.lambdaQuery(MobDO.class)
                .like(MobDO::getName, mobQuery.getName());
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
}
