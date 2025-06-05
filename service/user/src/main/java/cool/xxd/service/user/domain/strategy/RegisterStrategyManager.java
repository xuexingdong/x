package cool.xxd.service.user.domain.strategy;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.user.domain.enums.RegisterType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RegisterStrategyManager {

    private final Map<RegisterType, RegisterStrategy> strategies;

    public RegisterStrategyManager(List<RegisterStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        RegisterStrategy::getRegisterType,
                        Function.identity()
                ));
    }

    public RegisterStrategy getStrategy(RegisterType registerType) {
        RegisterStrategy strategy = strategies.get(registerType);
        if (strategy == null) {
            throw new BusinessException("不支持的注册类型: " + registerType);
        }
        return strategy;
    }
}