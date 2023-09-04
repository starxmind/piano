package com.starxmind.piano.wechat.client.exceptions;

/**
 * 微信客户端异常
 *
 * @author pizzalord
 * @since 1.0
 */
public class WechatClientException extends RuntimeException {
    private static final long serialVersionUID = 6701077920489142930L;

    public WechatClientException() {
    }

    public WechatClientException(String message) {
        super(message);
    }

    public WechatClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatClientException(Throwable cause) {
        super(cause);
    }

    public WechatClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
