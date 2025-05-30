package cool.xxd.service.pay.application.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.service.pay.application.converter.AppConverter;
import cool.xxd.service.pay.application.mapper.AppMapper;
import cool.xxd.service.pay.application.model.AppDO;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppRepositoryImpl implements AppRepository {

    private final AppMapper appMapper;

    @Override
    public void save(App app) {
        var appDO = AppConverter.INSTANCE.domain2do(app);
        appMapper.insert(appDO);
    }

    @Override
    public void deleteById(Long id) {
        var appDO = new AppDO();
        appDO.setId(id);
        appMapper.deleteById(appDO);
    }

    @Override
    public void update(App app) {
        var appDO = AppConverter.INSTANCE.domain2do(app);
        appMapper.updateById(appDO);
    }

    @Override
    public Optional<App> findById(Long id) {
        var appDO = appMapper.selectById(id);
        var app = AppConverter.INSTANCE.do2domain(appDO);
        return Optional.ofNullable(app);
    }

    @Override
    public List<App> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var appDOList = appMapper.selectBatchIds(ids);
        return AppConverter.INSTANCE.do2domain(appDOList);
    }

    @Override
    public App getByAppid(String appid) {
        var queryWrapper = Wrappers.lambdaQuery(AppDO.class);
        queryWrapper.eq(AppDO::getAppid, appid);
        var appDO = appMapper.selectOne(queryWrapper);
        return AppConverter.INSTANCE.do2domain(appDO);
    }

    @Override
    public List<App> findByAppids(List<String> appids) {
        if (appids.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(AppDO.class);
        queryWrapper.in(AppDO::getAppid, appids);
        var appDOList = appMapper.selectList(queryWrapper);
        return AppConverter.INSTANCE.do2domain(appDOList);
    }
}
