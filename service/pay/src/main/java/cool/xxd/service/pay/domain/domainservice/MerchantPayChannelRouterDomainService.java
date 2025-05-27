package cool.xxd.service.pay.domain.domainservice;

import cool.xxd.service.pay.domain.command.AddMerchantPayChannelRouterCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelRouterCommand;

public interface MerchantPayChannelRouterDomainService {

    Long addMerchantPayChannelRouter(AddMerchantPayChannelRouterCommand addMerchantPayChannelRouterCommand);

    void deleteMerchantPayChannelRouter(DeleteMerchantPayChannelRouterCommand deleteMerchantPayChannelRouterCommand);

}
