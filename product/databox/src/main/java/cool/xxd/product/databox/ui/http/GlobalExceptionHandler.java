package cool.xxd.product.databox.ui.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(-1)
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ProblemDetail handleException(Exception ex) {
        log.error("Exception: ", ex);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation Exception: ", ex);

        var bindingResult = ex.getBindingResult();
        var errorMessages = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed: " + String.join("; ", errorMessages)
        );
    }
}

