package cool.xxd.service.user.infra.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.service.user.domain.aggregate.User;
import cool.xxd.service.user.domain.repository.UserRepository;
import cool.xxd.service.user.infra.converter.UserConverter;
import cool.xxd.service.user.infra.mapper.UserMapper;
import cool.xxd.service.user.model.UserDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void save(User user) {
        var userDO = UserConverter.INSTANCE.domain2do(user);
        userMapper.insert(userDO);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var queryWrapper = Wrappers.lambdaQuery(UserDO.class);
        queryWrapper.eq(UserDO::getUsername, username);
        var userDO = userMapper.selectOne(queryWrapper);
        return Optional.ofNullable(UserConverter.INSTANCE.do2domain(userDO));
    }
}
