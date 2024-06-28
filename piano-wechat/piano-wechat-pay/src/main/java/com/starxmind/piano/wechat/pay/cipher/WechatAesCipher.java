package com.starxmind.piano.wechat.pay.cipher;

import com.starxmind.bass.security.Base64Utils;
import com.wechat.pay.java.core.cipher.AeadAesCipher;

public final class WechatAesCipher {
    private final AeadAesCipher cipher;

    public WechatAesCipher(String key) {
        cipher = new AeadAesCipher(key.getBytes());
    }

    public String decrypt(String associatedData, String nonce, byte[] ciphertext) {
        return cipher.decrypt(associatedData.getBytes(), nonce.getBytes(), ciphertext);
    }

    public String decryptWithBase64(String associatedData, String nonce, String ciphertextBase64) {
        return decrypt(associatedData, nonce, Base64Utils.decrypt(ciphertextBase64));
    }
}
