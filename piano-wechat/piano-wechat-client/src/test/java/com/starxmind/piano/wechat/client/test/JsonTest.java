package com.starxmind.piano.wechat.client.test;

import org.junit.Test;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class JsonTest {
    @Test
    public void testJsonString() {
        String json = String.format("{\"code\":\"%s\"}", "123");
        System.out.println(json);
    }
}
