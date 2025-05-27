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
public class CloseOrderRequest extends BaseRequest {

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @XmlElement(name = "sub_appid")
    private String subAppid = "";

    public CloseOrderRequest() {
        super();
    }

    public CloseOrderRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
