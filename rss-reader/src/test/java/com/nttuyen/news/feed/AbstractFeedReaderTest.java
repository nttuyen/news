package com.nttuyen.news.feed;

import org.junit.Assert;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author nttuyen266@gmail.com
 */
public abstract class AbstractFeedReaderTest {
	protected FeedReader feedReader;

	@Before
	public void before() {
		feedReader = new FeedReader();
	}

	protected Feed read(String file, String type) {
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream(file);
			Feed feed = feedReader.read(input, type);
			return feed;
		} catch (FeedException ex) {
			Assert.fail("Should not throw FeedException");
		} catch (IOException ex) {
			Assert.fail("Should not throw IOException");
		}
		return null;
	}
}
