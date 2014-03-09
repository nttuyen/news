package com.nttuyen.news.feed.provider;

import com.nttuyen.news.feed.Builder;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedException;
import com.nttuyen.news.feed.FeedProvider;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author nttuyen266@gmail.com
 */
public class RomeFeedProvider implements FeedProvider {
	@Override
	public String[] getSupportedTypes() {
		return new String[] {"*"};
	}

	@Override
	public Feed read(InputStream input) throws IOException, FeedException {
		SyndFeedInput feedInput = new SyndFeedInput();
		try {
			SyndFeed syndFeed = feedInput.build(new InputStreamReader(input));
			Builder<Feed> feedBuilder = new RomeFeedBuilder(syndFeed);
			return feedBuilder.build();

		} catch (com.sun.syndication.io.FeedException e) {
			throw new FeedException(e);
		}
	}
}
