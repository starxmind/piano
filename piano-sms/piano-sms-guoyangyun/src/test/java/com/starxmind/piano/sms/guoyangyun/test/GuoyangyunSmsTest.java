package com.starxmind.piano.sms.guoyangyun.test;

import com.google.common.collect.Maps;
import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.sms.core.request.SmsRequest;
import com.starxmind.piano.sms.guoyangyun.GuoyangyunSmsClient;

import java.util.Map;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class GuoyangyunSmsTest {
    //    @Test
    public void send() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("code", "1234");
        params.put("minute", "10");
        StarxHttp StarxHttp = new StarxHttp();
        GuoyangyunSmsClient guoyangyunSmsClient = new GuoyangyunSmsClient("xxx", StarxHttp);
        guoyangyunSmsClient.send(
                SmsRequest.builder()
                        .signName("xxx")
                        .templateCode("xxx")
                        .templateParam(params)
                        .phoneNumber("13812341234")
                        .build()
        );
    }
}
