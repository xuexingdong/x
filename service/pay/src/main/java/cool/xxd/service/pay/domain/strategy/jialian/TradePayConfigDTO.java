package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

@Data
public class TradePayConfigDTO {
    private String payType;
    private String scanStatus;
    private String scanAuthPlatform;
    private String scanQrCode;
    private String payJsApiInfo;
    private String mchTransNo;
}
