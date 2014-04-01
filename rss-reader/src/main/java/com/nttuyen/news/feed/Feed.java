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
}
