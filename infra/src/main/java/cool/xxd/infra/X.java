package cool.xxd.infra;

import cool.xxd.infra.cache.CacheUtil;
import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.infra.serial.SerialNoGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class X {
    public static IdGenerator id;
    public static CacheUtil cache;
    public static SerialNoGenerator serial;

    private final IdGenerator idGeneratorInstance;
    private final CacheUtil cacheUtilInstance;
    private final SerialNoGenerator serialNoGeneratorInstance;

    @PostConstruct
    public void init() {
        X.id = idGeneratorInstance;
        X.cache = cacheUtilInstance;
        X.serial = serialNoGeneratorInstance;
    }
}
