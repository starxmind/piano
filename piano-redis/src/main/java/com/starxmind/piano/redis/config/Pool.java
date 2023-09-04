package com.starxmind.piano.redis.config;

import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class Pool {
    private int maxPoolSize;
    private int minIdleSize;
}
