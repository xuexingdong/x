package cool.xxd.service.pay.domain.strategy.jialian;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRefundRequest {
    private String tradeNo;
    private String outTradeNo;
    @NotNull
    private String outRefundNo;
    @NotNull
    private String clientIp;
    @NotNull
    private Integer refundAmount;
    @NotNull
    private String refundRemark;
    private String outNotifyUrl;
}
