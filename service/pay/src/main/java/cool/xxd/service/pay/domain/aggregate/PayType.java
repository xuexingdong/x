package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

import java.time.Duration;
import java.time.Period;

@Data
public class PayType {
    private String code;
    private String name;
    // 是否是离线支付方式
    private Boolean offline;
    // 是否支持退款
    private Boolean supportRefund;
    // 是否支持离线退款
    private Boolean supportOfflineRefund;
    // 是否支持部分退款
    private Boolean supportPartialRefund;
    // 退款有效期
    private Period refundValidPeriod;
    // 是否支持撤销
    private Boolean supportCancel;
    // 撤销有效期
    private Duration cancelValidDuration;
}
