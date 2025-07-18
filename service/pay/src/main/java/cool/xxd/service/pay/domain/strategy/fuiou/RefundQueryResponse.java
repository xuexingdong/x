package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
import cool.xxd.service.pay.domain.strategy.fuiou.enums.TransStatEnum;
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
public class RefundQueryResponse extends BaseResponse {

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "trans_stat")
    private TransStatEnum transStat;

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @NotNull
    @XmlElement(name = "refund_order_no")
    private String refundOrderNo;

    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "refund_id")
    private String refundId;

    @NotNull
    @XmlElement(name = "reserved_fy_settle_dt")
    private String reservedFySettleDt;

    @NotNull
    @XmlElement(name = "reserved_fy_trace_no")
    private String reservedFyTraceNo;

    @XmlElement(name = "reserved_refund_amt")
    private String reservedRefundAmt;

    @XmlElement(name = "reserved_promotion_detail")
    private String reservedPromotionDetail;

}
