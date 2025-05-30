package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.service.pay.application.model.OrderLogDO;
import cool.xxd.service.pay.domain.aggregate.OrderLog;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.factory.OrderLogFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderLogFactoryImpl implements OrderLogFactory {

    private final IdGenerator idGenerator;

    @Override
    public OrderLog create(PayOrder payOrder, String req) {
        var orderLog = new OrderLog();
        orderLog.setId(idGenerator.nextId(OrderLogDO.class));
        orderLog.setAppid(payOrder.getAppid());
        orderLog.setMchid(payOrder.getMchid());
        orderLog.setPayOrderNo(payOrder.getPayOrderNo());
        orderLog.setReq(req);
        return orderLog;
    }

    @Override
    public OrderLog create(RefundOrder refundOrder, String req) {
        var orderLog = new OrderLog();
        orderLog.setId(idGenerator.nextId(OrderLogDO.class));
        orderLog.setAppid(refundOrder.getAppid());
        orderLog.setMchid(refundOrder.getMchid());
        orderLog.setPayOrderNo(refundOrder.getPayOrderNo());
        orderLog.setRefundOrderNo(refundOrder.getRefundOrderNo());
        orderLog.setReq(req);
        return orderLog;
    }
}
