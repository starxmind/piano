package com.starxmind.piano.verifycode.test;

import com.starxmind.piano.verifycode.MemoryVerifyCodeCache;
import org.junit.Test;

public class VerifyCodeCacheTest {
    @Test
    public void testMemoryVerifyCodeCache() {
        MemoryVerifyCodeCache a = new MemoryVerifyCodeCache();
        MemoryVerifyCodeCache b = new MemoryVerifyCodeCache(10_000);
        System.out.println(a.getExpirationMilliseconds());
        System.out.println(b.getExpirationMilliseconds());
    }
}
