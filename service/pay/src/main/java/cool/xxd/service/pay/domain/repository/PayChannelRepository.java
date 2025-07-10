package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.PayChannel;

import java.util.List;
import java.util.Optional;

public interface PayChannelRepository extends BaseRepository<PayChannel, Long> {

    Optional<PayChannel> findByCode(String payC);

    List<App> findByAppidList(List<String> appIdList);
}
