package com.starxmind.piano.redis.exceptions;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class LockException extends RuntimeException {
    private static final long serialVersionUID = -4294062370451044305L;

    public LockException() {
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

    public LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
