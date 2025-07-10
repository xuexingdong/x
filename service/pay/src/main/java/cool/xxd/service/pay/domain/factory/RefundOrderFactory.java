package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.command.RefundCommand;

public interface RefundOrderFactory {
    RefundOrder create(PayOrder payOrder, RefundCommand refundCommand);
}
