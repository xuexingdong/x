package cool.xxd.service.pay.application.service;

import cool.xxd.service.pay.domain.command.RefundCommand;
import cool.xxd.service.pay.domain.valueobject.RefundResult;

public interface RefundService {

    Long refund(RefundCommand refundCommand);

    void syncRefundStatus();

    void updateRefundResult(String refundOrderNo, RefundResult refundResult);

    void updateRefundResult(Long refundOrderId, RefundResult refundResult);

}
