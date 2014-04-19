package com.nttuyen.content.api;

/**
 * @author nttuyen266@gmail.com
 */
public class ContentServiceException extends Exception {
    public ContentServiceException() {
        super();
    }

    public ContentServiceException(String message) {
        super(message);
    }

    public ContentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentServiceException(Throwable cause) {
        super(cause);
    }

    protected ContentServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
