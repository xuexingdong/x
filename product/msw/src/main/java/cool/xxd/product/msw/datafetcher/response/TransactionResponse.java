package cool.xxd.product.msw.datafetcher.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class TransactionResponse {
    private Long id;

    private String characterId;

    private String itemType;

    private String itemName;

    private String currency;

    private long amount;

    private int quantity;

    private String transactionType;

    private String createdAt;

}
