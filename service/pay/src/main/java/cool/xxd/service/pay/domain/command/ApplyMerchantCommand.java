package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public class ApplyMerchantCommand {
    private String merchantName;
    private String applyData;
    private Long userId;
}
