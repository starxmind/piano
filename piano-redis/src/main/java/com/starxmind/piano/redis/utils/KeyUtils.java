package com.starxmind.piano.redis.utils;

/**
 * Redis key
 *
 * @author pizzalord
 * @since 1.0
 */
public abstract class KeyUtils {
    /**
     * Lock key
     *
     * @param key
     * @return
     */
    public static String lockKey(String key) {
        return "lock:" + key;
    }
}
