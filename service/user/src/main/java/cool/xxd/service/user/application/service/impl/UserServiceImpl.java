package cool.xxd.service.user.application.service.impl;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.user.application.service.UserService;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.repository.UserRepository;
import cool.xxd.service.user.domain.service.UserDomainService;
import cool.xxd.service.user.domain.valueobject.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDomainService userDomainService;

    private final UserRepository userRepository;

    @Override
    public Long register(RegisterCommand registerCommand) {
        return userDomainService.register(registerCommand);
    }

    @Override
    public Token login(LoginCommand loginCommand) {
        return userDomainService.login(loginCommand);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("用户不存在"));
    }
}
