package com.xxd.x.auth.controller;

import com.xxd.x.auth.dto.PasswordLoginDTO;
import com.xxd.x.auth.service.UserService;
import com.xxd.x.jwt.JWTService;
import com.xxd.x.auth.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("password_login")
    public Token loginByPassword(@Validated @RequestBody PasswordLoginDTO passwordLoginDTO) {
        return userService.passwordLogin(passwordLoginDTO);
    }

    @PostMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        String userId = jwtService.getCurrentUserId();
        userService.logout(userId);
    }
}


