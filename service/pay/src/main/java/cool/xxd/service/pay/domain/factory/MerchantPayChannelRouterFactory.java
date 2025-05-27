package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.MerchantPayChannelRouter;

public interface MerchantPayChannelRouterFactory {
    MerchantPayChannelRouter create(String mchid, String payTypeCode, String payChannelCode);
}
