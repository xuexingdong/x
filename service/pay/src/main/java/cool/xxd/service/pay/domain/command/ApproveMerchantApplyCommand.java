package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public class ApproveMerchantApplyCommand {
    private String merchantApplyNo;
    private Long operatorId;
}
