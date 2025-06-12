package cool.xxd.service.user.domain.strategy;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.user.domain.enums.LoginType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LoginStrategyManager {

    private final Map<LoginType, LoginStrategy> strategies;

    public LoginStrategyManager(List<LoginStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        LoginStrategy::getLoginType,
                        Function.identity()
                ));
    }

    public LoginStrategy getStrategy(LoginType loginType) {
        LoginStrategy strategy = strategies.get(loginType);
        if (strategy == null) {
            throw new BusinessException("不支持的登录类型: " + loginType);
        }
        return strategy;
    }
}