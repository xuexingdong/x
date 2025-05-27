package cool.xxd.service.pay.domain.utils;

import jakarta.annotation.Nullable;

/**
 * 微信支付：<a href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1">微信</a>
 * 支付宝：<a href="https://opendocs.alipay.com/open/1f1fe18c_alipay.trade.pay?pathHash=29c9a9ba&ref=api&scene=32">支付宝</a>
 */
public final class PayTypeDetector {

    public enum PayType {
        WECHAT_PAY,
        ALIPAY
    }

    // TODO 银联
    @Nullable
    public static PayType detectPayType(String code) {
        if (code == null || code.isBlank()) {
            return null;
        }
        String trimmedCode = code.trim();
        // 微信支付: 18位纯数字，前缀以10、11、12、13、14、15开头
        if (trimmedCode.matches("^1[0-5]\\d{16}$")) {
            return PayType.WECHAT_PAY;
        }

        // 支付宝支付: 25~30开头，16~24位数字 或者 "fp"开头的35位字符串
        if (trimmedCode.matches("^2[5-9]\\d{14,22}$") || trimmedCode.matches("^3[0]\\d{14,22}$") || trimmedCode.matches("^fp\\w{33}$")) {
            return PayType.ALIPAY;
        }

        // 未知支付方式
        return null;
    }
}
