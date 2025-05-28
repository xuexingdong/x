package cool.xxd.product.databox.adapter.in.web;

import cool.xxd.infra.http.Response;
import cool.xxd.product.databox.application.dto.request.LoginRequest;
import cool.xxd.product.databox.application.dto.request.RegisterRequest;
import cool.xxd.product.databox.application.dto.response.LoginResponse;
import cool.xxd.product.databox.application.dto.response.RegisterResponse;
import cool.xxd.product.databox.application.dto.response.UserResponse;
import cool.xxd.service.user.application.service.UserService;
import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.command.RegisterCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "用户接口")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Response<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        var registerCommand = new RegisterCommand();
        registerCommand.setUsername(registerRequest.getUsername());
        registerCommand.setPassword(registerRequest.getPassword());
        registerCommand.setConfirmPassword(registerRequest.getConfirmPassword());
        var userId = userService.register(registerCommand);

        var registerResponse = new RegisterResponse();
        registerResponse.setUserId(userId);
        return Response.data(registerResponse);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var loginCommand = new LoginCommand();
        loginCommand.setUsername(loginRequest.getUsername());
        loginCommand.setPassword(loginRequest.getPassword());
        var token = userService.login(loginCommand);
        var loginResponse = new LoginResponse();
        loginResponse.setToken(token.token());
        return Response.data(loginResponse);
    }

    @Operation(summary = "查看当前用户信息")
    @PostMapping("/currentUser")
    public Response<UserResponse> currentUser(@AuthenticationPrincipal String username) {
        var user = userService.getByUsername(username);
        var userResponse = new UserResponse();
        userResponse.setUsername(username);
        userResponse.setUserStatus(user.getUserStatus().getCode());
        return Response.data(userResponse);
    }
}
