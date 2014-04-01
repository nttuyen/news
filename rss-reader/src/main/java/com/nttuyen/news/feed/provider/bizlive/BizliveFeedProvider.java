package com.nttuyen.news.feed.provider.bizlive;

import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedException;
import com.nttuyen.news.feed.FeedProvider;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author nttuyen266@gmail.com
 */
public class BizliveFeedProvider implements FeedProvider {
	@Override
	public String[] getSupportedTypes() {
		return new String[]{
				"rss/2.0/bizlive.vn"
		};
	}

	@Override
	public Feed read(InputStream input) throws IOException, FeedException {
		//TODO: how to install this?
		return null;
	}
}
