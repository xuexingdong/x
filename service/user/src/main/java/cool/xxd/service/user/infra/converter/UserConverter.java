package cool.xxd.service.user.infra.converter;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.model.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CommonEnumMapper.class})
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDO domain2do(User user);

    User do2domain(UserDO userDO);
}
