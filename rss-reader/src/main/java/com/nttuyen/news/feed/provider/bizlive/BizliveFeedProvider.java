package com.nttuyen.news.feed.provider.bizlive;

import com.nttuyen.news.feed.provider.rss.RSS20FeedProvider;
import org.apache.log4j.Logger;

/**
 * @author nttuyen266@gmail.com
 */
public class BizliveFeedProvider extends RSS20FeedProvider {
	private static final Logger log = Logger.getLogger(BizliveFeedProvider.class);
	@Override
	public String[] getSupportedTypes() {
		return new String[]{
				"rss/2.0/bizlive.vn"
		};
	}
}
