package cool.xxd.service.pay.domain.strategy.fuiou;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.UUID;

/**
 * 不加XmlAccessorType会出现Class has two properties of the same name
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseRequest {

    @NotNull
    @XmlElement(name = "version")
    private String version;

    @NotNull
    @XmlElement(name = "ins_cd")
    private String insCd;

    @NotNull
    @XmlElement(name = "mchnt_cd")
    private String mchntCd;

    @NotNull
    @XmlElement(name = "term_id")
    private String termId;

    @NotNull
    @XmlElement(name = "random_str")
    private String randomStr;

    @NotNull
    @XmlElement(name = "sign")
    private String sign;

    public BaseRequest() {
        this.version = "1.0";
        this.randomStr = UUID.randomUUID().toString().replace("-", "");
    }

    public BaseRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        this.version = "1.0";
        this.insCd = insCd;
        this.mchntCd = fuiouPayChannelConfig.getMchnt_cd();
        this.termId = fuiouPayChannelConfig.getTerm_id();
        this.randomStr = UUID.randomUUID().toString().replace("-", "");
    }
}
