package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

@Data
public class QueryTradeRequest {
    private String tradeNo;
    private String outTradeNo;
    private String payType;
}
