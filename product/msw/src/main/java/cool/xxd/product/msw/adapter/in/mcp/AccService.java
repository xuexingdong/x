package cool.xxd.product.msw.adapter.in.mcp;

import cool.xxd.product.msw.domain.domainservice.AccuracyCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccService {

    @Tool(description = "计算物理命中率")
    public AccuracyCalculator.AccuracyResult calcAcc(@ToolParam(description = "人物等级") int charLevel,
                                                     @ToolParam(description = "人物命中") int accuracy,
                                                     @ToolParam(description = "怪物等级") int mobLevel,
                                                     @ToolParam(description = "怪物闪避值") int mobAvoid
    ) {
        return AccuracyCalculator.calculatePhysical(charLevel, accuracy, mobLevel, mobAvoid);
    }

    @Tool(description = "计算魔法命中率")
    public AccuracyCalculator.AccuracyResult calculatePhysicalAcc(@ToolParam(description = "人物等级") int charLevel,
                                                                  @ToolParam(description = "人物智力") int intel,
                                                                  @ToolParam(description = "人物幸运") int luk,
                                                                  @ToolParam(description = "怪物等级") int mobLevel,
                                                                  @ToolParam(description = "怪物闪避值") int mobAvoid
    ) {
        return AccuracyCalculator.calculateMagical(charLevel, intel, luk, mobLevel, mobAvoid);
    }
}
