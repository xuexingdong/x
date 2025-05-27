package cool.xxd.service.pay.domain.query;

import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefundOrderQuery {
    private String appid;
    private RefundStatusEnum refundStatus;
    private LocalDateTime fromPollingStartTime;
    private LocalDateTime toPollingStartTime;
}
