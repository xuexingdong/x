package cool.xxd.product.msw.datafetcher;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import cool.xxd.product.msw.datafetcher.interceptor.MarketCookieInterceptor;
import cool.xxd.product.msw.datafetcher.response.ItemResponse;
import cool.xxd.product.msw.datafetcher.response.PageResponse;
import cool.xxd.product.msw.datafetcher.response.TransactionResponse;
import org.springframework.stereotype.Component;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

@Component
@RetrofitClient(baseUrl = "https://artale-market.org/api/")
@Intercept(handler = MarketCookieInterceptor.class, exclude = "**/static/**")
public interface MarketApi {

    @GET("items/static")
    ItemResponse getItems();

    @GET("transactions")
    PageResponse<TransactionResponse> getTransactions(@QueryMap Map<String, String> options
    );
}
