package cool.xxd.service.pay.application.service.impl;

import group.hckj.pay.application.service.MerchantPayChannelService;
import cool.xxd.service.pay.domain.command.AddMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.ConfigMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.UpdateMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.domainservice.MerchantPayChannelDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantPayChannelServiceImpl implements MerchantPayChannelService {

    private final MerchantPayChannelDomainService merchantPayChannelDomainService;

    @Override
    public Long addMerchantPayChannel(AddMerchantPayChannelCommand addMerchantPayChannelCommand) {
        return merchantPayChannelDomainService.addMerchantPayChannel(addMerchantPayChannelCommand);
    }

    @Override
    public void deleteMerchantPayChannel(DeleteMerchantPayChannelCommand deleteMerchantPayChannelCommand) {
        merchantPayChannelDomainService.deleteMerchantPayChannel(deleteMerchantPayChannelCommand);
    }

    @Override
    public Long configMerchantPayChannel(ConfigMerchantPayChannelCommand configMerchantPayChannelCommand) {
        return merchantPayChannelDomainService.configMerchantPayChannel(configMerchantPayChannelCommand);
    }

    @Override
    public void updateMerchantPayChannel(UpdateMerchantPayChannelCommand updateMerchantPayChannelCommand) {
        merchantPayChannelDomainService.updateMerchantPayChannel(updateMerchantPayChannelCommand);
    }
}
