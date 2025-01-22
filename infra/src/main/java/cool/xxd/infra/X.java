package cool.xxd.infra;

import cool.xxd.infra.cache.CacheUtil;
import cool.xxd.infra.idgen.IdGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class X {
    public static IdGenerator id;
    public static CacheUtil cache;

    private final IdGenerator idGeneratorInstance;
    private final CacheUtil cacheUtilInstance;

    @PostConstruct
    public void init() {
        X.id = idGeneratorInstance;
        X.cache = cacheUtilInstance;
    }
}
