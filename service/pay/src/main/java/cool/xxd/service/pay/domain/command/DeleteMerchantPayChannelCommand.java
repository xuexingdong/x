package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public class DeleteMerchantPayChannelCommand {
    private String mchid;
    private String payChannelCode;
}
