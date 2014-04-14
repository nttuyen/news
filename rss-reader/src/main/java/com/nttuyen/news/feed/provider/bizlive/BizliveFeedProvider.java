package com.nttuyen.news.feed.provider.bizlive;

import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.impl.FeedEntryBuilder;
import com.nttuyen.news.feed.impl.ImageBuilder;
import com.nttuyen.news.feed.provider.rss.RSS20FeedProvider;
import org.apache.log4j.Logger;
import org.dom4j.Node;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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

    @Override
    protected FeedEntry buildEntry(Node node) {
        FeedEntry entry = super.buildEntry(node);
        final String link = entry.getLink();
        final String title = entry.getTitle();
        final String description = entry.getDescription();
        FeedEntryBuilder builder = new FeedEntryBuilder(entry);

        Document doc = Jsoup.parseBodyFragment(description);

        //Extract pure description
        builder.withDescription(doc.body().ownText());

        //Extract image
        Elements images = doc.select("a > img");
        if(images != null && images.size() > 0) {
            ImageBuilder imgBuilder = new ImageBuilder();
            imgBuilder.withURL(images.get(0).attr("src"));
            imgBuilder.withLink(link);
            imgBuilder.withTitle(title);
            try {
                String width = images.get(0).attr("width").replace("px", "");
                imgBuilder.withWidth(Integer.parseInt(width));
            } catch (Throwable ex) {}

            builder.withImage(imgBuilder.build());
        }

        return builder.build();
    }
}
