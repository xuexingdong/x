package cool.xxd.service.pay.domain.strategy.fuiou.api;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.strategy.fuiou.utils.FuiouUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.converter.jaxb3.JaxbConverterFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;


@Slf4j
@Component
public class FuiouSignInterceptor extends BasePathMatchInterceptor {

    @Bean
    public JaxbConverterFactory jaxbConverterFactory() {
        return JaxbConverterFactory.create();
    }

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        var request = chain.request();
        var requestBody = request.body();
        if (requestBody == null) {
            return chain.proceed(request);
        }
        var app = request.tag(App.class);
        if (app == null) {
            return chain.proceed(request);
        }
        var buffer = new Buffer();
        requestBody.writeTo(buffer);
        var requestBodyString = buffer.readUtf8();
        String signedRequestBodyString = FuiouUtils.sign(requestBodyString, app.getPrivateKey());
        var newBody = new FormBody.Builder()
                .add("req", URLEncoder.encode(signedRequestBodyString, FuiouUtils.CHARSET))
                .build();
        var newRequest = request.newBuilder()
                .post(newBody).build();
        var response = chain.proceed(newRequest);
        var responseBody = response.body();
        if (responseBody == null) {
            return response;
        }
        var responseBodyString = URLDecoder.decode(responseBody.string(), FuiouUtils.CHARSET);
        boolean verified = FuiouUtils.verify(responseBodyString, app.getPublicKey());
        if (!verified) {
            log.error("验签不通过，报文-{}", responseBodyString);
            throw new RuntimeException("验签不通过");
        }
        return response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString)).build();
    }
}
