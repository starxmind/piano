package com.starxmind.piano.wechat.client.response.cellphone;

import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class Watermark {
    /**
     * 用户获取手机号操作的时间戳
     */
    private long timestamp;

    /**
     * 小程序appid
     */
    private String appid;
}
