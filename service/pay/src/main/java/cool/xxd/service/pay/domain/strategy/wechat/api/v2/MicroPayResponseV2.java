package cool.xxd.service.pay.domain.strategy.wechat.api.v2;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "xml")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MicroPayResponseV2 {
    @XmlElement(name = "return_code")
    private String returnCode;

    @XmlElement(name = "return_msg")
    private String returnMsg;

    @XmlElement(name = "appid")
    private String appid;

    @XmlElement(name = "sub_appid")
    private String subAppid;

    @XmlElement(name = "mch_id")
    private String mchid;

    @XmlElement(name = "sub_mch_id")
    private String subMchId;

    @XmlElement(name = "device_info")
    private String deviceInfo;

    @NotNull
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    @NotNull
    @XmlElement(name = "sign")
    private String sign;

    @XmlElement(name = "result_code")
    private String resultCode;

    @XmlElement(name = "err_code")
    private String errCode;

    @XmlElement(name = "err_code_des")
    private String errCodeDes;

    @XmlElement(name = "trade_type")
    private String tradeType;

    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "time_end")
    private String timeEnd;
}
