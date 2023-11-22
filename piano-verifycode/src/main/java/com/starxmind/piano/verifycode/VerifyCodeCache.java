package com.starxmind.piano.verifycode;

public abstract class VerifyCodeCache {
    long expirationMilliseconds() {
        return 60_000;
    }

    String cacheKey(String receiver, String scene) {
        return "verifycode:" + scene + ":" + receiver;
    }

    public abstract void put(String receiver, String verifyCode, String scene);

    public abstract String get(String receiver, String scene);
}
