package com.starxmind.piano.redis;

import com.starxmind.piano.redis.exceptions.LockException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public class DistributedLock {
    private final RedissonClient redissonClient;
    private final String lockKey;
    private final RLock internalLock;

    public DistributedLock(RedissonClient redissonClient, String lockKey) {
        this.redissonClient = redissonClient;
        this.lockKey = lockKey;
        this.internalLock = initInternalLock();
    }

    private RLock initInternalLock() {
        return redissonClient.getLock(lockKey);
    }

    public boolean isLock() {
        return internalLock.isLocked();
    }

    public boolean isHeldByCurrentThread() {
        return internalLock.isHeldByCurrentThread();
    }

    public void lock(long leaseTime, TimeUnit unit) {
        internalLock.lock(leaseTime, unit);
    }

    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        try {
            return internalLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(String.format("Acquire lock fail by thread interrupted,path:%s", lockKey), e);
        }
    }

    public void unlock() {
        try {
            internalLock.unlock();
        } catch (IllegalMonitorStateException ex) {
            log.warn("Unlock path:{} error for thread status change in concurrency", lockKey, ex);
        }
    }
}
