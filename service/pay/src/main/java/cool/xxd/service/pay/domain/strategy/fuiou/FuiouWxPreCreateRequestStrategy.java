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

import java.util.HashMap;
import java.util.Map;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Component(PAY_CHANNEL_STRATEGY_PREFIX + "FUIOU_JSAPI")
public class FuiouWxPreCreateRequestStrategy extends AbstractFuiouPayTemplate<WxPreCreateRequest, WxPreCreateResponse> {

    public FuiouWxPreCreateRequestStrategy(OrderLogDomainService orderLogDomainService, FuiouPayApi fuiouPayApi, FuiouPayConfig fuiouPayConfig) {
        super(orderLogDomainService, fuiouPayApi, fuiouPayConfig);
    }

    @Override
    protected WxPreCreateRequest buildRequest(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var appConfig = app.getConfig();
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var payRequest = new WxPreCreateRequest(appConfig.get("ins_cd"), fuiouPayChannelConfig);
        PayRequestBuilder.setCommonFields(payRequest, payOrder);
        payRequest.setNotifyUrl(appConfig.get("notify_url"));
        payRequest.setTradeType(FuiouUtils.getTradeType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        payRequest.setSubOpenid(payOrder.getSubOpenid());
        payRequest.setSubAppid(payOrder.getSubAppid());
        return payRequest;
    }

    @Override
    protected WxPreCreateResponse executeRequest(App app, WxPreCreateRequest request) {
        return fuiouPayApi.wxPreCreate(app, request);
    }

    @Override
    PayResult parseResponse(PayOrder payOrder, WxPreCreateResponse response) {
        var payResult = new PayResult();
        if (response.getResultCode().equals("000000")) {
            payResult.setPayStatus(PayStatusEnum.PAYING);
        } else {
            payResult.setPayStatus(PayStatusEnum.FAILED);
        }
        payResult.setThirdTradeNo(response.getReservedFyOrderNo());
        if (response.getResultCode().equals("000000")) {
            var clientPayInvokeParams = getClientPayInvokeParams(response);
            payResult.setClientPayInvokeParams(JSON.toJSONString(clientPayInvokeParams));
        }
        return payResult;
    }

    private static Map<String, String> getClientPayInvokeParams(WxPreCreateResponse response) {
        var clientPayInvokeParams = new HashMap<String, String>();
        clientPayInvokeParams.put("appId", response.getSdkAppid());
        clientPayInvokeParams.put("timeStamp", response.getSdkTimestamp());
        clientPayInvokeParams.put("nonceStr", response.getSdkNoncestr());
        clientPayInvokeParams.put("package", response.getSdkPackage());
        clientPayInvokeParams.put("signType", response.getSdkSigntype());
        clientPayInvokeParams.put("paySign", response.getSdkPaysign());
        clientPayInvokeParams.put("partnerid", response.getSdkPartnerid());
        clientPayInvokeParams.put("trade_no", response.getReservedTransactionId());
        return clientPayInvokeParams;
    }
}
