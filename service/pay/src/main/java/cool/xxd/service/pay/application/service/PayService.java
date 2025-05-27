package cool.xxd.service.pay.application.service;

import cool.xxd.service.pay.domain.command.CloseCommand;
import cool.xxd.service.pay.domain.command.PayCommand;
import cool.xxd.service.pay.domain.valueobject.PayResult;

public interface PayService {

    Long pay(PayCommand payCommand);

    void syncPayStatus();

    void updatePayResult(String payOrderNo, PayResult payResult);

    void updatePayResult(Long payOrderId, PayResult payResult);

    void close(CloseCommand closeCommand);

    void batchExpire();

}
