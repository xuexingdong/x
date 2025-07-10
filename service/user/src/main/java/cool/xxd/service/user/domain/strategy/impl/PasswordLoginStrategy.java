package cool.xxd.service.user.domain.strategy.impl;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.enums.LoginType;
import cool.xxd.service.user.domain.repository.UserRepository;
import cool.xxd.service.user.domain.strategy.LoginStrategy;
import cool.xxd.service.user.domain.valueobject.LoginResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 用户名密码注册策略
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordLoginStrategy implements LoginStrategy {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginType getLoginType() {
        return LoginType.USERNAME;
    }

    @Override
    public void validateLoginCommand(LoginCommand loginCommand) {

    }

    @Override
    public User executeLogin(LoginCommand loginCommand) {
        var user = userRepository.findByUsername(loginCommand.getUsername())
                .orElseThrow(() -> new BusinessException("用户不存在"));
        if (!passwordEncoder.matches(loginCommand.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return user;
    }

    @Override
    public void postLogin(LoginResult loginResult, LoginCommand loginCommand) {

    }
} 