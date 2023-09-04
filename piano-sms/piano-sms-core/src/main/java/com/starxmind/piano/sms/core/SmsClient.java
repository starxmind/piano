package com.starxmind.piano.sms.core;


import com.starxmind.piano.sms.core.request.SmsRequest;

/**
 * 短信业务
 *
 * @author pizzalord
 * @since 1.0
 */
public interface SmsClient {
    /**
     * 发送短信
     *
     * @param smsRequest 发送短信的请求
     */
    void send(SmsRequest smsRequest);
}
