package cool.xxd.service.pay.domain.validator;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.repository.RefundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class UniqueRefundNoValidationHandler implements ValidationHandler {
    private ValidationHandler next;

    private final RefundOrderRepository refundOrderRepository;

    @Override
    public void setNext(ValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(RefundValidateContext refundValidateContext) {
        var refundCommand = refundValidateContext.getRefundCommand();
        refundOrderRepository.findByMchidAndOutRefundNo(refundCommand.getAppid(), refundCommand.getOutRefundNo())
                .ifPresent(e -> {
                    throw new BusinessException("商户退款单号不可重复");
                });
        if (next != null) {
            next.handle(refundValidateContext);
        }
    }
}