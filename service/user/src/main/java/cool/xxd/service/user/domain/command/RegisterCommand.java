package cool.xxd.service.user.domain.command;

import cool.xxd.service.user.domain.enums.RegisterType;
import lombok.Data;

@Data
public class RegisterCommand {
    private RegisterType registerType;
    private String username;
    private String password;
    private String confirmPassword;
}
