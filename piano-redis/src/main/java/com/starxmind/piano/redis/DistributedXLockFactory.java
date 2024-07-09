package com.starxmind.piano.redis;

import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.bass.concurrent.lock.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public class DistributedXLockFactory implements XLockFactory {
    private final RedissonClient redissonClient;

    public DistributedXLockFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public DistributedXLock get(String lockName) {
        final String localKey = KeyUtils.lockKey(lockName);
        RLock nativeLock = redissonClient.getLock(localKey);
        return new DistributedXLock(nativeLock);
    }
}
