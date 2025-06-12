package cool.xxd.service.user.domain.service.impl;

import cool.xxd.infra.X;
import cool.xxd.infra.lock.LockTemplate;
import cool.xxd.service.user.application.constants.CacheKeys;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.repository.UserRepository;
import cool.xxd.service.user.domain.service.UserDomainService;
import cool.xxd.service.user.domain.strategy.LoginStrategyManager;
import cool.xxd.service.user.domain.strategy.RegisterStrategyManager;
import cool.xxd.service.user.domain.valueobject.LoginResult;
import cool.xxd.service.user.domain.valueobject.Token;
import cool.xxd.service.user.infra.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final RegisterStrategyManager registerStrategyManager;
    private final LoginStrategyManager loginStrategyManager;
    private final LockTemplate lockTemplate;
    private final JwtConfig jwtConfig;

    @Override
    public Long register(RegisterCommand registerCommand) {
        var strategy = registerStrategyManager.getStrategy(registerCommand.getRegisterType());
        strategy.validateRegisterCommand(registerCommand);
        var lockKey = strategy.getLockKey(registerCommand);
        return lockTemplate.execute(lockKey, () -> {
            strategy.checkUniqueness(registerCommand);
            return strategy.executeRegister(registerCommand);
        });
    }

    @Override
    public Token login(LoginCommand loginCommand) {
        var strategy = loginStrategyManager.getStrategy(loginCommand.getLoginType());
        var user = strategy.executeLogin(loginCommand);
        var token = generateToken(user);
        X.cache.save(CacheKeys.USER + user.getUsername(), user, jwtConfig.getExpiration());
        return new Token(token);
    }

    @Override
    public Optional<String> parseToken(String token) {
        byte[] encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(encodedKey);
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("当前用户登录过期:{}", token, e);
            return Optional.empty();
        }
        var username = claims.getSubject();
        return X.cache.load(CacheKeys.USER + username, User.class)
                .map(User::getUsername);
    }

    private String generateToken(User user) {
        var encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        var key = Keys.hmacShaKeyFor(encodedKey);
        return Jwts.builder()
                .subject(user.getUsername())
                .signWith(key)
                .compact();
    }
}
