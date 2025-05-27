package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

@Data
public class MerchantPayChannel {
    private Long id;
    private String mchid;
    private String payChannelCode;
    private String config;

    public void update(String payChannelCode, String config) {
        this.payChannelCode = payChannelCode;
        this.config = config;
    }

    public void update(String config) {
        this.config = config;
    }
}
