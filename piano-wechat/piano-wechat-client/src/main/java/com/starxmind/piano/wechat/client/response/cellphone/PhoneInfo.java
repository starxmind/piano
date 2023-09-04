package com.starxmind.piano.wechat.client.response.cellphone;

import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class PhoneInfo {
    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;

    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;

    /**
     * 区号
     */
    private String countryCode;

    /**
     * 数据水印
     */
    private Watermark watermark;
}
