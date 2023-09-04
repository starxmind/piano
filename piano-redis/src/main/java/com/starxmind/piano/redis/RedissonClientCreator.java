package com.starxmind.piano.redis;

import com.starxmind.piano.redis.config.ClusterConfig;
import com.starxmind.piano.redis.config.StandaloneConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * The creator of a redisson client
 *
 * @author pizzalord
 * @since 1.0
 */
public class RedissonClientCreator {
    /**
     * 单机部署配置
     *
     * @return 单机部署配置
     */
    public RedissonClient singleServerConfig(StandaloneConfig standaloneConfig) {
        Config config = new Config();
        config.useSingleServer().
                setAddress("redis://" + standaloneConfig.getHost() + ":" + standaloneConfig.getPort())
                .setPassword(standaloneConfig.getPassword())
                .setTimeout(standaloneConfig.getTimeout())
                .setDatabase(standaloneConfig.getDatabase())
                .setConnectionPoolSize(standaloneConfig.getPool().getMaxPoolSize())
                //最小空闲连接
                .setConnectionMinimumIdleSize(standaloneConfig.getPool().getMinIdleSize());
        return Redisson.create(config);
    }

    /**
     * 集群部署配置
     * 不支持多数据库，单机下的Redis可以支持16个数据库，但集群之只能使用一个数据库空间，即db0
     *
     * @return 集群部署配置
     */
    public RedissonClient clusterServerConfig(ClusterConfig clusterConfig) {
        Config config = new Config();
        // 集群节点
        String[] nodes = clusterConfig.getCluster().getNodes().split(",");
        // redisson版本>=3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }
        // 这是用的集群server
        config.useClusterServers()
                //设置集群状态扫描时间
//                .setScanInterval(2000)
                .addNodeAddress(nodes)
                .setPassword(clusterConfig.getPassword())
                .setTimeout(clusterConfig.getTimeout())
                .setSlaveConnectionPoolSize(clusterConfig.getPool().getMaxPoolSize())
                .setSlaveConnectionMinimumIdleSize(clusterConfig.getPool().getMinIdleSize());
        return Redisson.create(config);
    }
}
