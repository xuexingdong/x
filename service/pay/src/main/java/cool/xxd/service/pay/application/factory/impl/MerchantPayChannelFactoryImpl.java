package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.service.pay.application.model.MerchantPayChannelDO;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.command.AddMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.command.ConfigMerchantPayChannelCommand;
import cool.xxd.service.pay.domain.factory.MerchantPayChannelFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantPayChannelFactoryImpl implements MerchantPayChannelFactory {
    private final IdGenerator idGenerator;

    @Override
    public MerchantPayChannel create(AddMerchantPayChannelCommand addMerchantPayChannelCommand) {
        var merchantPayChannel = new MerchantPayChannel();
        merchantPayChannel.setId(idGenerator.nextId(MerchantPayChannelDO.class));
        merchantPayChannel.setMchid(addMerchantPayChannelCommand.getMchid());
        merchantPayChannel.setPayChannelCode(addMerchantPayChannelCommand.getPayChannelCode());
        merchantPayChannel.setConfig(addMerchantPayChannelCommand.getConfig());
        return merchantPayChannel;
    }

    @Override
    public MerchantPayChannel create(ConfigMerchantPayChannelCommand configMerchantPayChannelCommand) {
        var merchantPayChannel = new MerchantPayChannel();
        merchantPayChannel.setId(idGenerator.nextId(MerchantPayChannelDO.class));
        merchantPayChannel.setMchid(configMerchantPayChannelCommand.getMchid());
        merchantPayChannel.setPayChannelCode(configMerchantPayChannelCommand.getPayChannelCode());
        merchantPayChannel.setConfig(configMerchantPayChannelCommand.getConfig());
        return merchantPayChannel;
    }
}
