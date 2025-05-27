package cool.xxd.service.pay.domain.domainservice.impl;

import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.factory.OrderLogFactory;
import cool.xxd.service.pay.domain.repository.OrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLogDomainServiceImpl implements OrderLogDomainService {

    private final OrderLogFactory orderLogFactory;
    private final OrderLogRepository orderLogRepository;

    @Override
    public long init(PayOrder payOrder, String req) {
        var orderLog = orderLogFactory.create(payOrder, req);
        orderLogRepository.save(orderLog);
        return orderLog.getId();
    }

    @Override
    public long init(RefundOrder refundOrder, String req) {
        var orderLog = orderLogFactory.create(refundOrder, req);
        orderLogRepository.save(orderLog);
        return orderLog.getId();
    }

    @Override
    public void updateResp(Long id, String resp) {
        var orderLog = orderLogRepository.getById(id);
        orderLog.updateResp(resp);
        orderLogRepository.update(orderLog);
    }
}
