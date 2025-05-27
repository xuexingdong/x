package cool.xxd.service.pay.domain.strategy.alipay.api.model.response;

import lombok.Data;

@Data
public class AlipayErrorResponse {
    private String code;
    private String message;
}
