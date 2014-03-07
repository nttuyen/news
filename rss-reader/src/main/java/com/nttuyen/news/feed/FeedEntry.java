package com.nttuyen.news.feed;

import java.util.Date;

public interface FeedEntry {
    String getTitle();
    String getLink();
    String getDescription();
    Image getImage();
    Date getPublishDate();
    String getCategory();

    interface FeedEntryBuilder extends Builder<FeedEntry> {
        FeedEntryBuilder withTitle(String title);
        FeedEntryBuilder withLink(String link);
        FeedEntryBuilder withDescription(String description);
        FeedEntryBuilder withImage(Image image);
        FeedEntryBuilder withPublishDate(Date publishDate);
        FeedEntryBuilder withCategory(String category);
    }
}