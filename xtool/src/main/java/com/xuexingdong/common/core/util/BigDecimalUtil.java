package cool.xxd.mapstruct.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class BigDecimalUtil {
    private BigDecimalUtil() {

    }

    public static int convertYuanToCents(BigDecimal amountInYuan) {
        Objects.requireNonNull(amountInYuan);
        BigDecimal amountInCents = amountInYuan.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
        return amountInCents.intValueExact();
    }

    public static BigDecimal convertCentsToYuan(Integer amountInCents) {
        Objects.requireNonNull(amountInCents);
        return new BigDecimal(amountInCents).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }
}
