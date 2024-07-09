package com.starxmind.piano.redis;

import com.starxmind.piano.redis.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public class DistributedLockFactory {
    private final RedissonClient redissonClient;

    public DistributedLockFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public DistributedLock get(String lockName) {
        final String localKey = KeyUtils.lockKey(lockName);
        return new DistributedLock(redissonClient, localKey);
    }
}
