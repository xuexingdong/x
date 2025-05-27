package cool.xxd.service.pay.domain.strategy.wechat.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DownloadBillRequest {
    private String hashType;
    private String hashValue;
    private String downloadUrl;
}
