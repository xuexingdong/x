package cool.xxd.service.pay.application.service;

import cool.xxd.service.pay.domain.command.AddMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.ConfigMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.DeleteMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.UpdateMerchantPayChannelCommand;

public interface MerchantPayChannelService {

    Long addMerchantPayChannel(AddMerchantPayChannelCommand addMerchantPayChannelCommand);

    void deleteMerchantPayChannel(DeleteMerchantPayChannelCommand deleteMerchantPayChannelCommand);

    Long configMerchantPayChannel(ConfigMerchantPayChannelCommand configMerchantPayChannelCommand);

    void updateMerchantPayChannel(UpdateMerchantPayChannelCommand updateMerchantPayChannelCommand);

}
