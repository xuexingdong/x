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
    private String updatedAt;
    private AdditionalProps additionalProps;

    @Data
    private static class AdditionalProps {
        // 装备属性
        private Integer hp;
        private Integer mp;
        private Integer physicalAttack;
        private Integer magicAttack;
        private Integer strength;
        private Integer dexterity;
        private Integer intelligence;
        private Integer luck;
        private Integer totalMainStats;
        private Integer accuracy;
        private Integer avoidance;
        private Integer jump;
        private Integer movement;
        private Integer scrollCount;
    }
}
