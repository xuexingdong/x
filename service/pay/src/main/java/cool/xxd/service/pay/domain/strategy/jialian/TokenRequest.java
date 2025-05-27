package cool.xxd.service.pay.domain.strategy.jialian;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequest {
    @NotNull
    private String appKey;
    private String appSecret;
    private String authorizeType;
    private String code;
    // private String refreshToken;
}
