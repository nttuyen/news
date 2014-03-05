package com.nttuyen.news.rss.impl;

import com.nttuyen.news.rss.FeedReader;
import com.nttuyen.news.rss.FeedReaderException;
import com.nttuyen.news.rss.NewsChanel;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class FeedReaderImpl implements FeedReader {
	@Override
	public NewsChanel read(String url) throws FeedReaderException {
		if(url == null) {
			throw new FeedReaderException("URL must not null");
		}

		try {
			URL href = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection)href.openConnection();
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(httpConnection));

			return null;
		} catch (MalformedURLException e) {
			throw new FeedReaderException(e);
		} catch (IOException ex) {
			throw new FeedReaderException(ex);
		} catch (FeedException e) {
			throw new FeedReaderException(e);
		}

	}
}
