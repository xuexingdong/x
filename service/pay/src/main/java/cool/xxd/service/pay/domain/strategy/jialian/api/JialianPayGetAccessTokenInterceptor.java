package cool.xxd.service.pay.domain.strategy.jialian.api;

import com.alibaba.fastjson2.JSON;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.strategy.jialian.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 嘉联支付API拦截器，主要解包返回值用
 */
@Slf4j
@Component
public class JialianPayGetAccessTokenInterceptor extends BasePathMatchInterceptor {

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        var response = chain.proceed(chain.request());
        var responseBody = response.body();
        if (responseBody == null) {
            return response;
        }
        var responseBodyString = responseBody.string();
        var apiResponse = JSON.parseObject(responseBodyString, ApiResponse.class);
        if (apiResponse.getErrCode() != null && !apiResponse.getErrCode().equals(0)) {
            log.error("请求嘉联支付失败-{}", apiResponse.getErrMessage());
            throw new BusinessException(apiResponse.getErrMessage());
        }

        return response.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(), JSON.toJSONString(apiResponse.getData()))).build();
    }
}
