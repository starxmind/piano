package com.starxmind.piano.wechat.client.response;

import lombok.Data;

/**
 * 会话返回
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class WechatSessionResponse extends WechatResponse {
    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html
     */
    private String unionid;

    /**
     * 用户唯一标识
     */
    private String openid;
}
