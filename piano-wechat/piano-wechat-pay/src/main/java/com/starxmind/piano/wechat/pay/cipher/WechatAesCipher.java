package com.starxmind.piano.wechat.pay.cipher;

import com.starxmind.bass.security.Base64Utils;
import com.wechat.pay.java.core.cipher.AeadAesCipher;

public final class WechatAesCipher {
    private final AeadAesCipher cipher;

    public WechatAesCipher(String key) {
        cipher = new AeadAesCipher(key.getBytes());
    }

    public String decrypt(String associatedData, String nonce, String ciphertext) {
        return cipher.decrypt(
                associatedData.getBytes(),
                nonce.getBytes(),
                Base64Utils.decrypt(ciphertext)
        );
    }
}
