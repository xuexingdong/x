package cool.xxd.service.user.domain.strategy.impl;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.enums.RegisterType;
import cool.xxd.service.user.domain.factory.UserFactory;
import cool.xxd.service.user.domain.repository.UserRepository;
import cool.xxd.service.user.domain.strategy.RegisterStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 用户名密码注册策略
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordRegisterStrategy implements RegisterStrategy {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,50}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,255}$");

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterType getRegisterType() {
        return RegisterType.USERNAME;
    }

    @Override
    public void validateRegisterCommand(RegisterCommand registerCommand) {
        // 验证用户名
        if (!StringUtils.hasText(registerCommand.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        if (!USERNAME_PATTERN.matcher(registerCommand.getUsername()).matches()) {
            throw new BusinessException("用户名只能包含字母、数字和下划线，长度为3-50个字符");
        }

        // 验证密码
        if (!StringUtils.hasText(registerCommand.getPassword())) {
            throw new BusinessException("密码不能为空");
        }
        if (!PASSWORD_PATTERN.matcher(registerCommand.getPassword()).matches()) {
            throw new BusinessException("密码长度必须为6-255个字符");
        }

        // 验证确认密码
        if (!StringUtils.hasText(registerCommand.getConfirmPassword())) {
            throw new BusinessException("确认密码不能为空");
        }
        if (!registerCommand.getPassword().equals(registerCommand.getConfirmPassword())) {
            throw new BusinessException("密码和确认密码不匹配");
        }
    }

    @Override
    public void checkUniqueness(RegisterCommand registerCommand) {
        // 检查用户名是否已存在
        userRepository.findByUsername(registerCommand.getUsername())
                .ifPresent(user -> {
                    throw new BusinessException("用户名已存在");
                });
    }

    @Override
    public Long executeRegister(RegisterCommand registerCommand) {
        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(registerCommand.getPassword());
        
        // 创建用户实体
        User user = userFactory.create(registerCommand.getUsername(), encodedPassword);
        
        // 保存用户
        userRepository.save(user);
        
        log.info("用户注册成功: username={}, userId={}", registerCommand.getUsername(), user.getId());
        
        return user.getId();
    }

    @Override
    public void postRegister(User user, RegisterCommand registerCommand) {
        // 用户名密码注册后处理
        // 这里可以添加如下逻辑：
        // 1. 发送欢迎邮件
        // 2. 记录注册日志
        // 3. 初始化用户默认设置
        // 目前暂不需要特殊处理
        log.info("用户注册后处理完成: username={}, userId={}", user.getUsername(), user.getId());
    }
} 