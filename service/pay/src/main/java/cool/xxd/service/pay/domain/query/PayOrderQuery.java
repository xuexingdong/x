package cool.xxd.service.pay.domain.query;

import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PayOrderQuery {
    // 商户号 必填
    private String mchid;
    private String appid;
    private List<PayStatusEnum> payStatusList;
    private LocalDateTime fromTimeExpire;
    private LocalDateTime toTimeExpire;
    private LocalDateTime fromPollingStartTime;
    private LocalDateTime toPollingStartTime;
}
