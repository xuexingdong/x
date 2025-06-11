package cool.xxd.service.user.domain.strategy;

import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.command.RegisterCommand;
import cool.xxd.service.user.domain.enums.RegisterType;

// 注册策略接口
public interface RegisterStrategy {

    /**
     * 获取注册类型
     */
    RegisterType getRegisterType();

    /**
     * 验证注册参数
     */
    void validateRegisterCommand(RegisterCommand registerCommand);

    String getLockKey(RegisterCommand registerCommand);

    /**
     * 检查唯一性（用户名/邮箱/手机号是否已存在）
     */
    void checkUniqueness(RegisterCommand registerCommand);

    /**
     * 执行注册逻辑
     */
    Long executeRegister(RegisterCommand registerCommand);

    /**
     * 注册后处理（发送验证码/邮件等）
     */
    void postRegister(User user, RegisterCommand registerCommand);

}