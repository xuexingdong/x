package cool.xxd.service.user.domain.command;

import cool.xxd.service.user.domain.enums.LoginType;
import lombok.Data;

@Data
public class LoginCommand {
    private LoginType loginType;
    private String username;
    private String password;
}
