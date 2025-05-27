package cool.xxd.service.pay.domain.command;

import cool.xxd.service.pay.domain.enums.TransModeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class PayCommand extends BaseCommand {
    // 商户订单号
    private String outTradeNo;
    // 订单总金额，单位为元，两位小数
    private BigDecimal totalAmount;
    // 支付方式编码
    private String payTypeCode;
    private TransModeEnum transMode;
    private String authCode;
    // 支付的小程序的appid，对应支付宝op_app_id，微信支付sub_appid
    private String subAppid;
    // 用户子标识，对应支付宝buyer_open_id，微信支付若sub_openid有传的情况下，sub_appid必填，且sub_appid需与sub_openid对应
    private String subOpenid;
    // 超时分钟数，默认15
    private Integer expireMinutes;
    // 订单标题，对应支付宝subject
    private String subject;
    // 订单描述，对应支付宝body，微信支付description，富友goods_des
    private String description;
    private String goodsDetail;

    // 自定义数据，对应支付宝passback_params，微信支付attach，富友addn_inf
    private String customData;
}
