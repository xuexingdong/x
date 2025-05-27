package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.idgen.IdGenerator;
import group.hckj.pay.application.model.MerchantPayChannelRouterDO;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannelRouter;
import cool.xxd.service.pay.domain.factory.MerchantPayChannelRouterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantPayChannelRouterFactoryImpl implements MerchantPayChannelRouterFactory {
    private final IdGenerator idGenerator;

    @Override
    public MerchantPayChannelRouter create(String mchid, String payTypeCode, String payChannelCode) {
        var id = idGenerator.nextId(MerchantPayChannelRouterDO.class);
        var merchantPayChannelRouter = new MerchantPayChannelRouter();
        merchantPayChannelRouter.setId(id);
        merchantPayChannelRouter.setMchid(mchid);
        merchantPayChannelRouter.setPayTypeCode(payTypeCode);
        merchantPayChannelRouter.setPayChannelCode(payChannelCode);
        return merchantPayChannelRouter;
    }
}
