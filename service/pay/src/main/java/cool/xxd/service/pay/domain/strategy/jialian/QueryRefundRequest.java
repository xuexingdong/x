package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

@Data
public class QueryRefundRequest {
    private String tradeNo;
    private String outTradeNo;
    private String refundNo;
    private String outRefundNo;
}
