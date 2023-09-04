package com.starxmind.piano.redis;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public enum DeploymentMode {
    Standalone,
    MasterSlaveReplication,
    Sentinel,
    Cluster;
}
