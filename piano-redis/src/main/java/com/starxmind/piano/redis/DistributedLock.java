package com.starxmind.piano.redis;

import com.starxmind.piano.redis.exceptions.LockException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public class DistributedLock {
    private final RLock lock;

    public DistributedLock(RLock lock) {
        this.lock = lock;
    }

    public boolean isLock() {
        return lock.isLocked();
    }

    public boolean isHeldByCurrentThread() {
        return lock.isHeldByCurrentThread();
    }

    public void lock(long leaseTime, TimeUnit unit) {
        lock.lock(leaseTime, unit);
    }

    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(String.format("Acquire lock fail by thread interrupted,path:%s", lock.getName()), e);
        }
    }

    public void unlock() {
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException ex) {
            log.warn("Unlock path:{} error for thread status change in concurrency", lock.getName(), ex);
        }
    }
}
