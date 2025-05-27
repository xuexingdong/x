package cool.xxd.service.pay.domain.strategy.wechat.api.response;

import lombok.Data;

@Data
public class GetBillDownloadUrlResponse {
    private String hashType;
    private String hashValue;
    private String downloadUrl;
}
