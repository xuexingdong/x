package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public class AddMerchantPayChannelRouterCommand {
    private String mchid;
    private String payTypeCode;
    private String payChannelCode;
}
