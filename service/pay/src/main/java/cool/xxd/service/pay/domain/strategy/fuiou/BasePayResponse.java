package cool.xxd.service.pay.domain.strategy.fuiou;

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
public abstract class BasePayResponse extends BaseResponse {

    @XmlElement(name = "reserved_fy_order_no")
    private String reservedFyOrderNo;

    @XmlElement(name = "reserved_fy_trace_no")
    private String reservedFyTraceNo;

    @XmlElement(name = "reserved_channel_order_id")
    private String reservedChannelOrderId;

}
