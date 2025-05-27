package cool.xxd.service.pay.domain.strategy;

import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;

public interface PayChannelStrategy {

    PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    PayResult query(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    void close(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    /**
     * @param app
     * @param merchantPayChannel
     * @param payOrder           原订单
     * @param refundOrder        退款单
     * @return
     */
    RefundResult refund(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder refundOrder);

    RefundResult queryRefund(App app, MerchantPayChannel merchantPayChannel, RefundOrder refundOrder);

}
