package com.starxmind.piano.wechat.pay.notify;

import java.util.Map;

public interface WechatPayNotify {
    WechatPayNotifyResp wechatPayNotify(Map<String, Object> req);
}
