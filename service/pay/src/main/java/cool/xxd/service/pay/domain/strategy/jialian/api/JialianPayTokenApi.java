package cool.xxd.service.pay.domain.strategy.jialian.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import cool.xxd.service.pay.domain.strategy.jialian.TokenRequest;
import cool.xxd.service.pay.domain.strategy.jialian.TokenResponse;
import org.springframework.stereotype.Component;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 文档地址 <a href="https://mtcbeijing.feishu.cn/wiki/NpSGwpjaRi3eW9k8rV4cjXt0nkc">ISV获取和刷新 accessToken</a>
 */
@Component
@RetrofitClient(baseUrl = "${pay.jialian.base-url}")
@Intercept(handler = JialianPayGetAccessTokenInterceptor.class)
public interface JialianPayTokenApi {

    @POST("auth/token")
    TokenResponse token(@Body TokenRequest tokenRequest);

}
