package cool.xxd.service.user.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.user.domain.aggregate.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
