package cool.xxd.service.pay.domain.domainservice;

import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;

public interface OrderLogDomainService {
    long init(PayOrder payOrder, String req);

    long init(RefundOrder refundOrder, String req);

    void updateResp(Long id, String resp);
}
