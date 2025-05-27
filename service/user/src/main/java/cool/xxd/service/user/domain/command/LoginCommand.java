package cool.xxd.service.user.domain.command;

import lombok.Data;

@Data
public class LoginCommand {
    private String username;
    private String password;
}
