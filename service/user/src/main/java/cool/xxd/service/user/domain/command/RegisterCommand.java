package cool.xxd.service.user.domain.command;

import lombok.Data;

@Data
public class RegisterCommand {
    private String username;
    private String password;
    private String confirmPassword;
}
