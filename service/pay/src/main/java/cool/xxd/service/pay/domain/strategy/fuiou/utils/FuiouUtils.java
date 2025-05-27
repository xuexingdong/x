package cool.xxd.service.pay.domain.strategy.fuiou.utils;

import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
import cool.xxd.service.pay.domain.strategy.fuiou.enums.TradeTypeEnum;
import cool.xxd.service.pay.domain.strategy.fuiou.enums.TransStatEnum;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class FuiouUtils {

    public static final Charset CHARSET = Charset.forName("GBK"); // 编码方式

    private static final String ALGORITHM = "MD5withRSA"; // 签名算法

    private static final Map<TransStatEnum, PayStatusEnum> PAY_RESULT_STATUS_MAP = new HashMap<>();

    static {
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.SUCCESS, PayStatusEnum.PAID);
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.REFUND, PayStatusEnum.REFUNDED);
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.NOTPAY, PayStatusEnum.UNPAID);
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.CLOSED, PayStatusEnum.CLOSED);
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.REVOKED, PayStatusEnum.REVOKED);
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.USERPAYING, PayStatusEnum.PAYING);
        PAY_RESULT_STATUS_MAP.put(TransStatEnum.PAYERROR, PayStatusEnum.FAILED);
    }

    // FIXME 尚未实现
    public static TradeTypeEnum getTradeType(String payTypeCode, TransModeEnum transMode) {
        return TradeTypeEnum.LETPAY;
    }

    // FIXME 尚未实现
    public static OrderTypeEnum getOrderType(String payTypeCode, TransModeEnum transMode) {
        return OrderTypeEnum.WECHAT;
    }

    public static PayStatusEnum getPayStatus(TransStatEnum transStat) {
        return PAY_RESULT_STATUS_MAP.get(transStat);
    }

    public static String sign(String originalBodyString, String privateKey) {
        try {
            var requestDocument = parseDocument(originalBodyString);
            var requestSignData = getSignData(requestDocument);
            var sign = sign(requestSignData, stringToPrivateKey(privateKey));
            return addSign(requestDocument, sign);
        } catch (Exception e) {
            log.error("签名出错，参数-{}", originalBodyString, e);
            throw new RuntimeException("签名出错");
        }
    }

    public static boolean verify(String responseBodyString, String publicKey) {
        try {
            var document = parseDocument(responseBodyString);
            var responseSign = document.getElementsByTagName("sign").item(0).getTextContent();
            var responseSignData = getSignData(document);
            return verify(responseSignData, responseSign, stringToPublicKey(publicKey));
        } catch (Exception e) {
            log.error("验签出错，报文-{}", responseBodyString, e);
            throw new RuntimeException("验签出错");
        }
    }

    private static String addSign(Document document, String sign) throws Exception {
        var newNode = document.createElement("sign");
        newNode.setTextContent(sign);
        // 将新节点添加到根元素
        document.getDocumentElement().appendChild(newNode);
        document.setXmlStandalone(true);
        var outputStream = new ByteArrayOutputStream();
        try {
            var transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, CHARSET.name());
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(outputStream));
        } catch (TransformerException e) {
            throw new IOException("Failed to transform Document to String", e);
        }
        return outputStream.toString(CHARSET);
    }

    private static Document parseDocument(String content) throws Exception {
        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(content)));
    }

    private static String getSignData(Document document) {
        var rootElement = document.getDocumentElement();
        var nodeList = rootElement.getChildNodes();
        var sortedParams = new TreeMap<String, String>();
        for (var i = 0; i < nodeList.getLength(); i++) {
            var node = nodeList.item(i);
            if (needSign(node.getNodeName())) {
                sortedParams.put(node.getNodeName(), node.getTextContent());
            }
        }
        return sortedParams.entrySet().stream()
                .map(e -> e.getKey() + "=" + Objects.requireNonNullElse(e.getValue(), ""))
                .collect(Collectors.joining("&"));
    }

    private static String sign(String content, PrivateKey privateKey) throws Exception {
        var signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        signature.update(content.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    private static boolean verify(String content, String sign, PublicKey publicKey) throws Exception {
        var signature = Signature.getInstance(ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(content.getBytes(CHARSET));
        return signature.verify(Base64.getDecoder().decode(sign));
    }

    private static boolean needSign(String nodeName) {
        return !nodeName.equals("sign") && !nodeName.startsWith("reserved");
    }

    // 将 Base64 编码的字符串转换为 PublicKey 对象
    public static PublicKey stringToPublicKey(String publicKeyString) throws Exception {
        var keyBytes = Base64.getDecoder().decode(publicKeyString);
        var keySpec = new X509EncodedKeySpec(keyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // 将 Base64 编码的字符串转换为 PrivateKey 对象
    public static PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        var keyBytes = Base64.getDecoder().decode(privateKeyString);
        var keySpec = new PKCS8EncodedKeySpec(keyBytes);
        var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
