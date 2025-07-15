package cool.xxd.product.msw.datafetcher.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private String itemName;
    private String transactionType;
    private Boolean isActive;
    private String currency;
    private Boolean showDiscordUsername;
    // 24, 6, 3, 1
    private Integer timeRange;
    private Integer page;
    private Integer limit;
    private String sortKey;
    private String sortDirection;
}
