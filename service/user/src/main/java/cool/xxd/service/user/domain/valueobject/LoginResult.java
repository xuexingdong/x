package cool.xxd.service.user.domain.valueobject;

import cool.xxd.service.user.domain.aggregate.User;
import lombok.Data;

@Data
public class LoginResult {
    private User user;
}
