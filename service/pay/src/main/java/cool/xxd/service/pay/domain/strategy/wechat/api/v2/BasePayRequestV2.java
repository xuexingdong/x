package cool.xxd.service.pay.domain.strategy.wechat.api.v2;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BasePayRequestV2 {
    @NotNull
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    @NotNull
    @XmlElement(name = "sign")
    private String sign;
}
