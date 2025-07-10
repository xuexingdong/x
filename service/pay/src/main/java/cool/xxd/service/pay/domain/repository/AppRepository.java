package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.App;

import java.util.List;
import java.util.Optional;

public interface AppRepository extends BaseRepository<App, Long> {

    Optional<App> findByAppid(String mchid, String appid);

    List<App> findByAppidList(List<String> appIdList);
}
