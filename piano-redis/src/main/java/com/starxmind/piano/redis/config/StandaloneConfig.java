package com.starxmind.piano.redis.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单机配置
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class StandaloneConfig {
    private String host;
    private int port;
    private String password;
    private int database;
    @Builder.Default
    private int timeout = 3000;
    private Pool pool;
}
