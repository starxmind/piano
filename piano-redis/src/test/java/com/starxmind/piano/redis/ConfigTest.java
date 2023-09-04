package com.starxmind.piano.redis;

import com.starxmind.piano.redis.config.StandaloneConfig;
import org.junit.Test;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class ConfigTest {
    @Test
    public void testConfig() {
        StandaloneConfig standaloneConfig = new StandaloneConfig();
        System.out.println(standaloneConfig);
    }
}
