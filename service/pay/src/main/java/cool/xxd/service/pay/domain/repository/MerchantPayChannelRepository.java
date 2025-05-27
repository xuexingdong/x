package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.repository.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;

import java.util.List;
import java.util.Optional;

public interface MerchantPayChannelRepository extends BaseRepository<MerchantPayChannel, Long> {
    List<MerchantPayChannel> findByMchid(String mchid);

    Optional<MerchantPayChannel> findByMchidAndPayChannelCode(String mchid, String payChannelCode);

    List<MerchantPayChannel> findAll();
}

