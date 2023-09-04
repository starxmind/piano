package com.starxmind.piano.redis.config;

import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class Cluster {
    private String nodes;
    private int maxRedirects;
}
