package cool.xxd.service.pay.domain.strategy.cash;

import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategy;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Component(PAY_CHANNEL_STRATEGY_PREFIX + "CASH")
@RequiredArgsConstructor
public class CashPayChannelStrategy implements PayChannelStrategy {

    @Override
    public PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        return buildPayResult();
    }

    @Override
    public PayResult query(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        return buildPayResult();
    }

    @Override
    public void close(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
    }

    @Override
    public RefundResult refund(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder refundOrder) {
        return buildRefundResult();
    }

    @Override
    public RefundResult queryRefund(App app, MerchantPayChannel merchantPayChannel, RefundOrder refundOrder) {
        return buildRefundResult();
    }

    private PayResult buildPayResult() {
        var payResult = new PayResult();
        payResult.setPayStatus(PayStatusEnum.PAID);
        payResult.setPayTime(LocalDateTime.now());
        return payResult;
    }

    private RefundResult buildRefundResult() {
        var refundResult = new RefundResult();
        refundResult.setRefundStatus(RefundStatusEnum.SUCCESS);
        refundResult.setRefundTime(LocalDateTime.now());
        return refundResult;
    }
}
