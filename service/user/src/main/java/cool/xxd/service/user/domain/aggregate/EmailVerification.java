package cool.xxd.service.user.domain.aggregate;

import lombok.Data;

@Data
public class EmailVerification {
    private Long id;
    private Long userId;
    private String email;
}
