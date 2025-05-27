package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

@Data
public class MerchantPayChannelRouter {
    private Long id;
    private String mchid;
    private String payTypeCode;
    private String payChannelCode;
}
