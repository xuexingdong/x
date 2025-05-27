package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;
    private Long expires;
    private String authorityId;
    private String refreshToken;
}
