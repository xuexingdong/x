package cool.xxd.infra.serial;

import cool.xxd.infra.X;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class SerialNoGeneratorImpl implements SerialNoGenerator {

    private static final int DEFAULT_DIGITS = 0;

    @Override
    public String generate(String key) {
        return generate(key, DEFAULT_DIGITS);
    }

    @Override
    public String generate(String key, int digits) {
        var count = X.cache.increment(key, Duration.ofDays(1));
        if (digits <= 0) {
            return String.valueOf(count);
        }
        if (count > calculateMaxValue(digits)) {
            throw new IllegalArgumentException("Count exceeds maximum value for the given digits");
        }
        return String.format("%0" + digits + "d", count);
    }

    private int calculateMaxValue(Integer digits) {
        return (int) Math.pow(10, digits) - 1;
    }
}
