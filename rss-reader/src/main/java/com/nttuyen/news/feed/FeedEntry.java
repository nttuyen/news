package com.nttuyen.news.feed;

import java.util.Date;

public interface FeedEntry {
    String getTitle();
    String getLink();
    String getDescription();
    Image getImage();
    String getAuthor();
    Date getPublishDate();
    String getCategory();
}
