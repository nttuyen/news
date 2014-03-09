package com.nttuyen.news.feed;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class TestCafeFFeedReader extends AbstractFeedReaderTest {

	@Test
	public void testReadHomeFeed() {
		Feed feed = this.read("rss/news/cafef.vn_Trang-chu.rss", "RSS/2.0/cafef.vn");

		Assert.assertNotNull(feed);

		Assert.assertEquals("Cổng thông tin, dữ liệu tài chính - chứng khoán Việt Nam | CafeF.vn",
				feed.getTitle());
		Assert.assertEquals("http://cafef.vn/", feed.getLink());
		Assert.assertEquals("Thông tin tài chính, chứng khoán, bất động sản, kinh tế, đầu tư, tài chính quốc tế, thông tin doanh nghiệp, lãi suất, tiền tệ, ngân hàng",
				feed.getDescription());

		Assert.assertNotNull(feed.getPublishDate());
		//TODO:
		Assert.assertNull(feed.getLastUpdateDate());
		//TODO: need to read ttl?
		//Assert.assertEquals(5, feed.getTtl());

		Assert.assertNotNull(feed.getImage());
		Image image = feed.getImage();
		Assert.assertEquals("http://cafef3.vcmedia.vn/v2/images/logo3.gif", image.url);
		Assert.assertEquals("Cổng thông tin, dữ liệu tài chính - chứng khoán Việt Nam | CafeF.vn", image.title);
		Assert.assertEquals("http://cafef.vn/", image.link);

		Assert.assertNotNull(feed.getEntries());

		List<FeedEntry> entries = feed.getEntries();
		Assert.assertEquals(50, entries.size());

		FeedEntry e0 = entries.get(0);
		Assert.assertEquals("Phụ nữ đầu tư như thế nào?",
				e0.getTitle().trim());
		Assert.assertEquals("http://cafef.vn/tai-chinh-quoc-te/phu-nu-dau-tu-nhu-the-nao-201403081102473107ca32.chn",
				e0.getLink());
		Assert.assertEquals("<a href=\"http://cafef.vn/tai-chinh-quoc-te/phu-nu-dau-tu-nhu-the-nao-201403081102473107ca32.chn\"><img width=\"80px\" border=\"0\" onerror=\"loadErrorImage(this,\"/storageimage.ashx?width=80&amp;height=0&amp;image=http://images1.cafef.vn/Images/Uploaded/Share/40286d7b2e4618f9b6b557f6eb959bb7/2014/03/08/2.png\");\" src=\"http://cafef.vcmedia.vn/thumb_w/80/Images/Uploaded/Share/40286d7b2e4618f9b6b557f6eb959bb7/2014/03/08/2.png\" align=\"left\" /></a>Phụ nữ có xu hướng tập trung vào những mục tiêu dài hạn hơn. Họ cũng suy nghĩ thấu đáo hơn và mất nhiều thời gian hơn để đưa ra các quyết định \"xuống tiền\".",
				e0.getDescription().trim());
		Assert.assertNotNull(e0.getPublishDate());
	}
}
