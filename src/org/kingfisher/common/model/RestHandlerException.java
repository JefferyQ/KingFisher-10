package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить полное описание
public class RestHandlerException extends Exception {
    public RestHandlerException() {
    }

    public RestHandlerException(final String message) {
        super(message);
    }

    public RestHandlerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RestHandlerException(final Throwable cause) {
        super(cause);
    }

    public RestHandlerException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
