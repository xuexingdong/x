package cool.xxd.service.pay.domain.strategy.jialian.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import cool.xxd.service.pay.domain.strategy.jialian.*;
import org.springframework.stereotype.Component;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Tag;

/**
 * 嘉联支付API拦截器，主要解包返回值用
 * 文档地址 <a href="https://mtcbeijing.feishu.cn/wiki/TJ6wwR3zqiF8HVknAY6c7lbJnSb">支付</a>
 */
@Component
@RetrofitClient(baseUrl = "${pay.jialian.base-url}")
@Intercept(handler = JialianPayInterceptor.class, exclude = "auth/token")
public interface JialianPayApi {

    // 发起支付
    @POST("pay/api/open/trade/createTrade")
    TradeCreateResult createTrade(@Tag JialianPayChannelConfig jialianPayChannelConfig, @Body CreateTradeRequest createTradeRequest);

    // 发起退款
    @POST("pay/api/open/trade/createRefund")
    RefundCreateResult createRefund(@Tag JialianPayChannelConfig jialianPayChannelConfig, @Body CreateRefundRequest createRefundRequest);

    // 发起关单
    @POST("pay/api/open/trade/closeTrade")
    Boolean closeTrade(@Tag JialianPayChannelConfig jialianPayChannelConfig, @Body CloseTradeRequest closeTradeRequest);

    // 支付结果查询
    @POST("pay/api/open/trade/queryTrade")
    TradeInfoDTO queryTrade(@Tag JialianPayChannelConfig jialianPayChannelConfig, @Body QueryTradeRequest queryTradeRequest);

    // 退款结果查询
    @POST("pay/api/open/trade/queryRefund")
    RefundQueryResult queryRefund(@Tag JialianPayChannelConfig jialianPayChannelConfig, @Body QueryRefundRequest queryRefundRequest);

    @POST("ps/api/open/custom/accept/balance/split/v2")
    RefundQueryResult balanceSplit(@Tag JialianPayChannelConfig jialianPayChannelConfig, @Body QueryRefundRequest queryRefundRequest);
}
