package com.starxmind.piano.sms.aliyun.test;

import com.google.common.collect.ImmutableMap;
import com.starxmind.piano.sms.aliyun.AliyunSmsClient;
import com.starxmind.piano.sms.core.request.SmsRequest;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class AliyunSmsTest {
    //    @Test
    public void send() throws Exception {
        AliyunSmsClient aliyunSmsClient = new AliyunSmsClient("xxx", "xxx");
        aliyunSmsClient.send(
                SmsRequest.builder()
                        .signName("xxx")
                        .phoneNumber("13812341234")
                        .templateCode("SMS_1234")
                        .templateParam(ImmutableMap.of("code", "123456"))
                        .build()
        );
    }
}
