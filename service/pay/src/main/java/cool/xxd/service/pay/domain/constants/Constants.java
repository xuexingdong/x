package cool.xxd.service.pay.domain.constants;

import java.time.format.DateTimeFormatter;

public final class Constants {
    private Constants() {

    }

    public static final DateTimeFormatter DTF = DateTimeFormatter.BASIC_ISO_DATE;

    public static final long POLLING_DELAY_SECONDS = 60;
}
