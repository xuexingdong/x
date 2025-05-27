package cool.xxd.service.pay.domain.domainservice.impl;

import com.alibaba.fastjson2.JSON;
import cool.xxd.infra.enums.CommonEnum;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.infra.lock.LockTemplate;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.command.AddMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.ConfigMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.UpdateMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.domainservice.MerchantPayChannelDomainService;
import cool.xxd.service.pay.domain.enums.PayChannelEnum;
import cool.xxd.service.pay.domain.factory.MerchantPayChannelFactory;
import cool.xxd.service.pay.domain.repository.MerchantPayChannelRepository;
import cool.xxd.service.pay.domain.strategy.alipay.AlipayChannelConfig;
import cool.xxd.service.pay.domain.strategy.fuiou.FuiouPayChannelConfig;
import cool.xxd.service.pay.domain.strategy.jialian.JialianPayChannelConfig;
import cool.xxd.service.pay.domain.strategy.wechat.WechatPayChannelConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MerchantPayChannelDomainServiceImpl implements MerchantPayChannelDomainService {

    private final MerchantPayChannelFactory merchantPayChannelFactory;
    private final MerchantPayChannelRepository merchantPayChannelRepository;
    private final LockTemplate lockTemplate;

    @Override
    public Long addMerchantPayChannel(AddMerchantPayChannelCommand addMerchantPayChannelCommand) {
        validateConfig(addMerchantPayChannelCommand.getPayChannelCode(), addMerchantPayChannelCommand.getConfig());
        var lockKey = String.format(CacheKeys.MERCHANT_ADD_MERCHANT_PAY_CHANNEL, addMerchantPayChannelCommand.getMchid());
        return lockTemplate.execute(lockKey, () -> {
            merchantPayChannelRepository.findByMchidAndPayChannelCode(addMerchantPayChannelCommand.getMchid(), addMerchantPayChannelCommand.getPayChannelCode())
                    .ifPresent(merchantPayChannel -> {
                        throw new BusinessException("商户支付渠道已存在");
                    });
            var merchantPayChannel = merchantPayChannelFactory.create(addMerchantPayChannelCommand);
            merchantPayChannelRepository.save(merchantPayChannel);
            return merchantPayChannel.getId();
        });
    }

    @Override
    public void deleteMerchantPayChannel(DeleteMerchantPayChannelCommand deleteMerchantPayChannelCommand) {
        merchantPayChannelRepository.findByMchidAndPayChannelCode(deleteMerchantPayChannelCommand.getMchid(), deleteMerchantPayChannelCommand.getPayChannelCode())
                .ifPresent(merchantPayChannel -> {
                    merchantPayChannelRepository.deleteById(merchantPayChannel.getId());
                });
    }

    @Transactional
    @Override
    public Long configMerchantPayChannel(ConfigMerchantPayChannelCommand configMerchantPayChannelCommand) {
        validateConfig(configMerchantPayChannelCommand.getPayChannelCode(), configMerchantPayChannelCommand.getConfig());
        var payChannels = merchantPayChannelRepository.findByMchid(configMerchantPayChannelCommand.getMchid());
        if (payChannels.size() > 2) {
            throw new BusinessException("存在多种支付渠道，请检查配置");
        }
        MerchantPayChannel merchantPayChannel;
        if (payChannels.isEmpty()) {
            merchantPayChannel = merchantPayChannelFactory.create(configMerchantPayChannelCommand);
            merchantPayChannelRepository.save(merchantPayChannel);
        } else {
            merchantPayChannel = payChannels.getFirst();
            merchantPayChannel.update(configMerchantPayChannelCommand.getPayChannelCode(), configMerchantPayChannelCommand.getConfig());
            merchantPayChannelRepository.update(merchantPayChannel);
        }
        return merchantPayChannel.getId();
    }

    @Override
    public void updateMerchantPayChannel(UpdateMerchantPayChannelCommand updateMerchantPayChannelCommand) {
        validateConfig(updateMerchantPayChannelCommand.getPayChannelCode(), updateMerchantPayChannelCommand.getConfig());
        var merchantPayChannel = merchantPayChannelRepository.findByMchidAndPayChannelCode(updateMerchantPayChannelCommand.getMchid(), updateMerchantPayChannelCommand.getPayChannelCode())
                .orElseThrow(() -> new BusinessException("商户支付渠道不存在"));
        merchantPayChannel.update(updateMerchantPayChannelCommand.getConfig());
        merchantPayChannelRepository.update(merchantPayChannel);
    }

    private void validateConfig(String payChannelCode, String config) {
        var payChannelEnum = CommonEnum.of(PayChannelEnum.class, payChannelCode);
        switch (payChannelEnum) {
            case FUIOU -> {
                var fuiouPayChannelConfig = JSON.parseObject(config, FuiouPayChannelConfig.class);
                if (fuiouPayChannelConfig.getMchnt_cd().isBlank()) {
                    throw new BusinessException("mchnt_cd不能为空");
                }
                if (fuiouPayChannelConfig.getTerm_id().isBlank()) {
                    throw new BusinessException("term_id不能为空");
                }
            }
            case JIALIAN -> {
                var jialianPayChannelConfig = JSON.parseObject(config, JialianPayChannelConfig.class);
                if (jialianPayChannelConfig.getCode().isBlank()) {
                    throw new BusinessException("code不能为空");
                }
                if (jialianPayChannelConfig.getSettlementBodyId().isBlank()) {
                    throw new BusinessException("settlementBodyId不能为空");
                }
            }
            case WECHAT -> {
                var wechatPayChannelConfig = JSON.parseObject(config, WechatPayChannelConfig.class);
                if (StringUtils.isBlank(wechatPayChannelConfig.getSub_mchid())) {
                    throw new BusinessException("sub_mchid不能为空");
                }
            }
            case ALIPAY -> {
                var alipayChannelConfig = JSON.parseObject(config, AlipayChannelConfig.class);
                if (StringUtils.isBlank(alipayChannelConfig.getApp_auth_token())) {
                    throw new BusinessException("app_auth_token不能为空");
                }
            }
        }
    }
}

