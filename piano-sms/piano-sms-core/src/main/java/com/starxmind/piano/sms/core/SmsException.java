package com.starxmind.piano.sms.core;

/**
 * 短信服务发送异常
 */
public class SmsException extends RuntimeException {

    private static final long serialVersionUID = 7593090975792430553L;

    public SmsException() {
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsException(Throwable cause) {
        super("短信发送失败", cause);
    }

    public SmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
