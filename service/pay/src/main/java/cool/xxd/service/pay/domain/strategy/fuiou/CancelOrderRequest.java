package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
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
public class CancelOrderRequest extends BaseRequest {
    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @XmlElement(name = "cancel_order_no")
    private String cancelOrderNo = "";

    @XmlElement(name = "operator_id")
    private String operatorId = "";

    @XmlElement(name = "reserved_fy_term_id")
    private String reservedFyTermId = "";

    public CancelOrderRequest() {
        super();
    }

    public CancelOrderRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
