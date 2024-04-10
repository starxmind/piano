package com.starxmind.piano.verifycode;

import lombok.Getter;

@Getter
public abstract class VerifyCodeCache {
    private static final long DEFAULT_EXPIRATION_MILLISECONDS = 60_000;

    private final long expirationMilliseconds;

    public VerifyCodeCache() {
        this.expirationMilliseconds = DEFAULT_EXPIRATION_MILLISECONDS;
    }

    public VerifyCodeCache(long expirationMilliseconds) {
        this.expirationMilliseconds = expirationMilliseconds;
    }

    String cacheKey(String receiver, String scene) {
        return "verifycode:" + scene + ":" + receiver;
    }

    public abstract void put(String receiver, String verifyCode, String scene);

    public abstract String get(String receiver, String scene);
}
