package cool.xxd.service.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
public class UserDO extends XBaseDO {
    private String username;
    private String password;
    private String userStatus;
}
