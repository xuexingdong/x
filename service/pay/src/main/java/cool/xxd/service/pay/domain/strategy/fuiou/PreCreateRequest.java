package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PreCreateRequest extends BasePayRequest {
    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "notify_url")
    private String notifyUrl;

    @XmlElement(name = "reserved_sub_appid")
    private String reservedSubAppid = "";

    @XmlElement(name = "reserved_limit_pay")
    private String reservedLimitPay = "";

    public PreCreateRequest() {
        super();
    }

    public PreCreateRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
