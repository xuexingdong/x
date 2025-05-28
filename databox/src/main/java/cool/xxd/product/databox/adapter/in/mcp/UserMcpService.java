package cool.xxd.product.databox.adapter.in.mcp;

import cool.xxd.product.databox.application.dto.request.LoginRequest;
import cool.xxd.product.databox.application.dto.request.RegisterRequest;
import cool.xxd.service.user.application.service.UserService;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.valueobject.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMcpService {

    private final UserService userService;

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
}
