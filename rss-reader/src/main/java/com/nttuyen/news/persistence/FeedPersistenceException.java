package com.nttuyen.news.persistence;

/**
 * @author nttuyen266@gmail.com
 */
public class FeedPersistenceException extends Exception {
	public FeedPersistenceException() {
		super();
	}

	public FeedPersistenceException(String message) {
		super(message);
	}

	public FeedPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FeedPersistenceException(Throwable cause) {
		super(cause);
	}

	protected FeedPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
