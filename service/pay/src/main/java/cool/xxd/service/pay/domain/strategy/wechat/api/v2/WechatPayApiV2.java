package cool.xxd.service.pay.domain.strategy.wechat.api.v2;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import cool.xxd.service.pay.domain.strategy.wechat.Constants;
import org.springframework.stereotype.Component;
import retrofit2.converter.jaxb3.JaxbConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Component
@RetrofitClient(baseUrl = "${pay.wechat.base-url}", converterFactories = {JaxbConverterFactory.class})
public interface WechatPayApiV2 {
    @POST(Constants.V2_PREFIX + "/micropay")
    MicroPayResponseV2 microPay(@Body PayRequestV2 payRequestV2);
}
