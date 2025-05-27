package cool.xxd.service.pay.domain.strategy.alipay.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import cool.xxd.service.pay.domain.strategy.alipay.AlipayChannelConfig;
import cool.xxd.service.pay.domain.strategy.alipay.Constants;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.request.*;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.response.*;
import org.springframework.stereotype.Component;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Tag;

@Component
@RetrofitClient(baseUrl = "${pay.alipay.base-url}")
@Intercept(handler = AlipayInterceptor.class)
public interface AlipayApi {
    @POST(Constants.TRADE_PREFIX + "/create")
    AlipayCreateResponse create(@Tag AlipayChannelConfig alipayChannelConfig, @Body AlipayCreateRequest alipayCreateRequest);

    @POST(Constants.TRADE_PREFIX + "/pay")
    AlipayPayResponse pay(@Tag AlipayChannelConfig alipayChannelConfig, @Body AlipayPayRequest alipayPayRequest);

    @POST(Constants.TRADE_PREFIX + "/query")
    AlipayQueryResponse query(@Tag AlipayChannelConfig alipayChannelConfig, @Body AlipayQueryRequest alipayQueryRequest);

    @POST(Constants.TRADE_PREFIX + "/close")
    AlipayCloseResponse close(@Tag AlipayChannelConfig alipayChannelConfig, @Body AlipayCloseRequest alipayCloseRequest);

    @POST(Constants.TRADE_PREFIX + "/refund")
    AlipayRefundResponse refund(@Tag AlipayChannelConfig alipayChannelConfig, @Body AlipayRefundRequest request);

    @POST(Constants.TRADE_PREFIX + "/fastpay/refund/query")
    AlipayRefundQueryResponse queryRefund(@Tag AlipayChannelConfig alipayChannelConfig, @Body AlipayRefundQueryRequest alipayRefundQueryRequest);

}
