package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.FyTermTypeEnum;
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
public abstract class BasePayRequest extends BaseRequest {

    @NotNull
    @XmlElement(name = "goods_des")
    private String goodsDes;

    @XmlElement(name = "goods_detail")
    private String goodsDetail = "";

    @XmlElement(name = "goods_tag")
    private String goodsTag = "";

    @XmlElement(name = "addn_inf")
    private String addnInf = "";

    @NotNull
    @XmlElement(name = "mchnt_order_no")
    private String mchntOrderNo;

    @XmlElement(name = "curr_type")
    private String currType = "";

    @NotNull
    @XmlElement(name = "order_amt")
    private Integer orderAmt;

    @NotNull
    @XmlElement(name = "term_ip")
    private String termIp;

    @NotNull
    @XmlElement(name = "txn_begin_ts")
    private String txnBeginTs;

    @XmlElement(name = "reserved_expire_minute")
    private String reservedExpireMinute = "";

    @XmlElement(name = "reserved_fy_term_id")
    private String reservedFyTermId = "";

    @XmlElement(name = "reserved_fy_term_type")
    private FyTermTypeEnum reservedFyTermType;

    @XmlElement(name = "reserved_fy_term_sn")
    private String reservedFyTermSn = "";

    @XmlElement(name = "reserved_device_info")
    private String reservedDeviceInfo = "";

    @XmlElement(name = "reserved_ali_extend_params")
    private String reservedAliExtendParams = "";

    @XmlElement(name = "reserved_store_code")
    private String reservedStoreCode = "";

    @XmlElement(name = "reserved_alipay_store_id")
    private String reservedAlipayStoreId = "";

    @NotNull
    @XmlElement(name = "reserved_terminal_info")
    private String reservedTerminalInfo;

    @XmlElement(name = "reserved_business_params")
    private String reservedBusinessParams = "";

    public BasePayRequest() {
        super();
    }

    public BasePayRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
