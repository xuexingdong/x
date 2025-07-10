package cool.xxd.service.pay.domain.domainservice.impl;

import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.domainservice.PayDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.exceptions.PayException;
import cool.xxd.service.pay.domain.repository.PayOrderRepository;
import cool.xxd.service.pay.domain.utils.PayTypeDetector;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayDomainServiceImpl implements PayDomainService {

    private final PayOrderRepository payOrderRepository;

    @Override
    public PayTypeEnum getPayType(String authCode) {
        return switch (PayTypeDetector.detectPayType(authCode)) {
            case WECHAT_PAY -> PayTypeEnum.WECHAT_PAY;
            case ALIPAY -> PayTypeEnum.ALIPAY;
            case null -> throw new PayException("付款码格式错误，无法识别");
        };
    }

    @Override
    public void startPay(PayOrder payOrder) {
        // 先更新自己为支付中再调用三方，避免重复多次发起三方调用
        payOrder.startPay();
        payOrderRepository.updatePayResult(payOrder, PayStatusEnum.UNPAID);
    }

    @Override
    public void updatePayResult(String mchid, Long payOrderId, PayResult payResult) {
        var payOrder = payOrderRepository.findById(mchid, payOrderId)
                .orElseThrow(() -> new PayException("支付单不存在"));
        if (payOrder.isFinalPayStatus()
                && payOrder.getPayStatus() == payResult.getPayStatus()) {
            log.info("支付单已完成，幂等处理");
            return;
        }
        payOrder.handlePayResult(payResult);
        var updateSuccess = payOrderRepository.updatePayResult(payOrder, PayStatusEnum.PAYING);
        if (!updateSuccess) {
            log.error("更新支付单支付结果失败，支付单号-{}", payOrder.getPayOrderNo());
            throw new PayException("更新支付单支付结果失败");
        }
    }

    @Override
    public void fail(PayOrder payOrder) {
        payOrder.fail();
        payOrderRepository.updatePayResult(payOrder, PayStatusEnum.PAYING);
    }

    @Override
    public void close(PayOrder payOrder) {
        if (payOrder.isClosed()) {
            return;
        }
        var fromPayStatus = payOrder.getPayStatus();
        payOrder.close();
        if (!payOrderRepository.updatePayResult(payOrder, fromPayStatus)) {
            log.error("过期超时支付单失败，支付单号-{}", payOrder.getPayOrderNo());
            throw new PayException("过期超时支付单失败");
        }
    }
}
