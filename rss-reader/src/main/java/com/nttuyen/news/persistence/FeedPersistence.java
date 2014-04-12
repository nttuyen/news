package com.nttuyen.news.persistence;

import com.nttuyen.news.feed.Feed;

/**
 * @author nttuyen266@gmail.com
 */
public interface FeedPersistence {
	void persist(Feed feed) throws FeedPersistenceException;
}
