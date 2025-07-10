package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.infra.page.PageRequest;
import cool.xxd.infra.page.PageResult;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.query.PayOrderQuery;

import java.util.List;
import java.util.Optional;

public interface PayOrderRepository extends BaseRepository<PayOrder, Long> {

    void updateRefundedAmount(PayOrder payOrder);

    boolean updatePayResult(PayOrder payOrder, PayStatusEnum fromPayStatus);

    Optional<PayOrder> findById(String mchid, Long id);

    Optional<PayOrder> findByPayOrderNo(String mchid, String payOrderNo);

    Optional<PayOrder> findByOutTradeNo(String mchid, String outTradeNo);

    Optional<PayOrder> findByPayOrderNoAndOutTradeNo(String mchid, String payOrderNo, String outTradeNo);

    List<PayOrder> query(PayOrderQuery payOrderQuery);

    PageResult<PayOrder> query(PayOrderQuery payOrderQuery, PageRequest pageRequest);

}
