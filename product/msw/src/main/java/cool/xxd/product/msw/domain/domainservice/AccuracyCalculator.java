package cool.xxd.product.msw.domain.domainservice;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccuracyCalculator {
    private static final BigDecimal PHYSICAL_BASE = new BigDecimal("55.2");
    private static final BigDecimal PHYSICAL_LEVEL_FACTOR = new BigDecimal("2.15");
    private static final BigDecimal PHYSICAL_AVOID_FACTOR = new BigDecimal("15.0");
    private static final BigDecimal PHYSICAL_ACC1_FACTOR = new BigDecimal("0.5");
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    private static final BigDecimal MAGICAL_AVOID_FACTOR = new BigDecimal("0.04");
    private static final BigDecimal MAGICAL_ACC1_FACTOR = new BigDecimal("0.41");
    private static final BigDecimal MAGICAL_RATIO_A = new BigDecimal("-0.7011618132");
    private static final BigDecimal MAGICAL_RATIO_B = new BigDecimal("1.702139835");

    /**
     * 计算物理攻击命中率
     *
     * @param charLevel 角色等级
     * @param accuracy  命中值
     * @param mobLevel  怪物等级
     * @param mobAvoid  怪物闪避值
     * @return 命中率计算结果
     */
    public static AccuracyResult calculatePhysical(int charLevel, int accuracy, int mobLevel, int mobAvoid) {
        // 计算等级差
        int levelDiff = Math.max(0, mobLevel - charLevel);

        // 转换为BigDecimal
        BigDecimal levelDiffBD = new BigDecimal(levelDiff);
        BigDecimal accuracyBD = new BigDecimal(accuracy);
        BigDecimal mobAvoidBD = new BigDecimal(mobAvoid);

        // 物理攻击命中率计算
        // acc100 = (55.2 + 2.15 * levelDiff) * (mobAvoid / 15.0)
        BigDecimal acc100 = PHYSICAL_BASE.add(PHYSICAL_LEVEL_FACTOR.multiply(levelDiffBD))
                .multiply(mobAvoidBD.divide(PHYSICAL_AVOID_FACTOR, 10, RoundingMode.HALF_UP));

        // acc1 = acc100 * 0.5 + 1
        BigDecimal acc1 = acc100.multiply(PHYSICAL_ACC1_FACTOR).add(ONE);

        // accRatio = 100 * ((accuracy - (acc100 * 0.5)) / (acc100 * 0.5))
        BigDecimal accRatio = HUNDRED.multiply(
                accuracyBD.subtract(acc100.multiply(PHYSICAL_ACC1_FACTOR))
                        .divide(acc100.multiply(PHYSICAL_ACC1_FACTOR), 10, RoundingMode.HALF_UP)
        );

        // 限制命中率范围在0-100之间
        accRatio = accRatio.min(HUNDRED).max(BigDecimal.ZERO);

        return new AccuracyResult(acc1, acc100, accRatio);
    }

    /**
     * 计算魔法攻击命中率
     *
     * @param charLevel 角色等级
     * @param intel     智力值
     * @param luk       幸运值
     * @param mobLevel  怪物等级
     * @param mobAvoid  怪物闪避值
     * @return 命中率计算结果
     */
    public static AccuracyResult calculateMagical(int charLevel, int intel, int luk, int mobLevel, int mobAvoid) {
        // 计算等级差
        int levelDiff = Math.max(0, mobLevel - charLevel);

        // 当前命中值 = 向下取整(智力/10) + 向下取整(幸运/10)
        int curAcc = Math.floorDiv(intel, 10) + Math.floorDiv(luk, 10);

        // 转换为BigDecimal
        BigDecimal levelDiffBD = new BigDecimal(levelDiff);
        BigDecimal curAccBD = new BigDecimal(curAcc);
        BigDecimal mobAvoidBD = new BigDecimal(mobAvoid);

        // acc100 = (mobAvoid + 1) * (1 + 0.04 * levelDiff)
        BigDecimal acc100 = mobAvoidBD.add(ONE)
                .multiply(ONE.add(MAGICAL_AVOID_FACTOR.multiply(levelDiffBD)))
                .setScale(0, RoundingMode.FLOOR);

        // acc1 = round(0.41 * acc100)
        BigDecimal acc1 = acc100.multiply(MAGICAL_ACC1_FACTOR)
                .setScale(0, RoundingMode.HALF_UP);

        // accPart = (curAcc - acc1 + 1) / (acc100 - acc1 + 1)
        BigDecimal accPart = curAccBD.subtract(acc1).add(ONE)
                .divide(acc100.subtract(acc1).add(ONE), 10, RoundingMode.HALF_UP);
        accPart = accPart.min(ONE);

        // accRatio = (-0.7011618132 * accPart^2 + 1.702139835 * accPart) * 100
        BigDecimal accRatio = MAGICAL_RATIO_A.multiply(accPart.pow(2))
                .add(MAGICAL_RATIO_B.multiply(accPart))
                .multiply(HUNDRED);

        // 限制命中率范围在0-100之间
        accRatio = accRatio.min(HUNDRED).max(BigDecimal.ZERO);

        return new AccuracyResult(acc1, acc100, accRatio);
    }

    /**
     * 命中率计算结果
     */
    @Getter
    public static class AccuracyResult {
        private final BigDecimal acc1;      // 1%命中所需值
        private final BigDecimal acc100;    // 100%命中所需值
        private final BigDecimal accRatio;  // 实际命中率

        public AccuracyResult(BigDecimal acc1, BigDecimal acc100, BigDecimal accRatio) {
            this.acc1 = acc1.setScale(3, RoundingMode.HALF_UP);
            this.acc100 = acc100.setScale(3, RoundingMode.HALF_UP);
            this.accRatio = accRatio.setScale(3, RoundingMode.HALF_UP);
        }

        @Override
        public String toString() {
            return String.format("命中率计算结果{1%%命中所需值=%s, 100%%命中所需值=%s, 实际命中率=%s%%}",
                    acc1.toPlainString(), acc100.toPlainString(), accRatio.toPlainString());
        }
    }
}