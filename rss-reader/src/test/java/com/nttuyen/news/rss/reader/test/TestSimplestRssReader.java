package com.nttuyen.news.rss.reader.test;

import com.nttuyen.news.rss.FeedReader;
import com.nttuyen.news.rss.FeedReaderException;
import com.nttuyen.news.rss.NewsChanel;
import com.nttuyen.news.rss.impl.FeedReaderImpl;
import com.sun.syndication.io.ParsingFeedException;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class TestSimplestRssReader {
	Logger log = Logger.getLogger(TestSimplestRssReader.class);

	private FeedReader feedReader;

	@Before
	public void before() {
		this.feedReader = new FeedReaderImpl();
	}
	@After
	public void after() {
		this.feedReader = null;
	}

	@Test
	public void testReadNullRSSURL() {
		log.info("Test reader rss from NULL url");
		String url = null;
		try {
			List<NewsChanel> chanels = feedReader.read(url);
			Assert.fail("FeedReaderException must be thrown");
		} catch (FeedReaderException ex) {
			Assert.assertEquals("URL must not null", ex.getMessage());
		}
	}

	@Test
	public void testreadRssFromNotURL() {
		log.info("Test read rss from invalid url");
		String url = "url";
		try {
			List<NewsChanel> chanels = feedReader.read(url);
			Assert.fail("Exception must be thrown");
		} catch (FeedReaderException ex) {
			Throwable e = ex.getCause();
			Assert.assertEquals(MalformedURLException.class, e.getClass());
		}
	}

	@Test
	public void testReadRssFormNoExistURL() {
		log.info("Test read rss from not existing url");
		String url = "http://my.nttuyen.com";
		try {
			List<NewsChanel> chanels = feedReader.read(url);
			Assert.fail("Exception must be thrown");
		} catch (FeedReaderException ex) {
			Throwable e = ex.getCause();

			Assert.assertTrue(IOException.class.isAssignableFrom(e.getClass()));
		}
	}

	@Test
	public void testReadRssFormNotValidRss() {
		log.info("Test read rss from not valid rss url");
		String url = "http://google.com";
		try {
			List<NewsChanel> chanels = feedReader.read(url);
			Assert.fail("Exception must be thrown");
		} catch (FeedReaderException ex) {
			Throwable e = ex.getCause();
			Assert.assertEquals(ParsingFeedException.class, e.getClass());
		}
	}

	@Test
	public void simplestRssReader() {
		log.info("Test simplest rss reader");

		String url = "http://fastpshb.appspot.com/feed/1/fastpshb";
		try {
			List<NewsChanel> chanels = feedReader.read(url);

			Assert.assertNotNull(chanels);
			Assert.assertEquals(1, chanels.size());
		} catch (FeedReaderException ex) {
			Assert.fail("Exception must not be thrown");
		}
	}
}
