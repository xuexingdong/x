package cool.xxd.service.pay.application.service;

import cool.xxd.service.pay.domain.command.AddMerchantPayChannelRouterCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelRouterCommand;

public interface MerchantPayChannelRouterService {

    Long addMerchantPayChannelRouter(AddMerchantPayChannelRouterCommand addMerchantPayChannelRouterCommand);

    void deleteMerchantPayChannelRouter(DeleteMerchantPayChannelRouterCommand deleteMerchantPayChannelRouterCommand);

}
