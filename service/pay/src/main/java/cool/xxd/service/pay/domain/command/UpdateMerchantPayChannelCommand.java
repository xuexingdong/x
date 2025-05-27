package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public class UpdateMerchantPayChannelCommand {
    private String mchid;
    private String payChannelCode;
    private String config;
}
