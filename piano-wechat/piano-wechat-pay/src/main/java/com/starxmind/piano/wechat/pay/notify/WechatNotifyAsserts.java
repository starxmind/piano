package com.starxmind.piano.wechat.pay.notify;

import com.starxmind.bass.sugar.Asserts;

import java.util.Map;

public abstract class WechatNotifyAsserts {
    public static void payOk(Map<String, Object> notifyReq) {
        Asserts.equals("TRANSACTION.SUCCESS", notifyReq.get("event_type"),
                "Wechat notify request is not 'SUCCESS'!");
    }
}
