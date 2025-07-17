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
    
    // 金额范围
    private Long minAmount;
    private Long maxAmount;
    
    // 数量范围
    private Integer minQuantity;
    private Integer maxQuantity;
    
    // 装备属性范围
    private Integer minHp;
    private Integer maxHp;
    private Integer minMp;
    private Integer maxMp;
    private Integer minPhysicalAttack;
    private Integer maxPhysicalAttack;
    private Integer minMagicAttack;
    private Integer maxMagicAttack;
    private Integer minStrength;
    private Integer maxStrength;
    private Integer minDexterity;
    private Integer maxDexterity;
    private Integer minIntelligence;
    private Integer maxIntelligence;
    private Integer minLuck;
    private Integer maxLuck;
    private Integer minTotalMainStats;
    private Integer maxTotalMainStats;
    private Integer minAccuracy;
    private Integer maxAccuracy;
    private Integer minAvoidance;
    private Integer maxAvoidance;
    private Integer minJump;
    private Integer maxJump;
    private Integer minMovement;
    private Integer maxMovement;
    private Integer minScrollCount;
    private Integer maxScrollCount;
}
