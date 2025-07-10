package cool.xxd.service.pay.domain.repository;


import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.OrderLog;

public interface OrderLogRepository extends BaseRepository<OrderLog, Long> {
}
