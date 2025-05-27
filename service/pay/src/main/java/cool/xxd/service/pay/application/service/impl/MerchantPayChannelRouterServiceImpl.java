package cool.xxd.service.pay.application.service.impl;

import group.hckj.pay.application.service.MerchantPayChannelRouterService;
import cool.xxd.service.pay.domain.command.AddMerchantPayChannelRouterCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelRouterCommand;
import cool.xxd.service.pay.domain.domainservice.MerchantPayChannelRouterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantPayChannelRouterServiceImpl implements MerchantPayChannelRouterService {

    private final MerchantPayChannelRouterDomainService merchantPayChannelRouterDomainService;

    @Override
    public Long addMerchantPayChannelRouter(AddMerchantPayChannelRouterCommand addMerchantPayChannelRouterCommand) {
        return merchantPayChannelRouterDomainService.addMerchantPayChannelRouter(addMerchantPayChannelRouterCommand);
    }

    @Override
    public void deleteMerchantPayChannelRouter(DeleteMerchantPayChannelRouterCommand deleteMerchantPayChannelRouterCommand) {
        merchantPayChannelRouterDomainService.deleteMerchantPayChannelRouter(deleteMerchantPayChannelRouterCommand);
    }
}
