package cool.xxd.service.user.domain.service;

import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.valueobject.Token;

import java.util.Optional;

public interface UserDomainService {
    Long register(RegisterCommand registerCommand);

    Token login(LoginCommand loginCommand);

    Optional<String> parseToken(String token);
}
