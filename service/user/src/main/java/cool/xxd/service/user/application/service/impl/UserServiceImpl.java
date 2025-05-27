package cool.xxd.service.user.application.service.impl;

import cool.xxd.infra.X;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.infra.lock.LockTemplate;
import cool.xxd.service.user.application.constants.CacheKeys;
import cool.xxd.service.user.application.service.UserService;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.factory.UserFactory;
import cool.xxd.service.user.domain.repository.UserRepository;
import cool.xxd.service.user.domain.valueobject.Token;
import cool.xxd.service.user.infra.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LockTemplate lockTemplate;
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Override
    public Long register(RegisterCommand registerCommand) {
        validateConfirmPassword(registerCommand);
        var lockKey = CacheKeys.ADD_USER + registerCommand.getUsername();
        return lockTemplate.execute(lockKey, () -> {
            userRepository.findByUsername(registerCommand.getUsername())
                    .ifPresent(_ -> {
                        throw new BusinessException("用户名已存在");
                    });
            var user = userFactory.create(registerCommand.getUsername(), registerCommand.getPassword());
            userRepository.save(user);
            return user.getId();
        });
    }

    @Override
    public Token login(LoginCommand loginCommand) {
        var user = userRepository.findByUsername(loginCommand.getUsername())
                .orElseThrow(() -> new BusinessException("用户不存在"));
        if (!passwordEncoder.matches(loginCommand.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return generateToken(user);
    }

    private void validateConfirmPassword(RegisterCommand registerCommand) {
        if (!registerCommand.getPassword().equals(registerCommand.getConfirmPassword())) {
            throw new BusinessException("密码和确认密码不匹配");
        }
    }

    private Token generateToken(User user) {
        var encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        var key = Keys.hmacShaKeyFor(encodedKey);
        var jws = Jwts.builder()
                .content(String.valueOf(user.getId()))
                .signWith(key)
                .compact();
        var token = new Token(jws);
        X.cache.save(CacheKeys.USER + user.getId(), user, jwtConfig.getExpiration());
        return token;
    }
}
