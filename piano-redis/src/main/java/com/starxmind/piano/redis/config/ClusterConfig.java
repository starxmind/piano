package com.starxmind.piano.redis.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 集群配置
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ClusterConfig {
    private Cluster cluster;
    private String password;
    @Builder.Default
    private int timeout = 3000;
    private Pool pool;
}
