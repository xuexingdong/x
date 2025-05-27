package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.TradeTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * retrofit2.converter.jaxb3.JaxbConverterFactory不支持自定义XmlJavaTypeAdapter，所以只能通过给对象设置默认值的方式来实现null的对象也写入xml节点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxPreCreateRequest extends BasePayRequest {

    @XmlElement(name = "product_id")
    private String productId = "";

    @NotNull
    @XmlElement(name = "notify_url")
    private String notifyUrl;

    @XmlElement(name = "limit_pay")
    private String limitPay = "";

    @NotNull
    @XmlElement(name = "trade_type")
    private TradeTypeEnum tradeType;

    @XmlElement(name = "openid")
    private String openid = "";

    @XmlElement(name = "sub_openid")
    private String subOpenid = "";

    @XmlElement(name = "sub_appid")
    private String subAppid = "";

    @XmlElement(name = "reserved_txn_bonus")
    private String reservedTxnBonus = "";

    @XmlElement(name = "reserved_user_truename")
    private String reservedUserTruename = "";

    @XmlElement(name = "reserved_user_creid")
    private String reservedUserCreid = "";

    @XmlElement(name = "reserved_scene_info")
    private String reservedSceneInfo = "";

    @XmlElement(name = "reserved_front_url")
    private String reservedFrontUrl = "";

    @XmlElement(name = "reserved_front_fail_url")
    private String reservedFrontFailUrl = "";

    public WxPreCreateRequest() {
        super();
    }

    public WxPreCreateRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}