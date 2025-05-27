package cool.xxd.service.pay.domain.valueobject;

import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayResult {
    private PayStatusEnum payStatus;
    private LocalDateTime payTime;
    private String thirdTradeNo;
    private String qrCode;
    private String clientPayInvokeParams;
}
