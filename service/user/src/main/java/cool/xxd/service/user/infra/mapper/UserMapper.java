package cool.xxd.service.user.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.xxd.service.user.model.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
