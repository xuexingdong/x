package cool.xxd.service.pay.application.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import group.hckj.pay.application.converter.MerchantPayChannelConverter;
import group.hckj.pay.application.mapper.MerchantPayChannelMapper;
import group.hckj.pay.application.model.MerchantPayChannelDO;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.repository.MerchantPayChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MerchantPayChannelRepositoryImpl implements MerchantPayChannelRepository {

    private final MerchantPayChannelMapper merchantPayChannelMapper;

    @Override
    public void save(MerchantPayChannel merchantPayChannel) {
        var merchantPayChannelDO = MerchantPayChannelConverter.INSTANCE.domain2do(merchantPayChannel);
        merchantPayChannelMapper.insert(merchantPayChannelDO);
    }

    @Override
    public void deleteById(Long id) {
        var merchantPayChannelDO = new MerchantPayChannelDO();
        merchantPayChannelDO.setId(id);
        merchantPayChannelMapper.deleteById(merchantPayChannelDO);
    }

    @Override
    public void update(MerchantPayChannel merchantPayChannel) {
        var merchantPayChannelDO = MerchantPayChannelConverter.INSTANCE.domain2do(merchantPayChannel);
        merchantPayChannelMapper.updateById(merchantPayChannelDO);
    }

    @Override
    public Optional<MerchantPayChannel> findById(Long id) {
        var merchantPayChannelDO = merchantPayChannelMapper.selectById(id);
        var merchantPayChannel = MerchantPayChannelConverter.INSTANCE.do2domain(merchantPayChannelDO);
        return Optional.ofNullable(merchantPayChannel);
    }

    @Override
    public List<MerchantPayChannel> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var merchantPayChannelDOList = merchantPayChannelMapper.selectBatchIds(ids);
        return MerchantPayChannelConverter.INSTANCE.do2domain(merchantPayChannelDOList);
    }

    @Override
    public List<MerchantPayChannel> findByMchid(String mchid) {
        var queryWrapper = Wrappers.lambdaQuery(MerchantPayChannelDO.class);
        queryWrapper.eq(MerchantPayChannelDO::getMchid, mchid);
        var merchantPayChannelDOList = merchantPayChannelMapper.selectList(queryWrapper);
        return MerchantPayChannelConverter.INSTANCE.do2domain(merchantPayChannelDOList);
    }

    @Override
    public Optional<MerchantPayChannel> findByMchidAndPayChannelCode(String mchid, String payChannelCode) {
        var queryWrapper = Wrappers.lambdaQuery(MerchantPayChannelDO.class);
        queryWrapper.eq(MerchantPayChannelDO::getMchid, mchid);
        queryWrapper.eq(MerchantPayChannelDO::getPayChannelCode, payChannelCode);
        var merchantPayChannelDO = merchantPayChannelMapper.selectOne(queryWrapper);
        var merchantPayChannel = MerchantPayChannelConverter.INSTANCE.do2domain(merchantPayChannelDO);
        return Optional.ofNullable(merchantPayChannel);
    }

    @Override
    public List<MerchantPayChannel> findAll() {
        var queryWrapper = Wrappers.lambdaQuery(MerchantPayChannelDO.class);
        var merchantPayChannelDOList = merchantPayChannelMapper.selectList(queryWrapper);
        return MerchantPayChannelConverter.INSTANCE.do2domain(merchantPayChannelDOList);
    }
}
