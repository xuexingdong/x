package cool.xxd.product.msw.adapter.in.mcp;

import cool.xxd.product.msw.application.service.MarketService;
import cool.xxd.product.msw.datafetcher.enums.Currency;
import cool.xxd.product.msw.datafetcher.enums.TransactionType;
import cool.xxd.product.msw.datafetcher.request.TransactionRequest;
import cool.xxd.product.msw.datafetcher.response.MarketItem;
import cool.xxd.product.msw.datafetcher.response.PageResponse;
import cool.xxd.product.msw.datafetcher.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketMcpService {

    private final MarketService marketService;

    @Tool(description = "查询所有物品")
    public List<MarketItem> queryAllMarketItems() {
        return marketService.queryAllItem();
    }

    @Tool(description = "根据名称模糊查询物品，仅支持繁体字")
    public List<MarketItem> queryMarketItems(@ToolParam(description = "物品名称，为空查询全部，仅支持繁体字") String itemName) {
        return marketService.queryItem(itemName);
    }

    @Tool(description = "查询交易，需要从queryMarketItems中查出准确的name后再调用此函数")
    public PageResponse<TransactionResponse> queryTransactions(@ToolParam(description = "物品名称，仅支持精确匹配") String itemName,
                                                               @ToolParam(description = "交易类型") TransactionType transactionType,
                                                               @ToolParam(description = "是否仅显示有效交易") Boolean isActive,
                                                               @ToolParam(description = "货币类型") Currency currency,
                                                               @ToolParam(description = "是否显示Discord用户名", required = false) Boolean showDiscordUsername,
                                                               @ToolParam(description = "时间范围（小时），可选值：24/6/3/1", required = false) Integer timeRange,
                                                               @ToolParam(description = "页码", required = false) Integer page,
                                                               @ToolParam(description = "每页数量", required = false) Integer limit,
                                                               @ToolParam(description = "排序字段", required = false) String sortKey,
                                                               @ToolParam(description = "排序方向，asc或desc", required = false) String sortDirection) {

        var transactionRequest = new TransactionRequest();
        transactionRequest.setItemName(itemName);
        transactionRequest.setTransactionType(transactionType.getCode());
        transactionRequest.setIsActive(isActive);
        transactionRequest.setCurrency(currency.getCode());
        transactionRequest.setTimeRange(timeRange);
        return marketService.queryTransactions(transactionRequest);
    }
}
