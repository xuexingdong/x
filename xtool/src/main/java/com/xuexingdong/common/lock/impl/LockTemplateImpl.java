package cool.xxd.mapstruct.lock.impl;

import cool.xxd.mapstruct.cache.CacheUtil;
import cool.xxd.mapstruct.exceptions.BusinessException;
import cool.xxd.mapstruct.lock.LockTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class LockTemplateImpl implements LockTemplate {

    private final CacheUtil cacheUtil;

    @Override
    public <T> T execute(String lockKey, Supplier<T> supplier) {
        boolean lockSuccess = cacheUtil.tryLock(lockKey);
        if (!lockSuccess) {
            throw new BusinessException("加锁失败");
        }
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("执行操作发生异常", e);
            throw e;
        } finally {
            cacheUtil.unlock(lockKey);
        }
    }

    @Override
    public void executeWithoutResult(String lockKey, Runnable runnable) {
        boolean lockSuccess = cacheUtil.tryLock(lockKey);
        if (!lockSuccess) {
            throw new BusinessException("加锁失败");
        }
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("执行操作发生异常", e);
            throw e;
        } finally {
            cacheUtil.unlock(lockKey);
        }
    }
}
