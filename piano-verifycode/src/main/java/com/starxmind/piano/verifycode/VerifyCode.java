package com.starxmind.piano.verifycode;

import com.starxmind.bass.sugar.Asserts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class VerifyCode {
    private final VerifyCodeCache verifyCodeCache;

    public void send(String receiver, String verifyCode, String scene) {
        pushMessage(receiver, verifyCode);
        verifyCodeCache.put(receiver, verifyCode, scene);
    }

    protected abstract void pushMessage(String receiver, String verifyCode);

    public void check(String receiver, String reqVerifyCode, String scene) {
        String verifyCode = verifyCodeCache.get(receiver, scene);
        Asserts.equals(reqVerifyCode, verifyCode, "验证码错误");
    }
}
