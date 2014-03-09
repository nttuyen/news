package com.nttuyen.news.feed;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class TestFeedReader {
	FeedReader feedReader;

	@Before
	public void before() {
		feedReader = new FeedReader();
	}

	@After
	public void after() {
		feedReader = null;
	}

	@Test
	public void testReadFromNullInput() {
		try {
			feedReader.read(null, "*");
		} catch (FeedException ex) {

		} catch (IOException ex) {
			Assert.fail("Should not throw IOException");
		}
	}

	@Test
	public void testReadFromNullType() {
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream("rss/sample/rss2sample.xml");
			feedReader.read(input, null);
		} catch (FeedException ex) {
			Assert.assertEquals("Can not found FeedProvider for type: null", ex.getMessage());
		} catch (IOException ex) {
			Assert.fail("Should not throw IOException");
		}
	}

	@Test
	public void testReadRss2() {
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream("rss/sample/rss2sample.xml");
			Feed feed = feedReader.read(input, "rss/2.0");

			Assert.assertNotNull(feed);

			Assert.assertEquals("Liftoff News", feed.getTitle());
			Assert.assertEquals("http://liftoff.msfc.nasa.gov/", feed.getLink());
			Assert.assertEquals("Liftoff to Space Exploration.", feed.getDescription());

			Assert.assertNotNull(feed.getPublishDate());
			Assert.assertNotNull(feed.getLastUpdateDate());
			Assert.assertNull(feed.getImage());

			Assert.assertNotNull(feed.getEntries());

			List<FeedEntry> entries = feed.getEntries();
			Assert.assertEquals(4, entries.size());

			FeedEntry e0 = entries.get(0);
			Assert.assertEquals("Star City", e0.getTitle());
			Assert.assertEquals("http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp", e0.getLink());
			Assert.assertEquals("How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>.", e0.getDescription());
			Assert.assertNotNull(e0.getPublishDate());

		} catch (FeedException ex) {
			Assert.fail("Should not throw FeedException");
		} catch (IOException ex) {
			Assert.fail("Should not throw IOException");
		}
	}

	@Test
	public void testReadRss092() {
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream("rss/sample/sampleRss092.xml");
			Feed feed = feedReader.read(input, "rss/0.92");

			Assert.assertNotNull(feed);

			Assert.assertEquals("Dave Winer: Grateful Dead", feed.getTitle());
			Assert.assertEquals("http://www.scripting.com/blog/categories/gratefulDead.html", feed.getLink());
			Assert.assertEquals("A high-fidelity Grateful Dead song every day. This is where we're experimenting with enclosures on RSS news items that download when you're not using your computer. If it works (it will) it will be the end of the Click-And-Wait multimedia experience on the Internet. ",
					feed.getDescription());

			System.out.println(feed.getPublishDate());
			Assert.assertNotNull(feed.getPublishDate());
			Assert.assertNotNull(feed.getLastUpdateDate());
			Assert.assertNull(feed.getImage());

			Assert.assertNotNull(feed.getEntries());

			List<FeedEntry> entries = feed.getEntries();
			Assert.assertEquals(22, entries.size());

			FeedEntry e0 = entries.get(0);
			//Assert.assertEquals("Star City", e0.getTitle());
			//Assert.assertEquals("http://www.scripting.com/mp3s/weatherReportDicksPicsVol7.mp3", e0.getLink());
			Assert.assertNull(e0.getTitle());
			Assert.assertNull(e0.getLink());
			Assert.assertEquals("It's been a few days since I added a song to the Grateful Dead channel. Now that there are all these new Radio users, many of whom are tuned into this channel (it's #16 on the hotlist of upstreaming Radio users, there's no way of knowing how many non-upstreaming users are subscribing, have to do something about this..). Anyway, tonight's song is a live version of Weather Report Suite from Dick's Picks Volume 7. It's wistful music. Of course a beautiful song, oft-quoted here on Scripting News. <i>A little change, the wind and rain.</i>", e0.getDescription());
			Assert.assertNull(e0.getPublishDate());

		} catch (FeedException ex) {
			Assert.fail("Should not throw FeedException");
		} catch (IOException ex) {
			Assert.fail("Should not throw IOException");
		}
	}
}
