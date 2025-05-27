package cool.xxd.service.user.domain.aggregate;

import cool.xxd.service.user.domain.enums.UserStatus;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private UserStatus userStatus;
}
