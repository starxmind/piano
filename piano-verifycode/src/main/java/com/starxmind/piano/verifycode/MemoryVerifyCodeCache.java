package com.starxmind.piano.verifycode;

import com.starxmind.bass.datastructure.tree.ExpiringMap;

public class MemoryVerifyCodeCache extends VerifyCodeCache {
    ExpiringMap<String, String> expiringMap = new ExpiringMap<>();

    public MemoryVerifyCodeCache() {
    }

    public MemoryVerifyCodeCache(long expirationMilliseconds) {
        super(expirationMilliseconds);
    }

    @Override
    public void put(String receiver, String verifyCode, String scene) {
        expiringMap.put(cacheKey(receiver, scene), verifyCode, getExpirationMilliseconds());
    }

    @Override
    public String get(String receiver, String scene) {
        return expiringMap.get(cacheKey(receiver, scene));
    }
}
