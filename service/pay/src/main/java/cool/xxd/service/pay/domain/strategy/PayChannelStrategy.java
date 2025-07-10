package cool.xxd.service.pay.domain.strategy;

import cool.xxd.service.pay.domain.aggregate.*;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;

public interface PayChannelStrategy {

    PayResult pay(MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    PayResult query(MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    void close(MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    /**
     * @param merchantPayChannel
     * @param payOrder           原订单
     * @param refundOrder        退款单
     * @return
     */
    RefundResult refund(MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder refundOrder);

    RefundResult queryRefund(MerchantPayChannel merchantPayChannel, RefundOrder refundOrder);

}
