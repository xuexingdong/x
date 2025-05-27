package cool.xxd.service.pay.domain.strategy.fuiou.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.strategy.fuiou.*;
import org.springframework.stereotype.Component;
import retrofit2.converter.jaxb3.JaxbConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Tag;

/**
 * 文档地址 <a href="https://fundwx.fuiou.com/doc/#/scanpay/api">富友支付</a>
 */
@Component
@RetrofitClient(baseUrl = "${pay.fuiou.base-url}", converterFactories = {JaxbConverterFactory.class})
@Intercept(handler = FuiouSignInterceptor.class)
public interface FuiouPayApi {
    @POST("micropay")
    MicropayResponse micropay(@Tag App app, @Body MicropayRequest microPayRequest);

    @POST("preCreate")
    PreCreateResponse preCreate(@Tag App app, @Body PreCreateRequest preCreateRequest);

    @POST("wxPreCreate")
    WxPreCreateResponse wxPreCreate(@Tag App app, @Body WxPreCreateRequest wxPreCreateRequest);

    @POST("commonQuery")
    CommonQueryResponse commonQuery(@Tag App app, @Body CommonQueryRequest commonQueryRequest);

    @POST("commonRefund")
    CommonRefundResponse commonRefund(@Tag App app, @Body CommonRefundRequest commonRefundRequest);

    @POST("refundQuery")
    RefundQueryResponse refundQuery(@Tag App app, @Body RefundQueryRequest refundQueryRequest);

    @POST("cancelorder")
    CancelOrderResponse cancelorder(@Tag App app, @Body CancelOrderRequest refundRequest);

    @POST("closeorder")
    CloseOrderResponse closeorder(@Tag App app, @Body CloseOrderRequest refundRequest);

}
