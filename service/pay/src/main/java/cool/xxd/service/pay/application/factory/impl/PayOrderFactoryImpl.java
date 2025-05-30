package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.infra.serial.SerialNoGenerator;
import cool.xxd.service.pay.application.model.PayOrderDO;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.command.PayCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.constants.Constants;
import cool.xxd.service.pay.domain.enums.PayChannelEnum;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.factory.PayOrderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PayOrderFactoryImpl implements PayOrderFactory {

    private final IdGenerator idGenerator;
    private final SerialNoGenerator serialNoGenerator;

    @Override
    public PayOrder create(App app, Merchant merchant, PayCommand payCommand, PayTypeEnum payType, PayChannelEnum payChannel) {
        var now = LocalDateTime.now();
        var payOrder = new PayOrder();
        payOrder.setId(idGenerator.nextId(PayOrderDO.class));
        payOrder.setAppid(app.getAppid());
        payOrder.setMchid(merchant.getMchid());
        var payOrderNo = generatePayOrderNo(app.getOrderNoPrefix(), now);
        payOrder.setPayOrderNo(payOrderNo);
        payOrder.setOutTradeNo(payCommand.getOutTradeNo());
        payOrder.setTotalAmount(payCommand.getTotalAmount());
        payOrder.setRefundedAmount(BigDecimal.ZERO);
        payOrder.setTransMode(payCommand.getTransMode());
        payOrder.setAuthCode(payCommand.getAuthCode());
        payOrder.setSubAppid(payCommand.getSubAppid());
        payOrder.setSubOpenid(payCommand.getSubOpenid());
        payOrder.setPayTypeCode(payType.getCode());
        payOrder.setPayTypeName(payType.getName());
        if (PayTypeEnum.CASH == payType) {
            payOrder.setPayChannelCode(PayChannelEnum.CASH.getCode());
            payOrder.setPayChannelName(PayChannelEnum.CASH.getName());
        } else {
            payOrder.setPayChannelCode(payChannel.getCode());
            payOrder.setPayChannelName(payChannel.getName());
        }
        payOrder.setTradeTime(now);
        if (payCommand.getExpireMinutes() != null) {
            payOrder.setTimeExpire(now.plusMinutes(payCommand.getExpireMinutes()));
        } else {
            payOrder.setTimeExpire(now.plusMinutes(15));
        }
        payOrder.setSubject(payCommand.getSubject());
        payOrder.setDescription(payCommand.getDescription());
        payOrder.setCustomData(payCommand.getCustomData());
        payOrder.setPayStatus(PayStatusEnum.UNPAID);
        return payOrder;
    }

    private String generatePayOrderNo(String orderNoPrefix, LocalDateTime now) {
        var dateStr = now.toLocalDate().format(Constants.DTF);
        var key = CacheKeys.ORDER_NO + dateStr;
        return orderNoPrefix + dateStr + serialNoGenerator.generate(key, 6);
    }
}
