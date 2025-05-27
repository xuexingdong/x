package cool.xxd.infra.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class SignUtil {

    private SignUtil() {

    }

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static String sign(SignAlgorithm algorithm, String base64PrivateKey, String data) {
        PrivateKey privateKey;
        try {
            privateKey = stringToPrivateKey(base64PrivateKey);
        } catch (GeneralSecurityException e) {
            throw new CryptoException(e);
        }
        return sign(algorithm, privateKey, data);
    }

    private static String sign(SignAlgorithm algorithm, PrivateKey privateKey, String data) {
        try {
            var signature = Signature.getInstance(algorithm.getValue());
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (GeneralSecurityException e) {
            throw new CryptoException(e);
        }
    }

    public static boolean verify(SignAlgorithm algorithm, String base64PublicKey, String data, String sign) {
        PublicKey publicKey;
        try {
            publicKey = stringToPublicKey(base64PublicKey);
        } catch (GeneralSecurityException e) {
            throw new CryptoException(e);
        }
        return verify(algorithm, publicKey, data, sign);
    }

    private static boolean verify(SignAlgorithm algorithm, PublicKey publicKey, String data, String base64Signature) {
        try {
            var signature = Signature.getInstance(algorithm.getValue());
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.getDecoder().decode(base64Signature));
        } catch (GeneralSecurityException e) {
            throw new CryptoException(e);
        }
    }

    private static PrivateKey stringToPrivateKey(String base64PrivateKey) throws GeneralSecurityException {
        var keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        var keySpec = new PKCS8EncodedKeySpec(keyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static PublicKey stringToPublicKey(String base64PublicKey) throws GeneralSecurityException {
        var keyBytes = Base64.getDecoder().decode(base64PublicKey);
        var keySpec = new X509EncodedKeySpec(keyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // 微信示例
    public static void main(String[] args) {
        var sign = SignUtil.sign(SignAlgorithm.SHA256withRSA,
                "MIIEogIBAAKCAQEAptpm+qvIDCh/9wjU26SQCK26ogYkBhDrYxnAaw2JbbBsp1oDbHKk+1r381NeBUG2HEFAuU+Fr72u5ot3yKdzoF/FajAzQNKnm569/D3upKoi8mYBaST15Uig8j8qoUW1U217LL0jEHlSnHV3lcaDTXqDpTRR4Bfz9IqOgJgFZ8/oTfEomSrjrLYef81Eyxr7ZIMQXEKKEK7V4UXKS0+/fDsiG/cXidhzt8UbTL9vqXqxM2+IDImyO+FAc/tkBG55LmzxPto1Nq0WbnZzRM/wTzrd0I/8NlevxtFbphg4evlHjFNI7+GrqR87ViEwuAJJ9Je5QQjct5YJfFRWiZ5CMQIDAQABAoIBAGBi/GhEgezcHIg1ltlHaFlLGuxsRbUnYwM9phVxnXk7GJlYe2/TjpERjPkIqOC6hBwwadZjJORP3FCcMtc8PKRhjuZ377O7vU0915x2nnyLOGL1IE2AJ3iLi0ZFzTea0FPgg+5lWHM00s9FYI6qPcGtS41M+xtMWwZiYE3TBBRibHiY8ugGyaNAhiMKehyW05uApjlIF55wwCGxBkyESJpGRR/6853iHke6Ge+xVcMa9QmQdoH0QqL/8kT28PL568mJJr0Ow/83t4+dPe70YPzKAxgUnaDsHJqO+b8qH69AEs8rTI5h2Mon6pH+bJT66KUoiXhn+Kf+4LSshenRP10CgYEA1QJSfuFOWVRjrg3N/rAIc/Ak84BTZavbyrkqBSuoTs9i/nMI/hOzVxpDntg7Bx2Tctl6sZO3GioTxKdc/YYaTKci1TKBbeginpsqEQVgwkMCy8HpvUmRfyAMqLwZC4h9+j+NiZtuoFJDTCgv+WYbasX+kWYEUM21bnSYuO7yEQsCgYEAyIdPr9uzqPgzN34Tmx+CNTa16VjhBh+zkBtXRLDLhWBeIYxoYNJARD98Pb1XZdvpkZZWSk7MfaKo2/DomzyyyB/MbHWwAdFi3yb4y7uMJfyC1MzdUSNN3Vp579hJxHkJ+nN4Ys76yfcEeVOLnvUT1Z0KKCdIWRdT1Lgi+X1itzMCgYBJUXlPzwGG4fNFj97d0X23Wmt9nSgXkOYgi0eZbAOMzPmIF9R6kBFk49dur4Lx2g5Ms+r1gKC/0sfnIqxxX11iEQ1+UNoYGJUB/uql3TIG68XkmKR50P7RwRhaZBRC0gJ6xrFTMjsL2ATuC88niyvYvrn3FiRaI9RVZrDCxwxvLQKBgEXW4okEAqGBuAzGqztmkOnJoTehDdYdKmOxMgapcGiGdKJIjX3THDDoz3ONQyglnEZpTqpYoV3MTfU0BT8zt6x9bqwDnQY1D7NalmIWcqw0Mri8lQQSQKcsQLWo5aA466G/n5kCL1Qx5OwAjesRvhOyuvvbGpZ0ymyWqQ+tfLkDAoGATcul1L8y5D/wNVP1GXbXMZfBsFP3bbqy8c+Ashm6g8OLm2mGNntd5Z6h1KkID7Yksh+dZ6t7XaPBtGACXX5Eryr537JVvdX8hAVCp5HVtaN/9VBVP8Ka2e4sVS/xeNgOMQ7uzhRPBJ8HiTmdI1nHhDnYQpGiBgQn0Z5RAkSvFMk=",
                "POST\n" +
                        "/v3/pay/transactions/jsapi\n" +
                        "1554208460\n" +
                        "593BEC0C930BF1AFEB40B4A08C8FB242\n" +
                        "{\"appid\":\"wxd678efh567hg6787\",\"mchid\":\"1230000109\",\"description\":\"Image形象店-深圳腾大-QQ公仔\",\"out_trade_no\":\"1217752501201407033233368018\",\"notify_url\":\"https://www.weixin.qq.com/wxpay/pay.php\",\"amount\":{\"total\":100,\"currency\":\"CNY\"},\"payer\":{\"openid\":\"oUpF8uMuAJO_M2pxb1Q9zNjWeS6o\"}}\n");
        System.out.println(sign.equals("gEuexJ547PHFV77TQ6eiE4tphVYfWfUe1Wc2dBmVnoMYU2rl/M4zhw+b3vBhuMw6AC7pteNkryLA7UWU2h+umo0OdSuuLm1++O3NckQPCSfm6dypsjn4GYm84KMqXWFrhFmyxEwIdEJDr3w1UYfxOcu55OQupfLkrt/ZzuOspnliJFrPzGQFUk7lGqMMtpz3EfbDUNxnVsHblORg3hVmuYNmbGWnS2ovU30Y2Q+iKFDxzkaXBk8LTy6HzvxizRo6Q+J4SVM7O0hKXfgo1QdI68kpzNULb3EVBXlhTyPUzhkHzzLxECL1qHl3HH2hEv8++C+4wBlsagF3j/O6PABojA=="));
    }
}
