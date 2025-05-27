package cool.xxd.service.pay.domain.validator;

public interface ValidationHandler {
    void setNext(ValidationHandler next);

    void handle(RefundValidateContext refundValidateContext);
}
