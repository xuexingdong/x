package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.query.RefundOrderQuery;

import java.util.List;
import java.util.Optional;

public interface RefundOrderRepository extends BaseRepository<RefundOrder, Long> {

    boolean updateRefundResult(RefundOrder refundOrder, RefundStatusEnum fromRefundStatus);

    Optional<RefundOrder> findByRefundOrderId(String mchid, Long refundOrderId);

    Optional<RefundOrder> findByRefundOrderNo(String refundOrderNo);

    Optional<RefundOrder> findByMchidAndOutRefundNo(String mchid, String outRefundNo);

    List<RefundOrder> findByPayOrderNo(String payOrderNo);

    List<RefundOrder> query(RefundOrderQuery refundOrderQuery);

}
