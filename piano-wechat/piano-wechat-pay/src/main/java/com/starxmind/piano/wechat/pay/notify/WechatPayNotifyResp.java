package com.starxmind.piano.wechat.pay.notify;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class WechatPayNotifyResp {
    private String code;
    private String message;

    public static WechatPayNotifyResp success() {
        return WechatPayNotifyResp.builder().code("SUCCESS").build();
    }

    public static WechatPayNotifyResp fail(String message) {
        return WechatPayNotifyResp.builder().code("FAIL").message(message).build();
    }
}
