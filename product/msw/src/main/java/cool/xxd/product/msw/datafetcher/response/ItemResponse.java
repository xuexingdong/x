package cool.xxd.product.msw.datafetcher.response;

import lombok.Data;

import java.util.List;

@Data
public class ItemResponse {
    private List<MarketItem> items;
}
