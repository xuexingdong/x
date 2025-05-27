package cool.xxd.service.pay.domain.strategy.wechat.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.strategy.wechat.Constants;
import cool.xxd.service.pay.domain.strategy.wechat.api.request.CloseRequest;
import cool.xxd.service.pay.domain.strategy.wechat.api.request.PayRequest;
import cool.xxd.service.pay.domain.strategy.wechat.api.request.RefundRequest;
import cool.xxd.service.pay.domain.strategy.wechat.api.response.*;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.*;

@Component
@RetrofitClient(baseUrl = "${pay.wechat.base-url}")
@Intercept(handler = WechatPayInterceptor.class)
public interface WechatPayApi {
    @POST(Constants.TRANSACTION_PREFIX + "/jsapi")
    JsapiResponse jsapiPay(@Tag App app, @Body PayRequest payRequest);

    @POST(Constants.TRANSACTION_PREFIX + "/app")
    AppResponse appPay(@Tag App app, @Body PayRequest payRequest);

    @POST(Constants.TRANSACTION_PREFIX + "/h5")
    H5Response h5Pay(@Tag App app, @Body PayRequest payRequest);

    @POST(Constants.TRANSACTION_PREFIX + "/native")
    NativeResponse nativePay(@Body PayRequest payRequest);

    @GET(Constants.TRANSACTION_PREFIX + "/id/{transaction_id}")
    PayResponse queryPayResultByTransactionId(@Tag App app, @Path("transaction_id") String transactionId, @Query("sp_mchid") String spMchId, @Query("sub_mchid") String subMchid);

    @GET(Constants.TRANSACTION_PREFIX + "/out-trade-no/{out_trade_no}")
    PayResponse queryPayResultByOutTradeNo(@Tag App app, @Path("out_trade_no") String outTradeNo, @Query("sp_mchid") String spMchId, @Query("sub_mchid") String subMchid);

    @POST(Constants.TRANSACTION_PREFIX + "/out-trade-no/{out_trade_no}/close")
    Call<Void> close(@Tag App app, @Body CloseRequest closeRequest);

    @POST(Constants.REFUND_PREFIX)
    RefundResponse refund(@Tag App app, @Body RefundRequest refundRequest);

    @GET(Constants.REFUND_PREFIX + "/{out_refund_no}")
    RefundResponse queryRefundResult(@Tag App app, @Path("out_refund_no") String outRefundNo, @Query("sub_mchid") String subMchid);

    @GET(Constants.PREFIX + "/bill/tradebill")
    Call<String> getBillDownloadUrl(@Tag App app, @Query("bill_date") String billDate, @Query("bill_type") String billType, @Query("tarType") String tarType);

    @Streaming
    @GET
    Call<ResponseBody> downloadBill(@Tag App app, @Url String downloadUrl);
}
