package com.starxmind.piano.dingtalk;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class DingTalkResponse extends RuntimeException{
    private static final long serialVersionUID = 3236914832651203456L;

    public DingTalkResponse() {
    }

    public DingTalkResponse(String message) {
        super(message);
    }

    public DingTalkResponse(String message, Throwable cause) {
        super(message, cause);
    }

    public DingTalkResponse(Throwable cause) {
        super(cause);
    }

    public DingTalkResponse(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
