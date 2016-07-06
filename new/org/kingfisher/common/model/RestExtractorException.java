package org.kingfisher.common.model;

// TODO: 06.07.2016 добавить полное описание
public class RestExtractorException extends Exception {
    public RestExtractorException() {
    }

    public RestExtractorException(final String message) {
        super(message);
    }

    public RestExtractorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RestExtractorException(final Throwable cause) {
        super(cause);
    }

    public RestExtractorException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
