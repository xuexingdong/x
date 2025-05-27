package cool.xxd.service.pay.domain.domainservice.impl;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.infra.lock.LockTemplate;
import cool.xxd.service.pay.domain.command.AddMerchantPayChannelRouterCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelRouterCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.domainservice.MerchantPayChannelRouterDomainService;
import cool.xxd.service.pay.domain.factory.MerchantPayChannelRouterFactory;
import cool.xxd.service.pay.domain.repository.MerchantPayChannelRouterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantPayChannelRouterDomainServiceImpl implements MerchantPayChannelRouterDomainService {

    private final MerchantPayChannelRouterFactory merchantPayChannelRouterFactory;
    private final MerchantPayChannelRouterRepository merchantPayChannelRouterRepository;
    private final LockTemplate lockTemplate;

    @Override
    public Long addMerchantPayChannelRouter(AddMerchantPayChannelRouterCommand addMerchantPayChannelRouterCommand) {
        var lockKey = String.format(CacheKeys.MERCHANT_ADD_MERCHANT_PAY_CHANNEL_ROUTER, addMerchantPayChannelRouterCommand.getMchid());
        return lockTemplate.execute(lockKey, () -> {
            merchantPayChannelRouterRepository.findByMchidAndPayTypeCode(addMerchantPayChannelRouterCommand.getMchid(), addMerchantPayChannelRouterCommand.getPayTypeCode())
                    .ifPresent(merchantPayChannel -> {
                        throw new BusinessException("商户支付渠道路由已存在");
                    });
            var merchantPayChannelRouter = merchantPayChannelRouterFactory.create(addMerchantPayChannelRouterCommand.getMchid(), addMerchantPayChannelRouterCommand.getPayTypeCode(), addMerchantPayChannelRouterCommand.getPayChannelCode());
            merchantPayChannelRouterRepository.save(merchantPayChannelRouter);
            return merchantPayChannelRouter.getId();
        });
    }

    @Override
    public void deleteMerchantPayChannelRouter(DeleteMerchantPayChannelRouterCommand deleteMerchantPayChannelRouterCommand) {
        merchantPayChannelRouterRepository.findByMchidAndPayTypeCode(deleteMerchantPayChannelRouterCommand.getMchid(), deleteMerchantPayChannelRouterCommand.getPayTypeCode())
                .ifPresent(merchantPayChannelRouter -> {
                    merchantPayChannelRouterRepository.deleteById(merchantPayChannelRouter.getId());
                });
    }
}
