package cool.xxd.service.user.domain.factory;

import cool.xxd.infra.X;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.enums.UserStatus;
import cool.xxd.service.user.model.UserDO;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User create(String username, String encodedPassword) {
        User user = new User();
        user.setId(X.id.nextId(UserDO.class));
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setUserStatus(UserStatus.ACTIVE);
        return user;
    }
}
