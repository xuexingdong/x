package cool.xxd.service.pay.domain.strategy.fuiou;

import com.alibaba.fastjson2.JSON;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.strategy.fuiou.api.FuiouPayApi;
import cool.xxd.service.pay.domain.strategy.fuiou.utils.FuiouUtils;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.ui.config.FuiouPayConfig;
import org.springframework.stereotype.Component;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Component(PAY_CHANNEL_STRATEGY_PREFIX + "FUIOU_PASSIVE_SCAN")
public class FuiouMicropayRequestStrategy extends AbstractFuiouPayTemplate<MicropayRequest, MicropayResponse> {

    public FuiouMicropayRequestStrategy(OrderLogDomainService orderLogDomainService, FuiouPayApi fuiouPayApi, FuiouPayConfig fuiouPayConfig) {
        super(orderLogDomainService, fuiouPayApi, fuiouPayConfig);
    }

    @Override
    protected MicropayRequest buildRequest(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var payRequest = new MicropayRequest(fuiouPayChannelConfig.getIns_cd(), fuiouPayChannelConfig);
        PayRequestBuilder.setCommonFields(payRequest, payOrder);
        payRequest.setOrderType(FuiouUtils.getOrderType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        payRequest.setAuthCode(payOrder.getAuthCode());
        payRequest.setScene("1");
        return payRequest;
    }

    @Override
    protected MicropayResponse executeRequest(App app, MicropayRequest request) {
        return fuiouPayApi.micropay(app, request);
    }

    @Override
    PayResult parseResponse(PayOrder payOrder, MicropayResponse response) {
        var payResult = new PayResult();
        if (response.getResultCode().equals("000000")) {
            payResult.setPayStatus(PayStatusEnum.PAYING);
        } else {
            payResult.setPayStatus(PayStatusEnum.FAILED);
        }
        payResult.setThirdTradeNo(response.getReservedFyOrderNo());
        return payResult;
    }
}
