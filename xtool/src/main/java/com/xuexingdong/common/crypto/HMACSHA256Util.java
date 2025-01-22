package cool.xxd.mapstruct.crypto;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HMACSHA256Util {
    private HMACSHA256Util() {

    }

    // 使用 HMAC-SHA256 进行签名
    public static String sign(String data, String secretKey) {
        try {
            // 创建 HMAC-SHA256 的 Mac 实例
            Mac mac = Mac.getInstance(HmacAlgorithm.HmacSHA256.getValue());
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HmacAlgorithm.HmacSHA256.getValue());
            mac.init(secretKeySpec);
            // 执行 HMAC 签名
            byte[] signedBytes = mac.doFinal(data.getBytes());
            return Hex.encodeHexString(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException("HMAC-SHA256签名失败", e);
        }
    }
}
