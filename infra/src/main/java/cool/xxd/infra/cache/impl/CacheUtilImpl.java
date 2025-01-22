package cool.xxd.infra.cache.impl;

import cool.xxd.infra.cache.CacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheUtilImpl implements CacheUtil {

    private static final long LOCK_DEFAULT_WAIT_TIME = 30L;

    private static final long LOCK_DEFAULT_LEASE_TIME = 60L;

    private final RedissonClient redissonClient;

    private final TypedJsonJacksonCodec typedJsonJacksonCodec;

    @Override
    public long increment(String key, Duration duration) {
        var atomicLong = redissonClient.getAtomicLong(key);
        var count = atomicLong.incrementAndGet();
        atomicLong.expire(duration);
        return count;
    }

    @Override
    public <T> void save(String key, T value, Duration duration) {
        Objects.requireNonNull(duration);
        RBucket<T> bucket = redissonClient.getBucket(key, typedJsonJacksonCodec);
        bucket.set(value, duration);
    }

    @Override
    public void remove(String key) {
        redissonClient.getBucket(key).delete();
    }

    @Override
    public void remove(List<String> keys) {
        var rKeys = redissonClient.getKeys();
        rKeys.delete(keys.toArray(new String[0]));
    }

    @Override
    public <T> void add(String key, T value) {
        RList<T> list = redissonClient.getList(key, typedJsonJacksonCodec);
        list.add(value);
    }

    @Override
    public <T> void addAll(String key, List<T> value) {
        RList<T> list = redissonClient.getList(key, typedJsonJacksonCodec);
        list.addAll(value);
    }

    @Override
    public <T> Optional<T> load(String key, Class<T> clazz) {
        RBucket<T> bucket = redissonClient.getBucket(key, typedJsonJacksonCodec);
        return Optional.ofNullable(bucket.get());
    }

    @Override
    public boolean tryLock(String key) {
        return tryLock(key, LOCK_DEFAULT_WAIT_TIME, LOCK_DEFAULT_LEASE_TIME);
    }

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime) {
        return tryLock(key, waitTime, leaseTime, TimeUnit.SECONDS);
    }

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        var lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("lock error, key-{}", key, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock(String key) {
        var lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
