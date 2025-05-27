package cool.xxd.service.pay.domain.strategy.alipay.api;

import com.alibaba.fastjson2.JSON;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.strategy.PayInterceptorUtil;
import cool.xxd.service.pay.domain.strategy.alipay.AlipayChannelConfig;
import cool.xxd.service.pay.domain.strategy.alipay.AlipayConfig;
import cool.xxd.service.pay.domain.strategy.alipay.AlipaySigner;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.response.AlipayErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlipayInterceptor extends BasePathMatchInterceptor {

    private final AlipayConfig alipayConfig;

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        var request = chain.request();
        var alipayChannelConfig = request.tag(AlipayChannelConfig.class);
        if (alipayChannelConfig == null) {
            return chain.proceed(request);
        }
        var alipaySigner = new AlipaySigner(alipayConfig);
        var body = PayInterceptorUtil.getBody(request);
        var encryptedBody = alipaySigner.encrypt(body);
        var authorization = alipaySigner.buildAuthorization(request.method().toUpperCase(), PayInterceptorUtil.geUrl(request), encryptedBody, alipayChannelConfig.getApp_auth_token());
        var newRequest = request.newBuilder()
                .header("authorization", authorization)
                .header("alipay-encrypt-type", "AES")
                .header("alipay-app-auth-token", alipayChannelConfig.getApp_auth_token())
                .method(request.method(), RequestBody.create(MediaType.parse("text/plain"), encryptedBody))
                .build();
        var response = chain.proceed(newRequest);
        var responseBody = response.body();
        if (responseBody == null) {
            return response;
        }
        var responseBodyString = responseBody.string();
        if (response.code() != 200) {
            var alipayErrorResponse = JSON.parseObject(responseBodyString, AlipayErrorResponse.class);
            log.error("请求支付宝失败-{}", alipayErrorResponse.getMessage());
            throw new BusinessException(alipayErrorResponse.getMessage());
        }
        var headers = response.headers();
        var verified = alipaySigner.verify(headers.get("alipay-timestamp"),
                headers.get("alipay-nonce"),
                responseBodyString,
                headers.get("alipay-signature")
        );
        if (!verified) {
            log.error("支付宝验签失败");
            throw new BusinessException("支付宝验签失败");
        }
        var decryptedBody = alipaySigner.decrypt(responseBodyString);
        log.info("支付宝返回-{}", JSON.toJSONString(decryptedBody));
        return response.newBuilder().body(ResponseBody.create(MediaType.get("application/json"), decryptedBody)).build();
    }
}
