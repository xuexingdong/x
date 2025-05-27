package cool.xxd.service.pay.domain.strategy.fuiou;

import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.utils.BigDecimalUtils;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class PayRequestBuilder {
    private static final DateTimeFormatter TIME_DTF = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static void setCommonFields(BasePayRequest payRequest, PayOrder payOrder) {
        if (payOrder.getDescription() != null) {
            payRequest.setGoodsDes(payOrder.getDescription());
        }
        if (payOrder.getCustomData() != null) {
            payRequest.setAddnInf(payOrder.getCustomData());
        }
        payRequest.setMchntOrderNo(payOrder.getPayOrderNo());
        payRequest.setCurrType("CNY");
        payRequest.setOrderAmt(BigDecimalUtils.convertYuanToCents(payOrder.getTotalAmount()));
        payRequest.setTermIp("127.0.0.1");
        payRequest.setTxnBeginTs(payOrder.getTradeTime().format(TIME_DTF));
        var minutes = Duration.between(payOrder.getTradeTime(), payOrder.getTimeExpire()).toMinutes();
        payRequest.setReservedExpireMinute(String.valueOf(minutes));
    }
}
