package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.OrderLog;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;

public interface OrderLogFactory {
    OrderLog create(PayOrder payOrder, String req);

    OrderLog create(RefundOrder refundOrder, String req);
}
