package org.kingfisher.common.model;

// TODO: 04.07.2016 добавить полное описание
public class RestValidationException extends RestHandlerException {
    public RestValidationException() {
    }

    public RestValidationException(final String message) {
        super(message);
    }

    public RestValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RestValidationException(final Throwable cause) {
        super(cause);
    }

    public RestValidationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
