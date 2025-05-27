package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TradeCreateResult {
    private String tradeNo;
    private String outTradeNo;
    private Integer payStatus;
    private LocalDateTime orderTime;
    private TradePayConfigDTO payConfig;
    private Integer payAmount;
    private Integer actualPayAmount;
    private Integer discountAmount;
}
