package com.starxmind.piano.wechat.pay.notify;

import java.util.Map;

public abstract class WechatNotifyAsserts {
    public static boolean payOk(Map<String, Object> notifyReq) {
        return "TRANSACTION.SUCCESS".equals(notifyReq.get("event_type"));
    }
}
