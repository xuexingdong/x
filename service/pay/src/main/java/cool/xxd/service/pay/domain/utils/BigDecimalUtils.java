package cool.xxd.service.pay.domain.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BigDecimalUtils {
    private BigDecimalUtils() {

    }

    public static int convertYuanToCents(BigDecimal amountInYuan) {
        // 转换元为分，使用 setScale 来确保转换后的精度为整数
        BigDecimal amountInCents = amountInYuan.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
        // 转换为字符串
        return amountInCents.intValueExact();
    }

    public static BigDecimal convertCentsToYuan(Integer amountInCents) {
        // 转换分为元，使用 setScale 来确保转换后的精度为小数点后两位
        return new BigDecimal(amountInCents).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }
}
