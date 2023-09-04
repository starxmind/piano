package com.starxmind.piano.wechat.token.core;

import lombok.Data;
import lombok.ToString;

/**
 * 调用凭据
 *
 * @author Xpizza
 * @since piano1.0
 */
@ToString
@Data
public class AccessTokenResponse {
    /**
     * 小程序全局唯一后台接口调用凭据
     */
    private String access_token;

    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    private Integer expires_in;

    /**
     * 错误码
     * -1 	    系统繁忙，此时请开发者稍候再试
     * 0 	    请求成功
     * 40001    AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性
     * 40002    请确保 grant_type 字段值为 client_credential
     * 40013    不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写
     */
    private int errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    private long timeGet;
}
