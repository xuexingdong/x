package cool.xxd.service.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("x_user")
public class UserDO extends BaseDO {
    private String username;
    private String password;
    private Integer userStatus;
}
