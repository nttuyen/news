package com.nttuyen.news.rss;

/**
 * @author nttuyen266@gmail.com
 */
@Deprecated
public class FeedReaderException extends Exception {
	public FeedReaderException() {
		super();
	}

	public FeedReaderException(String message) {
		super(message);
	}

	public FeedReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public FeedReaderException(Throwable cause) {
		super(cause);
	}

	protected FeedReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
