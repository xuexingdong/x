package cool.xxd.service.pay.domain.strategy.fuiou;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxPreCreateResponse extends BasePayResponse {
    @XmlElement(name = "sub_mer_id")
    private String subMerId;

    @XmlElement(name = "session_id")
    private String sessionId;

    @XmlElement(name = "qr_code")
    private String qrCode;

    @XmlElement(name = "sub_appid")
    private String subAppid;

    @XmlElement(name = "sub_openid")
    private String subOpenid;

    @XmlElement(name = "sdk_appid")
    private String sdkAppid;

    @XmlElement(name = "sdk_timestamp")
    private String sdkTimestamp;

    @XmlElement(name = "sdk_noncestr")
    private String sdkNoncestr;

    @XmlElement(name = "sdk_package")
    private String sdkPackage;

    @XmlElement(name = "sdk_signtype")
    private String sdkSigntype;

    @XmlElement(name = "sdk_paysign")
    private String sdkPaysign;

    @XmlElement(name = "sdk_partnerid")
    private String sdkPartnerid;

    @XmlElement(name = "reserved_fy_settle_dt")
    private String reservedFySettleDt;

    @XmlElement(name = "reserved_transaction_id")
    private String reservedTransactionId;

    @XmlElement(name = "reserved_pay_info")
    private String reservedPayInfo;

    @XmlElement(name = "reserved_addn_inf")
    private String reservedAddnInf;
}
