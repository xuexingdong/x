package cool.xxd.service.pay.domain.strategy.wechat.api;

import com.alibaba.fastjson2.JSON;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.infra.random.RandomNoGenerator;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.strategy.PayInterceptorUtil;
import cool.xxd.service.pay.domain.strategy.wechat.WechatPayChannelStrategy;
import cool.xxd.service.pay.domain.strategy.wechat.api.response.WechatPayErrorResponse;
import cool.xxd.service.pay.ui.config.WechatPayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WechatPayInterceptor extends BasePathMatchInterceptor {

    private final WechatPayConfig wechatPayConfig;

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        var request = chain.request();
        var app = request.tag(App.class);
        if (app == null) {
            return chain.proceed(request);
        }
        var authorization = getAuthorization(app, request);
        var newRequest = request.newBuilder()
                .header("Accept", "application/json")
                .header("User-Agent", "huizengyun")
                .header("Authorization", authorization)
                .build();
        var response = chain.proceed(newRequest);
        if (response.code() != 200) {
            var responseBody = response.body();
            if (responseBody == null) {
                throw new BusinessException("微信支付调用失败");
            }
            var responseBodyString = responseBody.string();
            log.error("微信支付调用失败-{}", responseBodyString);
            var wechatPayErrorResponse = JSON.parseObject(responseBodyString, WechatPayErrorResponse.class);
            throw new BusinessException(wechatPayErrorResponse.getMessage());
        }
        return response;
    }

    private String getAuthorization(App app, Request request) {
        var method = request.method().toUpperCase();
        var config = app.getConfig();
        var nonce = RandomNoGenerator.generate(32, RandomNoGenerator.CodeType.LETTER);
        var timestamp = String.valueOf(Instant.now().getEpochSecond());
        var spMchid = wechatPayConfig.getSpMchid();
        var serialNo = wechatPayConfig.getSerialNo();
        var base64PrivateKey = config.get("wechat_pay_private_key");
        var signature = WechatPayChannelStrategy.sign(base64PrivateKey,
                method, PayInterceptorUtil.geUrl(request), timestamp, nonce, PayInterceptorUtil.getBody(request));
        return "WECHATPAY2-SHA256-RSA2048 " + Map.of(
                        "mchid", spMchid,
                        "serial_no", serialNo,
                        "nonce_str", nonce,
                        "timestamp", timestamp,
                        "signature", signature
                ).entrySet().stream()
                .map(entry -> String.format("%s=\"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(","));
    }
}
