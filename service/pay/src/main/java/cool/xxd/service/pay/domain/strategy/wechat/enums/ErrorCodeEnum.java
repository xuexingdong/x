package cool.xxd.service.pay.domain.strategy.wechat.enums;

public enum ErrorCodeEnum {
    TRADE_ERROR(403, "TRADE_ERROR", "交易错误", "因业务原因交易失败，请查看接口返回的详细信息"),
    SYSTEM_ERROR(500, "SYSTEM_ERROR", "系统错误", "系统异常，请用相同参数重新调用"),
    SIGN_ERROR(401, "SIGN_ERROR", "签名错误", "请检查签名参数和方法是否都符合签名算法要求"),
    RULE_LIMIT(403, "RULE_LIMIT", "业务规则限制", "因业务规则限制请求频率，请查看接口返回的详细信息"),
    PARAM_ERROR(400, "PARAM_ERROR", "参数错误", "请根据接口返回的详细信息检查请求参数"),
    OUT_TRADE_NO_USED(403, "OUT_TRADE_NO_USED", "商户订单号重复", "请核实商户订单号是否重复提交"),
    ORDER_NOT_EXIST(404, "ORDER_NOT_EXIST", "订单不存在", "请检查订单是否发起过交易"),
    ORDER_CLOSED(400, "ORDER_CLOSED", "订单已关闭", "当前订单已关闭，请重新下单"),
    OPENID_MISMATCH(500, "OPENID_MISMATCH", "openid和appid不匹配", "请确认openid和appid是否匹配"),
    NO_AUTH(403, "NO_AUTH", "商户无权限", "请商户前往申请此接口相关权限"),
    MCH_NOT_EXISTS(400, "MCH_NOT_EXISTS", "商户号不存在", "请检查商户号是否正确"),
    INVALID_TRANSACTIONID(500, "INVALID_TRANSACTIONID", "订单号非法", "请检查微信支付订单号是否正确"),
    INVALID_REQUEST(400, "INVALID_REQUEST", "无效请求", "请根据接口返回的详细信息检查"),
    FREQUENCY_LIMITED(429, "FREQUENCY_LIMITED", "频率超限", "请降低请求接口频率"),
    BANK_ERROR(500, "BANK_ERROR", "银行系统异常", "银行系统异常，请用相同参数重新调用"),
    APPID_MCHID_NOT_MATCH(400, "APPID_MCHID_NOT_MATCH", "appid和mch_id不匹配", "请确认appid和mch_id是否匹配"),
    ACCOUNT_ERROR(403, "ACCOUNT_ERROR", "账号异常", "用户账号异常，无需更多操作");

    private final int statusCode;
    private final String errorCode;
    private final String description;
    private final String solution;

    ErrorCodeEnum(int statusCode, String errorCode, String description, String solution) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.description = description;
        this.solution = solution;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

    public String getSolution() {
        return solution;
    }
}
