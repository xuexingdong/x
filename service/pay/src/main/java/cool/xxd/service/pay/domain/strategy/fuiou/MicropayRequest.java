package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.strategy.fuiou.enums.OrderTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MicropayRequest extends BasePayRequest {
    @NotNull
    @XmlElement(name = "order_type")
    private OrderTypeEnum orderType;

    @NotNull
    @XmlElement(name = "auth_code")
    private String authCode;

    /**
     * 1: 条码支付
     * 2: 声波支付
     * 3: 刷脸支付
     */
    @XmlElement(name = "scene")
    private String scene = "";

    @XmlElement(name = "reserved_sub_appid")
    private String reservedSubAppid = "";

    @XmlElement(name = "reserved_limit_pay")
    private String reservedLimitPay = "";

    @XmlElement(name = "reserved_scene_info")
    private String reservedSceneInfo = "";

    public MicropayRequest() {
        super();
    }

    public MicropayRequest(String insCd, FuiouPayChannelConfig fuiouPayChannelConfig) {
        super(insCd, fuiouPayChannelConfig);
    }
}
