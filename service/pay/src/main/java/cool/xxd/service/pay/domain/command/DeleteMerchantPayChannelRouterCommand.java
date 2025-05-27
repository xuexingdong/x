package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public class DeleteMerchantPayChannelRouterCommand {
    private String mchid;
    private String payTypeCode;
}
