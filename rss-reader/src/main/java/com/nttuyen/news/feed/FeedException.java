package com.nttuyen.news.feed;

public class FeedException extends Exception {
    public FeedException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FeedException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FeedException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FeedException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected FeedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
