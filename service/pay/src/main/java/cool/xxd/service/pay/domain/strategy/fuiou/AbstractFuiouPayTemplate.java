package cool.xxd.service.pay.domain.strategy.fuiou;

import com.alibaba.fastjson2.JSON;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.strategy.fuiou.api.FuiouPayApi;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.ui.config.FuiouPayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AbstractFuiouPayTemplate<Req extends BasePayRequest, Resp extends BasePayResponse> implements FuiouPayStrategy {

    protected final OrderLogDomainService orderLogDomainService;

    protected final FuiouPayApi fuiouPayApi;

    protected final FuiouPayConfig fuiouPayConfig;

    abstract Req buildRequest(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder);

    abstract Resp executeRequest(App app, Req request);

    abstract PayResult parseResponse(PayOrder payOrder, Resp response);

    @Override
    public PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        Req request = buildRequest(app, merchantPayChannel, payOrder);
        var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(request));
        Resp response = executeRequest(app, request);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(response));
        return parseResponse(payOrder, response);
    }
}
