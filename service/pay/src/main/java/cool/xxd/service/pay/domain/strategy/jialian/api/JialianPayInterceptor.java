package cool.xxd.service.pay.domain.strategy.jialian.api;

import com.alibaba.fastjson2.JSON;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import cool.xxd.infra.cache.CacheUtil;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.strategy.jialian.ApiResponse;
import cool.xxd.service.pay.domain.strategy.jialian.JialianPayChannelConfig;
import cool.xxd.service.pay.domain.strategy.jialian.TokenRequest;
import cool.xxd.service.pay.domain.strategy.jialian.TradeInfoDTO;
import cool.xxd.service.pay.domain.strategy.jialian.enums.JialianAuthorizeTypeEnum;
import cool.xxd.service.pay.domain.strategy.jialian.enums.JialianPayStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class JialianPayInterceptor extends BasePathMatchInterceptor {

    private static final String ACCESS_TOKEN_QUERY = "access_token";
    private final CacheUtil cacheUtil;
    private final JialianPayTokenApi jialianPayTokenApi;
    private static final String APP_KEY = "ea977c059ab547c1af472d1b896b62fc";
    private static final String APP_SECRET = "WDxxXBbhrZlpVN7hwkd5zfpaHuta3xWAbSwbyG3Pov";

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        var jialianPayChannelConfig = request.tag(JialianPayChannelConfig.class);
        if (jialianPayChannelConfig == null) {
            return chain.proceed(request);
        }
        var newRequest = newRequestWithAccessToken(request, jialianPayChannelConfig);
        var response = chain.proceed(newRequest);
        var responseBody = response.body();
        if (responseBody == null) {
            return response;
        }
        var responseBodyString = responseBody.string();
        var apiResponse = JSON.parseObject(responseBodyString, ApiResponse.class);
        // 如果token过期了重新请求
        if (apiResponse.getErrCode() != null && apiResponse.getErrCode() == 401) {
            log.info("access_token过期，错误码-{}", apiResponse.getErrMessage());
            generateAccessToken(jialianPayChannelConfig);
            newRequest = newRequestWithAccessToken(request, jialianPayChannelConfig);
            response = chain.proceed(newRequest);
            responseBody = response.body();
            if (responseBody == null) {
                return response;
            }
            responseBodyString = responseBody.string();
            apiResponse = JSON.parseObject(responseBodyString, ApiResponse.class);
        }
        if (BooleanUtils.isFalse(apiResponse.getSuccess())) {
            // FIXME 当成支付失败处理，嘉联特殊的逻辑
            if ("NO_EXIST_EFFECTIVE_TRADE".equals(apiResponse.getCode())) {
                var tradeInfoDTO = buildTradeCanceledResponse();
                log.warn("交易不存在，模拟嘉联支付失败返回值-{}", JSON.toJSONString(tradeInfoDTO));
                return response.newBuilder()
                        .body(ResponseBody.create(responseBody.contentType(), JSON.toJSONString(tradeInfoDTO))).build();
            }
            log.error("请求嘉联支付失败-{}", apiResponse.getMsg());
            throw new BusinessException(apiResponse.getMsg());
        }
        return response.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(), JSON.toJSONString(apiResponse.getData()))).build();
    }

    private TradeInfoDTO buildTradeCanceledResponse() {
        var tradeInfoDTO = new TradeInfoDTO();
        tradeInfoDTO.setPayStatus(JialianPayStatusEnum.CANCELED.getCode());
        return tradeInfoDTO;
    }

    private Request newRequestWithAccessToken(Request request, JialianPayChannelConfig jialianPayChannelConfig) {
        var newUrl = request.url().newBuilder()
                .addQueryParameter(ACCESS_TOKEN_QUERY, getAccessToken(jialianPayChannelConfig))
                .build();
        return request.newBuilder().url(newUrl).build();
    }

    private String getAccessToken(JialianPayChannelConfig jialianPayChannelConfig) {
        return cacheUtil.load(getAccessTokenCacheKey(), String.class).orElseGet(() -> this.generateAccessToken(jialianPayChannelConfig));
    }

    private String generateAccessToken(JialianPayChannelConfig jialianPayChannelConfig) {
        var tokenRequest = new TokenRequest();
        tokenRequest.setAppKey(APP_KEY);
        tokenRequest.setAppSecret(APP_SECRET);
        tokenRequest.setAuthorizeType(JialianAuthorizeTypeEnum.AUTH_CODE.getCode());
        tokenRequest.setCode(jialianPayChannelConfig.getCode());
        var token = jialianPayTokenApi.token(tokenRequest);
        var accessToken = token.getAccessToken();
        var accessTokenCacheKey = getAccessTokenCacheKey();
        cacheUtil.save(accessTokenCacheKey, accessToken, Duration.ofMillis(token.getExpires()).minus(Duration.ofHours(1)));
        return accessToken;
    }

    private String getAccessTokenCacheKey() {
        return CacheKeys.JIALIAN_ACCESS_TOKEN;
    }
}
