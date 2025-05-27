package cool.xxd.service.pay.application.converter;

import group.hckj.pay.application.model.MerchantPayChannelRouterDO;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannelRouter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MerchantPayChannelRouterConverter {
    MerchantPayChannelRouterConverter INSTANCE = Mappers.getMapper(MerchantPayChannelRouterConverter.class);

    MerchantPayChannelRouter do2domain(MerchantPayChannelRouterDO merchantPayChannelRouterDO);

    List<MerchantPayChannelRouter> do2domain(List<MerchantPayChannelRouterDO> merchantPayChannelRouterDOList);

    MerchantPayChannelRouterDO domain2do(MerchantPayChannelRouter merchantPayChannelRouter);

    List<MerchantPayChannelRouterDO> domain2do(List<MerchantPayChannelRouter> merchantPayChannelRouters);
}
