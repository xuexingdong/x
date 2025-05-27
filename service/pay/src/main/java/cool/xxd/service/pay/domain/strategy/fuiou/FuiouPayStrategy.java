package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.valueobject.PayResult;

public interface FuiouPayStrategy {
    PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder);
}
