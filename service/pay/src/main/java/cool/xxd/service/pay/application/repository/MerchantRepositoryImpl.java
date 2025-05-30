package cool.xxd.service.pay.application.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.service.pay.application.converter.MerchantConverter;
import cool.xxd.service.pay.application.mapper.MerchantMapper;
import cool.xxd.service.pay.application.model.MerchantDO;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MerchantRepositoryImpl implements MerchantRepository {

    private final MerchantMapper merchantMapper;

    @Override
    public void save(Merchant merchant) {
        var merchantDO = MerchantConverter.INSTANCE.domain2do(merchant);
        merchantMapper.insert(merchantDO);
    }

    @Override
    public void deleteById(Long id) {
        var merchantDO = new MerchantDO();
        merchantDO.setId(id);
        merchantMapper.deleteById(merchantDO);
    }

    @Override
    public void update(Merchant merchant) {
        var merchantDO = MerchantConverter.INSTANCE.domain2do(merchant);
        merchantMapper.updateById(merchantDO);
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        var merchantDO = merchantMapper.selectById(id);
        var merchant = MerchantConverter.INSTANCE.do2domain(merchantDO);
        return Optional.ofNullable(merchant);
    }

    @Override
    public List<Merchant> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var merchantDOList = merchantMapper.selectBatchIds(ids);
        return MerchantConverter.INSTANCE.do2domain(merchantDOList);
    }

    @Override
    public Optional<Merchant> findByAppidAndMchid(String appid, String mchid) {
        var queryWrapper = Wrappers.lambdaQuery(MerchantDO.class);
        queryWrapper.eq(MerchantDO::getAppid, appid);
        queryWrapper.eq(MerchantDO::getMchid, mchid);
        var merchantDO = merchantMapper.selectOne(queryWrapper);
        var merchant = MerchantConverter.INSTANCE.do2domain(merchantDO);
        return Optional.ofNullable(merchant);
    }

    @Override
    public Optional<Merchant> findByAppIdAndOutMchid(String appid, String outMchid) {
        var queryWrapper = Wrappers.lambdaQuery(MerchantDO.class);
        queryWrapper.eq(MerchantDO::getAppid, appid);
        queryWrapper.eq(MerchantDO::getOutMchid, outMchid);
        var merchantDO = merchantMapper.selectOne(queryWrapper);
        var merchant = MerchantConverter.INSTANCE.do2domain(merchantDO);
        return Optional.ofNullable(merchant);
    }

    @Override
    public List<Merchant> findByMchids(List<String> mchids) {
        if (mchids.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(MerchantDO.class);
        queryWrapper.in(MerchantDO::getMchid, mchids);
        var merchantDOList = merchantMapper.selectList(queryWrapper);
        return MerchantConverter.INSTANCE.do2domain(merchantDOList);
    }
}
