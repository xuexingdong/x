package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.infra.serial.SerialNoGenerator;
import cool.xxd.service.pay.application.model.RefundOrderDO;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.command.RefundCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.constants.Constants;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.factory.RefundOrderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RefundOrderFactoryImpl implements RefundOrderFactory {

    private final IdGenerator idGenerator;
    private final SerialNoGenerator serialNoGenerator;

    @Override
    public RefundOrder create(App app, PayOrder payOrder, RefundCommand refundCommand) {
        var refundOrder = new RefundOrder();
        refundOrder.setId(idGenerator.nextId(RefundOrderDO.class));
        refundOrder.setAppid(payOrder.getAppid());
        refundOrder.setMchid(payOrder.getMchid());
        refundOrder.setPayOrderNo(payOrder.getPayOrderNo());
        refundOrder.setOutTradeNo(payOrder.getOutTradeNo());
        var refundOrderNo = generateRefundOrderNo(app.getOrderNoPrefix());
        refundOrder.setRefundOrderNo(refundOrderNo);
        refundOrder.setOutRefundNo(refundCommand.getOutRefundNo());
        refundOrder.setTotalAmount(payOrder.getTotalAmount());
        refundOrder.setRefundAmount(refundCommand.getRefundAmount());
        refundOrder.setRefundReason(refundCommand.getRefundReason());
        refundOrder.setPayTypeCode(payOrder.getPayTypeCode());
        refundOrder.setPayTypeName(payOrder.getPayTypeName());
        refundOrder.setPayChannelCode(payOrder.getPayChannelCode());
        refundOrder.setPayChannelName(payOrder.getPayChannelName());
        refundOrder.setRefundStatus(RefundStatusEnum.INIT);
        return refundOrder;
    }

    private String generateRefundOrderNo(String orderNoPrefix) {
        var dateStr = LocalDate.now().format(Constants.DTF);
        var key = CacheKeys.REFUND_ORDER_NO + dateStr;
        return orderNoPrefix + "R" + dateStr + serialNoGenerator.generate(key, 6);
    }
}
