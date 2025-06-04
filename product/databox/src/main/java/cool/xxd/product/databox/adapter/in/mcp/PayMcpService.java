package cool.xxd.product.databox.adapter.in.mcp;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.product.databox.application.dto.request.LoginRequest;
import cool.xxd.product.databox.application.dto.request.RegisterRequest;
import cool.xxd.product.databox.application.dto.response.UserResponse;
import cool.xxd.service.user.application.service.UserService;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.service.UserDomainService;
import cool.xxd.service.user.domain.valueobject.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayMcpService {

    private final UserService userService;
    private final UserDomainService userDomainService;

    @Tool(description = "注册")
    public Long register(RegisterRequest registerRequest) {
        log.info("register request: {}", registerRequest);
        var registerCommand = new RegisterCommand();
        registerCommand.setUsername(registerRequest.getUsername());
        registerCommand.setPassword(registerRequest.getPassword());
        registerCommand.setConfirmPassword(registerRequest.getConfirmPassword());
        return userService.register(registerCommand);
    }

    @Tool(description = "登录")
    public Token login(LoginRequest loginRequest) {
        log.info("login request: {}", loginRequest);
        var loginCommand = new LoginCommand();
        loginCommand.setUsername(loginRequest.getUsername());
        loginCommand.setPassword(loginRequest.getPassword());
        return userService.login(loginCommand);
    }

    @Tool(description = "查看当前用户信息")
    public UserResponse currentUser(String token) {
        log.info(token);
        var userOptional = userDomainService.parseToken(token);
        if (userOptional.isEmpty()) {
            throw new BusinessException("用户未登录");
        }
        var username = userOptional.get();
        var user = userService.getByUsername(username);
        var userResponse = new UserResponse();
        userResponse.setUsername(username);
        userResponse.setUserStatus(user.getUserStatus().getCode());
        return userResponse;
    }
}
