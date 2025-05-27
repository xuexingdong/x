package cool.xxd.service.pay.domain.exceptions;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayException extends RuntimeException {
    private int code = -1;

    public PayException() {
    }

    public PayException(String message) {
        super(message);
    }

    public PayException(int code, String message) {
        super(message);
        this.code = code;
    }
}
