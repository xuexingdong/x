package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.repository.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.query.RefundOrderQuery;

import java.util.List;
import java.util.Optional;

public interface RefundOrderRepository extends BaseRepository<RefundOrder, Long> {

    boolean updateRefundResult(RefundOrder refundOrder, RefundStatusEnum fromRefundStatus);

    Optional<RefundOrder> findByRefundOrderNo(String refundOrderNo);

    Optional<RefundOrder> findByAppidAndOutRefundNo(String appid, String outRefundNo);

    List<RefundOrder> findByPayOrderNo(String payOrderNo);

    List<RefundOrder> query(RefundOrderQuery refundOrderQuery);

}
