package cool.xxd.service.pay.application.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.service.pay.application.converter.MerchantPayChannelRouterConverter;
import cool.xxd.service.pay.application.mapper.MerchantPayChannelRouterMapper;
import cool.xxd.service.pay.application.model.MerchantPayChannelRouterDO;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannelRouter;
import cool.xxd.service.pay.domain.repository.MerchantPayChannelRouterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MerchantPayChannelRouterRepositoryImpl implements MerchantPayChannelRouterRepository {

    private final MerchantPayChannelRouterMapper merchantPayChannelRouterMapper;

    @Override
    public void save(MerchantPayChannelRouter merchantPayChannelRouter) {
        var merchantPayChannelRouterDO = MerchantPayChannelRouterConverter.INSTANCE.domain2do(merchantPayChannelRouter);
        merchantPayChannelRouterMapper.insert(merchantPayChannelRouterDO);
    }

    @Override
    public void deleteById(Long id) {
        var merchantPayChannelRouterDO = new MerchantPayChannelRouterDO();
        merchantPayChannelRouterDO.setId(id);
        merchantPayChannelRouterMapper.deleteById(merchantPayChannelRouterDO);
    }

    @Override
    public void update(MerchantPayChannelRouter merchantPayChannelRouter) {
        var merchantPayChannelRouterDO = MerchantPayChannelRouterConverter.INSTANCE.domain2do(merchantPayChannelRouter);
        merchantPayChannelRouterMapper.updateById(merchantPayChannelRouterDO);
    }

    @Override
    public Optional<MerchantPayChannelRouter> findById(Long id) {
        var merchantPayChannelRouterDO = merchantPayChannelRouterMapper.selectById(id);
        var merchantPayChannelRouter = MerchantPayChannelRouterConverter.INSTANCE.do2domain(merchantPayChannelRouterDO);
        return Optional.ofNullable(merchantPayChannelRouter);
    }

    @Override
    public List<MerchantPayChannelRouter> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var merchantPayChannelRouterDOList = merchantPayChannelRouterMapper.selectByIds(ids);
        return MerchantPayChannelRouterConverter.INSTANCE.do2domain(merchantPayChannelRouterDOList);
    }

    @Override
    public Optional<MerchantPayChannelRouter> findByMchidAndPayTypeCode(String mchid, String payTypeCode) {
        var queryWrapper = Wrappers.lambdaQuery(MerchantPayChannelRouterDO.class);
        queryWrapper.eq(MerchantPayChannelRouterDO::getMchid, mchid);
        queryWrapper.eq(MerchantPayChannelRouterDO::getPayTypeCode, payTypeCode);
        var merchantPayChannelRouterDO = merchantPayChannelRouterMapper.selectOne(queryWrapper);
        var merchantPayChannelRouter = MerchantPayChannelRouterConverter.INSTANCE.do2domain(merchantPayChannelRouterDO);
        return Optional.ofNullable(merchantPayChannelRouter);
    }

    @Override
    public List<MerchantPayChannelRouter> findByMchid(String mchid) {
        var queryWrapper = Wrappers.lambdaQuery(MerchantPayChannelRouterDO.class);
        queryWrapper.eq(MerchantPayChannelRouterDO::getMchid, mchid);
        var merchantPayChannelRouterDOList = merchantPayChannelRouterMapper.selectList(queryWrapper);
        return MerchantPayChannelRouterConverter.INSTANCE.do2domain(merchantPayChannelRouterDOList);
    }
}
