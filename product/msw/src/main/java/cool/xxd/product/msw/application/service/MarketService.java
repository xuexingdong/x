package cool.xxd.product.msw.application.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import cool.xxd.product.msw.datafetcher.MarketApi;
import cool.xxd.product.msw.datafetcher.request.TransactionRequest;
import cool.xxd.product.msw.datafetcher.response.MarketItem;
import cool.xxd.product.msw.datafetcher.response.PageResponse;
import cool.xxd.product.msw.datafetcher.response.TransactionResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class MarketService {

    private List<MarketItem> marketItems;

    private final MarketApi marketApi;

    @PostConstruct
    public void initializeMarketData() {
        this.marketItems = marketApi.getItems().getItems();
    }

    public List<MarketItem> queryAllItem() {
        return marketItems;
    }

    public List<MarketItem> queryItem(String itemName) {
        return marketItems.stream()
                .filter(marketItem -> {
                    String name = marketItem.getName();
                    String[] keywords = itemName.split("");
                    return Arrays.stream(keywords).allMatch(name::contains);
                })
                .toList();
    }

    public PageResponse<TransactionResponse> queryTransactions(TransactionRequest transactionRequest) {
        var map = JSON.parseObject(JSON.toJSONString(transactionRequest), new TypeReference<Map<String, String>>() {
        });
        return marketApi.getTransactions(map);
    }

}
