package cool.xxd.mapstruct.cache;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface CacheUtil {

    long increment(String key, Duration duration);

    <T> void save(String key, T value, Duration duration);

    void remove(String key);

    void remove(List<String> keys);

    <T> Optional<T> load(String key, Class<T> clazz);

    boolean tryLock(String key);

    boolean tryLock(String key, long waitTime, long leaseTime);

    boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit);

    void unlock(String key);

}
