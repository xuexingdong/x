package cool.xxd.service.pay.domain.strategy.wechat.api.v2;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PayRequestV2 extends BasePayRequestV2 {

    @NotNull
    @XmlElement(name = "appid")
    private String appid;

    @XmlElement(name = "sub_appid")
    private String subAppid;

    @NotNull
    @XmlElement(name = "mch_id")
    private String mchId;

    @NotNull
    @XmlElement(name = "sub_mch_id")
    private String subMchId;

    @XmlElement(name = "device_info")
    private String deviceInfo;

    @NotNull
    @XmlElement(name = "body")
    private String body;

    @XmlElement(name = "detail")
    private Detail detail;

    @XmlElement(name = "attach")
    private String attach;

    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    @NotNull
    @XmlElement(name = "total_fee")
    private Integer totalFee;

    @XmlElement(name = "fee_type")
    private String feeType;

    @XmlElement(name = "spbill_create_ip")
    private String spbillCreateIp;

    @XmlElement(name = "goods_tag")
    private String goodsTag;

    @XmlElement(name = "limit_pay")
    private String limitPay;

    @XmlElement(name = "time_start")
    private String timeStart;

    @XmlElement(name = "time_expire")
    private String timeExpire;

    @NotNull
    @XmlElement(name = "auth_code")
    private String authCode;

    @XmlElement(name = "receipt")
    private String receipt;

    @XmlElement(name = "profit_sharing")
    private String profitSharing;

    @XmlElement(name = "scene_info")
    private SceneInfo sceneInfo;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Detail {

        @XmlElement(name = "cost_price")
        private Integer costPrice;

        @XmlElement(name = "invoice_id")
        private String invoiceId;

        @XmlElement(name = "goods_detail")
        private List<GoodsDetail> goodsDetail;

        @Data
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class GoodsDetail {

            @XmlElement(name = "goods_id")
            private String goodsId;

            @XmlElement(name = "wxpay_goods_id")
            private String wxpayGoodsId;

            @XmlElement(name = "goods_name")
            private String goodsName;

            @XmlElement(name = "quantity")
            private Integer quantity;

            @XmlElement(name = "price")
            private Integer price;
        }
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SceneInfo {

        @XmlElement(name = "id")
        private String id;

        @XmlElement(name = "name")
        private String name;

        @XmlElement(name = "area_code")
        private String areaCode;

        @XmlElement(name = "address")
        private String address;
    }
}
