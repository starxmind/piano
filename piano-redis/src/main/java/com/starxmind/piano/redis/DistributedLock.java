package com.starxmind.piano.redis;

import com.starxmind.bass.concurrent.XLock;
import com.starxmind.bass.concurrent.exceptions.LockException;
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
public class DistributedLock implements XLock {
    private final RLock lock;

    public DistributedLock(RLock lock) {
        this.lock = lock;
    }

    public boolean isLocked() {
        return lock.isLocked();
    }

    public boolean isHeldByCurrentThread() {
        return lock.isHeldByCurrentThread();
    }

    public void lock() {
        lock.lock();
    }

    public void lock(long leaseTime, TimeUnit timeUnit) {
        lock.lock(leaseTime, timeUnit);
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(long waitTime, TimeUnit timeUnit) {
        try {
            return lock.tryLock(waitTime, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(String.format("Acquire lock fail by thread interrupted,path:%s", lock.getName()), e);
        }
    }

    public boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) {
        try {
            return lock.tryLock(waitTime, leaseTime, timeUnit);
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
