package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

@Data
public class MerchantPayChannel {
    private Long id;
    private String mchid;
    // 订单号前缀-只有富友使用
    private String payChannelCode;
    private String orderNoPrefix;
    private String config;

    public void update(String payChannelCode, String config) {
        this.payChannelCode = payChannelCode;
        this.config = config;
    }

    public void update(String config) {
        this.config = config;
    }
}
