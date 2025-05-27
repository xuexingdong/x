package cool.xxd.service.pay.domain.strategy.fuiou;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundQueryRequest extends BaseRequest {
    @NotNull
    @XmlElement(name = "refund_order_no")
    private String refundOrderNo;

    public RefundQueryRequest() {
        super();
    }

    public RefundQueryRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
