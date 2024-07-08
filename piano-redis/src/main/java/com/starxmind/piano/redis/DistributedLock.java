package com.starxmind.piano.redis;

import com.starxmind.piano.redis.exceptions.LockException;
import com.starxmind.piano.redis.utils.KeyUtils;
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

    public DistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RLock getLock(String lockKey) {
        return redissonClient.getLock(KeyUtils.lockKey(lockKey));
    }

    public boolean isLocked(String lockKey) {
        RLock lock = getLock(lockKey);
        return lock != null && lock.isLocked();
    }

    public boolean isHeldByCurrentThread(String lockKey) {
        RLock lock = getLock(lockKey);
        return lock.isHeldByCurrentThread();
    }

    public void lock(String lockKey) {
        RLock lock = getLock(lockKey);
        lock.lock();
    }

    public void lock(String lockKey, long leaseTime, TimeUnit unit) {
        RLock lock = getLock(lockKey);
        lock.lock(leaseTime, unit);
    }

    public boolean tryLock(String lockKey) {
        RLock lock = getLock(lockKey);
        return lock.tryLock();
    }

    public boolean tryLock(String lockKey, long waitTime, TimeUnit unit) {
        try {
            RLock lock = getLock(lockKey);
            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(String.format("Acquire lock fail by thread interrupted,path:%s", lockKey), e);
        }
    }

    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        try {
            RLock lock = getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(String.format("Acquire lock fail by thread interrupted,path:%s", lockKey), e);
        }
    }

    public void unlock(String lockKey) {
        try {
            RLock lock = getLock(lockKey);
            lock.unlock();
        } catch (IllegalMonitorStateException ex) {
            log.warn("Unlock path:{} error for thread status change in concurrency", lockKey, ex);
        }
    }
}
