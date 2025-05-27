package cool.xxd.service.pay.domain.domainservice;

import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.valueobject.RefundResult;

public interface RefundDomainService {
    void startRefund(RefundOrder refundOrder);

    void updateRefundResult(Long refundOrderId, RefundResult refundResult);

    void fail(RefundOrder refundOrder);

}
