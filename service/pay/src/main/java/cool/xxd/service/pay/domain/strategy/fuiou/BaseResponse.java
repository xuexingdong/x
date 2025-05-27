package cool.xxd.service.pay.domain.strategy.fuiou;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseResponse {
    @NotNull
    @XmlElement(name = "result_code")
    private String resultCode;

    @NotNull
    @XmlElement(name = "result_code")
    private String resultMsg;

    @NotNull
    @XmlElement(name = "ins_cd")
    private String insCd;

    @NotNull
    @XmlElement(name = "mchnt_cd")
    private String mchntCd;

    @XmlElement(name = "term_id")
    private String termId;

    @NotNull
    @XmlElement(name = "random_str")
    private String randomStr;

    @NotNull
    private String sign;
}
