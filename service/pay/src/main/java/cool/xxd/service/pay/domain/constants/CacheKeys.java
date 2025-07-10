package cool.xxd.service.pay.domain.constants;

public final class CacheKeys {

    public static final String PREFIX = "x:pay:";
    public static final String MERCHANT_APPLY_SERIAL_NO = PREFIX + "merchant_apply:serial_no";
    public static final String ORDER_NO = PREFIX + "order_no:";
    public static final String REFUND_ORDER_NO = PREFIX + "refund_order_no:";
    public static final String JIALIAN_ACCESS_TOKEN = PREFIX + "jialian:access_token";
    public static final String MERCHANT_ADD_MERCHANT_PAY_CHANNEL = PREFIX + "merchant:%s:add_merchant_pay_channel:lock";
    public static final String MERCHANT_ADD_MERCHANT_PAY_CHANNEL_ROUTER = PREFIX + "merchant:%s:add_merchant_pay_channel_router:lock";
    public static final String MERCHANT_PAY_LOCK = PREFIX + "mchid:%s:pay:out_trade_no:%s:lock";
    public static final String PAY_ORDER_UPDATE_LOCK = PREFIX + "pay_order:%s:update:lock";
    public static final String REFUND_ORDER_UPDATE_LOCK = PREFIX + "refund_order:%s:update:lock";

    private CacheKeys() {

    }
}
