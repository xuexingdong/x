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
public class CommonRefundRequest extends BaseRequest {

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "refund_order_no")
    private String refundOrderNo;

    @NotNull
    @XmlElement(name = "total_amt")
    private Integer totalAmt;

    @NotNull
    @XmlElement(name = "refund_amt")
    private Integer refundAmt;

    @XmlElement(name = "operator_id")
    private String operatorId = "";

    @XmlElement(name = "reserved_fy_term_id")
    private String reservedFyTermId = "";

    /**
     * 原交易日期(yyyyMMdd)！该值必定等于reserved_fy_settle_dt(富友接收交易时间。理论和合作方下单时间一致。微量跨日交易会不一致)。
     * 不填该值，支持30天内的交易进行退款。
     * 填写该值，支持360天内的退款。
     */
    @XmlElement(name = "reserved_origi_dt")
    private String reservedOrigiDt = "";

    @XmlElement(name = "reserved_addn_inf")
    private String reservedAddnInf = "";

    public CommonRefundRequest() {
        super();
    }

    public CommonRefundRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
