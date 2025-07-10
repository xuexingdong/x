package cool.xxd.service.pay.domain.domainservice;


import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.valueobject.PayResult;

public interface PayDomainService {

    PayTypeEnum getPayType(String authCode);

    void startPay(PayOrder payOrder);

    void updatePayResult(String mchid, Long payOrderId, PayResult payResult);

    void fail(PayOrder payOrder);

    void close(PayOrder payOrder);
}
