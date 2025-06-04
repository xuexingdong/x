package cool.xxd.product.msw.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("msw_mob")
public class MobDO extends XBaseDO {
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
