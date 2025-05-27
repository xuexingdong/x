package cool.xxd.service.pay.domain.valueobject;

import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefundResult {
    private String thirdTradeNo;
    private RefundStatusEnum refundStatus;
    private LocalDateTime refundTime;
}
