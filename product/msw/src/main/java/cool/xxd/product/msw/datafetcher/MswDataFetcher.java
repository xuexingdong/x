package cool.xxd.product.msw.datafetcher;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import org.springframework.stereotype.Component;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

@Component
@RetrofitClient(baseUrl = "https://a2983456456.github.io/artale-drop/")
public interface MswDataFetcher {

    @GET("mob.json")
    Map<String, List<String>> getMobData();

    @GET("item.json")
    Map<String, List<String>> getItemData();

    @GET("drop_data.json")
    Map<String, List<String>> getDropData();

}
