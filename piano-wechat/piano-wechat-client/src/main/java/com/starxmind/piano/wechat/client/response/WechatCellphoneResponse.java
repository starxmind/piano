package com.starxmind.piano.wechat.client.response;

import com.starxmind.piano.wechat.client.response.cellphone.PhoneInfo;
import lombok.Data;

/**
 * 手机号返回
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class WechatCellphoneResponse extends WechatResponse {
    private PhoneInfo phone_info;
}
