package cool.xxd.service.user.infra.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.service.user.model.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends XBaseMapper<UserDO> {
}
