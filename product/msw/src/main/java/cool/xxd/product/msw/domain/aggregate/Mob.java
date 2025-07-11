package cool.xxd.product.msw.domain.aggregate;

import cool.xxd.product.msw.domain.command.AddMobCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Mob {
    private Long id;
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

    public void update(AddMobCommand addMobCommand) {
        this.name = addMobCommand.getName();
        this.level = addMobCommand.getLevel();
        this.maxHp = addMobCommand.getMaxHp();
        this.maxMp = addMobCommand.getMaxMp();
        this.exp = addMobCommand.getExp();
        this.physicalDefense = addMobCommand.getPhysicalDefense();
        this.magicalDefense = addMobCommand.getMagicalDefense();
        this.evasion = addMobCommand.getEvasion();
        this.baseAccuracyRequirement = addMobCommand.getBaseAccuracyRequirement();
        this.accuracyLevelPenalty = addMobCommand.getAccuracyLevelPenalty();
    }
}
