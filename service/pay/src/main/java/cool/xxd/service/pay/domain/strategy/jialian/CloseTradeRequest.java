package cool.xxd.service.pay.domain.strategy.jialian;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CloseTradeRequest {
    private String tradeNo;
    private String outTradeNo;
    private String closeRemark;
    @NotNull
    private String clientIp;
}
