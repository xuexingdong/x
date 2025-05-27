package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
import jakarta.validation.constraints.NotNull;
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
public class CloseOrderResponse extends BaseResponse {

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;


}
