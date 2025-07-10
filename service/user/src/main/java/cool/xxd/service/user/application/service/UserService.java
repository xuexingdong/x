package cool.xxd.service.user.application.service;

import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.valueobject.Token;

public interface UserService {

    Long register(RegisterCommand registerCommand);

    Token login(LoginCommand loginCommand);

    User getByUsername(String username);
}
