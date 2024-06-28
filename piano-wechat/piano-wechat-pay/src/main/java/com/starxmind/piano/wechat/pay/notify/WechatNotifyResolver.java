package com.starxmind.piano.wechat.pay.notify;

import com.starxmind.bass.json.XJson;
import com.starxmind.bass.sugar.Asserts;
import com.starxmind.piano.wechat.pay.cipher.WechatAesCipher;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class WechatNotifyResolver {
    private final WechatAesCipher wechatAesCipher;

    public Map<String, Object> resolve(Map<String, Object> notifyReq) {
        String eventType = notifyReq.get("event_type").toString();
        Asserts.equals("TRANSACTION.SUCCESS", eventType,
                "Check the request for event_type: " + eventType);
        Map<String, Object> resource = (Map<String, Object>) notifyReq.get("resource");
        String plainText = wechatAesCipher.decryptWithBase64(
                resource.get("associated_data").toString(),
                resource.get("nonce").toString(),
                resource.get("ciphertext").toString()
        );
        return XJson.deserializeObject(plainText, Map.class);
    }
}
