package com.nttuyen.news.feed.provider;

import com.nttuyen.common.Builder;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.impl.FeedEntryImpl;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * @author nttuyen266@gmail.com
 */
public class RomeFeedEntryBuilder implements Builder<FeedEntry> {
	private final SyndEntry entry;

	public RomeFeedEntryBuilder(SyndEntry entry) {
		if(entry == null) {
			throw new IllegalArgumentException("SyndEntry must not null");
		}
		this.entry = entry;
	}

	@Override
	public FeedEntry build() {
		//TODO: how to process images?
		return new FeedEntryImpl(entry.getTitle(),
				entry.getLink(),
				entry.getDescription().getValue(),
				null,
				entry.getAuthor(),
				entry.getPublishedDate(),
				null);
	}
}
