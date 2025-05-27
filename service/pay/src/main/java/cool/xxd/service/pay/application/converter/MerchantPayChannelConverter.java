package cool.xxd.service.pay.application.converter;

import group.hckj.pay.application.model.MerchantPayChannelDO;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MerchantPayChannelConverter {
    MerchantPayChannelConverter INSTANCE = Mappers.getMapper(MerchantPayChannelConverter.class);

    MerchantPayChannel do2domain(MerchantPayChannelDO merchantPayChannelDO);

    List<MerchantPayChannel> do2domain(List<MerchantPayChannelDO> merchantPayChannelDOList);

    MerchantPayChannelDO domain2do(MerchantPayChannel merchantPayChannel);

    List<MerchantPayChannelDO> domain2do(List<MerchantPayChannel> merchantPayChannels);
}
