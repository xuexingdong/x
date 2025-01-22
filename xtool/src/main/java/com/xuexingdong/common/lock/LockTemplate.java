package cool.xxd.mapstruct.lock;

import java.util.function.Supplier;

public interface LockTemplate {

    <T> T execute(String lockKey, Supplier<T> supplier);

    void executeWithoutResult(String lockKey, Runnable action);
}
