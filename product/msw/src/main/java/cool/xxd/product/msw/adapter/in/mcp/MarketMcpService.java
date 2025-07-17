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
                                                               @ToolParam(description = "货币类型，可选", required = false) Currency currency,
                                                               @ToolParam(description = "是否显示Discord用户名", required = false) Boolean showDiscordUsername,
                                                               @ToolParam(description = "时间范围（小时），可选值：24/6/3/1", required = false) Integer timeRange,
                                                               @ToolParam(description = "页码", required = false) Integer page,
                                                               @ToolParam(description = "每页数量", required = false) Integer limit,
                                                               @ToolParam(description = "排序字段", required = false) String sortKey,
                                                               @ToolParam(description = "排序方向，asc或desc", required = false) String sortDirection,
                                                               @ToolParam(description = "最小金额", required = false) Long minAmount,
                                                               @ToolParam(description = "最大金额", required = false) Long maxAmount,
                                                               @ToolParam(description = "最小数量", required = false) Integer minQuantity,
                                                               @ToolParam(description = "最大数量", required = false) Integer maxQuantity,
                                                               @ToolParam(description = "最小HP", required = false) Integer minHp,
                                                               @ToolParam(description = "最大HP", required = false) Integer maxHp,
                                                               @ToolParam(description = "最小MP", required = false) Integer minMp,
                                                               @ToolParam(description = "最大MP", required = false) Integer maxMp,
                                                               @ToolParam(description = "最小物理攻击力", required = false) Integer minPhysicalAttack,
                                                               @ToolParam(description = "最大物理攻击力", required = false) Integer maxPhysicalAttack,
                                                               @ToolParam(description = "最小魔法攻击力", required = false) Integer minMagicAttack,
                                                               @ToolParam(description = "最大魔法攻击力", required = false) Integer maxMagicAttack,
                                                               @ToolParam(description = "最小力量", required = false) Integer minStrength,
                                                               @ToolParam(description = "最大力量", required = false) Integer maxStrength,
                                                               @ToolParam(description = "最小敏捷", required = false) Integer minDexterity,
                                                               @ToolParam(description = "最大敏捷", required = false) Integer maxDexterity,
                                                               @ToolParam(description = "最小智力", required = false) Integer minIntelligence,
                                                               @ToolParam(description = "最大智力", required = false) Integer maxIntelligence,
                                                               @ToolParam(description = "最小运气", required = false) Integer minLuck,
                                                               @ToolParam(description = "最大运气", required = false) Integer maxLuck,
                                                               @ToolParam(description = "最小主属性总和", required = false) Integer minTotalMainStats,
                                                               @ToolParam(description = "最大主属性总和", required = false) Integer maxTotalMainStats,
                                                               @ToolParam(description = "最小命中", required = false) Integer minAccuracy,
                                                               @ToolParam(description = "最大命中", required = false) Integer maxAccuracy,
                                                               @ToolParam(description = "最小回避", required = false) Integer minAvoidance,
                                                               @ToolParam(description = "最大回避", required = false) Integer maxAvoidance,
                                                               @ToolParam(description = "最小跳跃", required = false) Integer minJump,
                                                               @ToolParam(description = "最大跳跃", required = false) Integer maxJump,
                                                               @ToolParam(description = "最小移动速度", required = false) Integer minMovement,
                                                               @ToolParam(description = "最大移动速度", required = false) Integer maxMovement,
                                                               @ToolParam(description = "最小卷轴次数", required = false) Integer minScrollCount,
                                                               @ToolParam(description = "最大卷轴次数", required = false) Integer maxScrollCount) {

        var transactionRequest = new TransactionRequest();
        transactionRequest.setItemName(itemName);
        transactionRequest.setTransactionType(transactionType.getCode());
        transactionRequest.setIsActive(isActive);
        transactionRequest.setCurrency(currency.getCode());
        transactionRequest.setShowDiscordUsername(showDiscordUsername);
        transactionRequest.setTimeRange(timeRange);
        transactionRequest.setPage(page);
        transactionRequest.setLimit(limit);
        transactionRequest.setSortKey(sortKey);
        transactionRequest.setSortDirection(sortDirection);

        // 设置金额和数量范围
        transactionRequest.setMinAmount(minAmount);
        transactionRequest.setMaxAmount(maxAmount);
        transactionRequest.setMinQuantity(minQuantity);
        transactionRequest.setMaxQuantity(maxQuantity);

        // 设置装备属性范围
        transactionRequest.setMinHp(minHp);
        transactionRequest.setMaxHp(maxHp);
        transactionRequest.setMinMp(minMp);
        transactionRequest.setMaxMp(maxMp);
        transactionRequest.setMinPhysicalAttack(minPhysicalAttack);
        transactionRequest.setMaxPhysicalAttack(maxPhysicalAttack);
        transactionRequest.setMinMagicAttack(minMagicAttack);
        transactionRequest.setMaxMagicAttack(maxMagicAttack);
        transactionRequest.setMinStrength(minStrength);
        transactionRequest.setMaxStrength(maxStrength);
        transactionRequest.setMinDexterity(minDexterity);
        transactionRequest.setMaxDexterity(maxDexterity);
        transactionRequest.setMinIntelligence(minIntelligence);
        transactionRequest.setMaxIntelligence(maxIntelligence);
        transactionRequest.setMinLuck(minLuck);
        transactionRequest.setMaxLuck(maxLuck);
        transactionRequest.setMinTotalMainStats(minTotalMainStats);
        transactionRequest.setMaxTotalMainStats(maxTotalMainStats);
        transactionRequest.setMinAccuracy(minAccuracy);
        transactionRequest.setMaxAccuracy(maxAccuracy);
        transactionRequest.setMinAvoidance(minAvoidance);
        transactionRequest.setMaxAvoidance(maxAvoidance);
        transactionRequest.setMinJump(minJump);
        transactionRequest.setMaxJump(maxJump);
        transactionRequest.setMinMovement(minMovement);
        transactionRequest.setMaxMovement(maxMovement);
        transactionRequest.setMinScrollCount(minScrollCount);
        transactionRequest.setMaxScrollCount(maxScrollCount);

        return marketService.queryTransactions(transactionRequest);
    }
}
