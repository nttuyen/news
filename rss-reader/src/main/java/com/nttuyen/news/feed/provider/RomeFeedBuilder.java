package com.nttuyen.news.feed.provider;

import com.nttuyen.common.Builder;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;
import com.nttuyen.news.feed.impl.FeedImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class RomeFeedBuilder implements Builder<Feed> {
	private final SyndFeed syndFeed;

	public RomeFeedBuilder(SyndFeed syndFeed) {
		if(syndFeed == null) {
			throw new IllegalArgumentException("SyndFeed object must not null");
		}
		this.syndFeed = syndFeed;
	}

	@Override
	public Feed build() {
		//Process image
		SyndImage syndImage = syndFeed.getImage();
		Image image = null;
		if(syndImage != null) {
			image = new Image(syndImage.getUrl(), syndImage.getTitle(), syndImage.getLink(), 0, 0, syndImage.getDescription());
		}

		//Build entry
		List<FeedEntry> entries = new LinkedList<FeedEntry>();
		List<SyndEntry> syndEntries = syndFeed.getEntries();
		for(SyndEntry entry : syndEntries) {
			RomeFeedEntryBuilder entryBuilder = new RomeFeedEntryBuilder(entry);
			entries.add(entryBuilder.build());
		}

		//TODO: how to read lastBuidDate and TTL and category
		return new FeedImpl(syndFeed.getTitle(),
				syndFeed.getLink(),
				syndFeed.getDescription(),
				image,
				syndFeed.getPublishedDate(),
				null,
				0,
				null,
				entries);
	}
}
