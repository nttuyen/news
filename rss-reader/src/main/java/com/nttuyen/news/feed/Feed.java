package com.nttuyen.news.feed;

import java.util.Date;
import java.util.List;

public interface Feed {
    String getTitle();
    String getLink();
    String getDescription();

    Image getImage();
    Date getPublishDate();
    Date getLastUpdateDate();
    int getTtl();
    String getCategory();

    List<FeedEntry> getEntries();

	@Deprecated
    interface FeedBuilder extends Builder<Feed> {
        FeedBuilder withTitle(String title);
        FeedBuilder withLink(String linke);
        FeedBuilder withDescription(String description);
        FeedBuilder withImage(Image image);
        FeedBuilder withPublishDate(Date publishDate);
        FeedBuilder withLastUpdateDate(Date lastUpdateDate);
        FeedBuilder withTtl(int ttl);
        FeedBuilder withCategory(String category);
        FeedBuilder withEntries(List<FeedEntry> entries);
        FeedBuilder addEntry(FeedEntry entry);
        FeedBuilder addEntry(FeedEntry entry, int index);
        FeedBuilder removeEntry(FeedEntry entry);
        FeedBuilder removeEntryAt(int index);
    }
}
