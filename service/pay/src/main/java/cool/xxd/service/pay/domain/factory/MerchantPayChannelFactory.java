package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.command.AddMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.ConfigMerchantPayChannelCommand;

public interface MerchantPayChannelFactory {
    MerchantPayChannel create(AddMerchantPayChannelCommand addMerchantPayChannelCommand);

    MerchantPayChannel create(ConfigMerchantPayChannelCommand configMerchantPayChannelCommand);
}
