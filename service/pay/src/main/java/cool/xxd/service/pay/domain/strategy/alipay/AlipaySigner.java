package cool.xxd.service.pay.domain.strategy.alipay;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.AES;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlipaySigner {

    private final AlipayConfig alipayConfig;
    private final Sign sign;
    private final AES aes;

    private static final byte[] IV = new byte[16];

    static {
        Arrays.fill(IV, (byte) 0);
    }

    public AlipaySigner(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
        this.sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, alipayConfig.getPrivateKey(), alipayConfig.getAlipayPublicKey());
        this.aes = new AES(Mode.CBC, Padding.PKCS5Padding, Base64.getDecoder().decode(alipayConfig.getEncryptKey().getBytes()), IV);
    }

    public String encrypt(String content) {
        return aes.encryptBase64(content, StandardCharsets.UTF_8);
    }

    public String buildAuthorization(String httpMethod,
                                     String httpRequestUrl,
                                     String httpRequestBody,
                                     String appAuthToken) {
        var authString = buildAuthString();
        var signature = sign(authString, httpMethod, httpRequestUrl, httpRequestBody, appAuthToken);
        return String.format("%s %s,sign=%s", "ALIPAY-" + SignAlgorithm.SHA256withRSA, authString, signature);
    }

    private String buildAuthString() {
        var nonce = UUID.randomUUID().toString();
        var timestamp = String.valueOf(Instant.now().toEpochMilli());
        return String.format("app_id=%s,nonce=%s,timestamp=%s", alipayConfig.getAppId(), nonce, timestamp);
    }

    private String sign(String authString, String httpMethod,
                        String httpRequestUrl,
                        String httpRequestBody,
                        String appAuthToken) {
        var signContent = buildSignContent(authString, httpMethod, httpRequestUrl, httpRequestBody, appAuthToken);
        return Base64.getEncoder().encodeToString(this.sign.sign(signContent.getBytes()));
    }

    private static String buildSignContent(String authString, String httpMethod, String httpRequestUrl, String httpRequestBody, String appAuthToken) {
        var arr = new String[]{authString, httpMethod, httpRequestUrl, httpRequestBody, appAuthToken};
        return String.join("\n", arr) + "\n";
    }

    public boolean verify(String timestamp, String alipayNonce, String httpResponseBody, String signature) {
        if (timestamp == null || alipayNonce == null || signature == null) {
            return false;
        }
        var content = String.format("%s\n%s\n%s\n", timestamp, alipayNonce, httpResponseBody);
        return sign.verify(content.getBytes(), Base64.getDecoder().decode(signature.getBytes()));
    }

    public String decrypt(String content) {
        return aes.decryptStr(content, StandardCharsets.UTF_8);
    }

    public boolean verifyNotify(Map<String, String> params) {
        var signContent = params.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("sign")
                        && !entry.getKey().equals("sign_type"))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> URLDecoder.decode(entry.getValue(), StandardCharsets.UTF_8)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        return sign.verify(signContent.getBytes(), Base64.getDecoder().decode(params.get("sign")));
    }
}
