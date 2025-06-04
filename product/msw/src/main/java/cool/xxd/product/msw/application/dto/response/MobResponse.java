package cool.xxd.product.msw.application.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MobResponse {
    private String code;
    private String name;

    // 基础属性模板
    private Integer level;
    private Integer maxHp;
    private Integer maxMp;
    private Integer exp;

    // 战斗属性模板
    private Integer physicalDefense;
    private Integer magicalDefense;
    private Integer evasion;
    private Integer baseAccuracyRequirement;
    private BigDecimal accuracyLevelPenalty;
}
