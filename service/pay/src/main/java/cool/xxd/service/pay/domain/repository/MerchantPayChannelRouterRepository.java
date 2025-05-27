package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.repository.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannelRouter;

import java.util.List;
import java.util.Optional;

public interface MerchantPayChannelRouterRepository extends BaseRepository<MerchantPayChannelRouter, Long> {
    Optional<MerchantPayChannelRouter> findByMchidAndPayTypeCode(String mchid, String payTypeCode);

    List<MerchantPayChannelRouter> findByMchid(String mchid);
}

