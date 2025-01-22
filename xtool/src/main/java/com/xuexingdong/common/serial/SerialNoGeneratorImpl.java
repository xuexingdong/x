package cool.xxd.mapstruct.serial;

import cool.xxd.mapstruct.cache.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SerialNoGeneratorImpl implements SerialNoGenerator {

    private final DateTimeFormatter DTF = DateTimeFormatter.BASIC_ISO_DATE;

    private final CacheUtil cacheUtil;

    @Override
    public String generate(Integer digits) {
        return generate(LocalDate.now().format(DTF), digits);
    }

    @Override
    public String generate(String key, Integer digits) {
        if (digits <= 0) {
            throw new IllegalArgumentException("Digits must be greater than 0");
        }

        var count = cacheUtil.increment(key, Duration.ofDays(1));
        if (count > calculateMaxValue(digits)) {
            throw new IllegalArgumentException("Count exceeds maximum value for the given digits");
        }

        var formattedCount = String.format("%0" + digits + "d", count);
        return key + formattedCount;
    }

    private int calculateMaxValue(Integer digits) {
        return (int) Math.pow(10, digits) - 1;
    }
}
