package cool.xxd.service.user.domain.strategy;

import cool.xxd.service.user.domain.command.LoginCommand;
import cool.xxd.service.user.domain.enums.LoginType;
import cool.xxd.service.user.domain.valueobject.LoginResult;

public interface LoginStrategy {

    /**
     * 获取登录类型
     */
    LoginType getLoginType();

    /**
     * 验证登录参数
     */
    void validateLoginCommand(LoginCommand loginCommand);

    /**
     * 执行登录逻辑
     */
    LoginResult executeLogin(LoginCommand loginCommand);

    /**
     * 登录后处理（记录日志、更新登录时间等）
     */
    void postLogin(LoginResult loginResult, LoginCommand loginCommand);
}