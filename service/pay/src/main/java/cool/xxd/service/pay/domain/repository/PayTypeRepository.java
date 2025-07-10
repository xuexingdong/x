package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.PayType;

public interface PayTypeRepository extends BaseRepository<PayType, Long> {
}
