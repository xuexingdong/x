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
public class CancelOrderResponse extends BaseResponse {

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @NotNull
    @XmlElement(name = "cancel_order_no")
    private String cancelOrderNo;

    @NotNull
    @XmlElement(name = "transaction_id")
    private String transactionId;

    @NotNull
    @XmlElement(name = "cancel_id")
    private String cancelId;

    @XmlElement(name = "fund_change")
    private String fundChange;

    @XmlElement(name = "recall")
    private String recall;

    @NotNull
    @XmlElement(name = "reserved_fy_settle_dt")
    private String reservedFySettleDt;

    @XmlElement(name = "reserved_fy_trace_no")
    private String reservedFyTraceNo;

}
