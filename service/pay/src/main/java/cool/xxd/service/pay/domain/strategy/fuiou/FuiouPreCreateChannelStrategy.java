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

@Component(PAY_CHANNEL_STRATEGY_PREFIX + "FUIOU_SCAN")
public class FuiouPreCreateChannelStrategy extends AbstractFuiouPayTemplate<PreCreateRequest, PreCreateResponse> {

    public FuiouPreCreateChannelStrategy(OrderLogDomainService orderLogDomainService, FuiouPayApi fuiouPayApi, FuiouPayConfig fuiouPayConfig) {
        super(orderLogDomainService, fuiouPayApi, fuiouPayConfig);
    }

    @Override
    PreCreateRequest buildRequest(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var appConfig = app.getConfig();
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var payRequest = new PreCreateRequest(appConfig.get("ins_cd"), fuiouPayChannelConfig);
        PayRequestBuilder.setCommonFields(payRequest, payOrder);
        payRequest.setOrderType(FuiouUtils.getOrderType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        payRequest.setNotifyUrl(appConfig.get("notify_url"));
        return payRequest;
    }

    @Override
    PreCreateResponse executeRequest(App app, PreCreateRequest request) {
        return fuiouPayApi.preCreate(app, request);
    }

    @Override
    PayResult parseResponse(PayOrder payOrder, PreCreateResponse response) {
        var payResult = new PayResult();
        if (response.getResultCode().equals("000000")) {
            payResult.setPayStatus(PayStatusEnum.PAYING);
        } else {
            payResult.setPayStatus(PayStatusEnum.FAILED);
        }
        payResult.setQrCode(response.getQrCode());
        payResult.setThirdTradeNo(response.getReservedFyOrderNo());
        return payResult;
    }
}
