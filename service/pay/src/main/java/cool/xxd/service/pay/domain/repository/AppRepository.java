package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.repository.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.App;

import java.util.List;

public interface AppRepository extends BaseRepository<App, Long> {

    App getByAppid(String appid);

    List<App> findByAppids(List<String> appids);
}
