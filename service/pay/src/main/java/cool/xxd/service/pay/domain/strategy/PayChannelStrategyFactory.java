package cool.xxd.service.pay.domain.strategy;

import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.enums.PayChannelEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.exceptions.PayException;
import cool.xxd.service.pay.domain.repository.MerchantPayChannelRepository;
import cool.xxd.service.pay.domain.repository.MerchantPayChannelRouterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Component
@RequiredArgsConstructor
public class PayChannelStrategyFactory {
    private final Map<String, PayChannelStrategy> payChannelStrategyMap;
    private final MerchantPayChannelRepository merchantPayChannelRepository;
    private final MerchantPayChannelRouterRepository merchantPayChannelRouterRepository;

    public PayChannelStrategy getStrategy(String payChannelCode) {
        var payChannelStrategy = payChannelStrategyMap.get(PAY_CHANNEL_STRATEGY_PREFIX + payChannelCode);
        if (payChannelStrategy == null) {
            throw new PayException("支付/退款策略为空，支付/退款失败");
        }
        return payChannelStrategy;
    }

    /**
     * TODO 支付路由，后期完善，目前系统限定一个商户只能使用一个支付渠道
     *
     * @param mchid
     * @param payTypeCode
     * @return
     */
    public MerchantPayChannel routePayChannel(String mchid, String payTypeCode) {
        if (PayTypeEnum.CASH.getCode().equals(payTypeCode)) {
            var merchantPayChannel = new MerchantPayChannel();
            merchantPayChannel.setMchid(mchid);
            merchantPayChannel.setPayChannelCode(PayChannelEnum.CASH.getCode());
            return merchantPayChannel;
        }
        var merchantPayChannelRouter = merchantPayChannelRouterRepository.findByMchidAndPayTypeCode(mchid, payTypeCode)
                .orElseThrow(() -> new PayException("商户无可用支付渠道"));
        return merchantPayChannelRepository.findByMchidAndPayChannelCode(merchantPayChannelRouter.getMchid(), merchantPayChannelRouter.getPayChannelCode())
                .orElseThrow(() -> new PayException("商户支付渠道不存在"));
    }
}
