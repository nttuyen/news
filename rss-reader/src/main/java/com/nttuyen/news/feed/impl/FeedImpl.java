/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.nttuyen.news.feed.impl;

import java.util.Date;
import java.util.List;

import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;

public class FeedImpl implements Feed {
    private final String title;
    private final String link;
    private final String description;
    private final Image image;
    private final Date publishDate;
    private final Date lastUpdateDate;
    private final int ttl;
    private final String category;
    private final List<FeedEntry> entries;

    public FeedImpl(String title, String link, String description, Image image, Date publishDate, Date lastUpdateDate, int ttl, String category, List<FeedEntry> entries) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
        this.publishDate = publishDate;
        this.lastUpdateDate = lastUpdateDate;
        this.ttl = ttl;
        this.category = category;
        this.entries = entries;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Date getPublishDate() {
        return publishDate;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public int getTtl() {
        return ttl;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public List<FeedEntry> getEntries() {
        return entries;
    }
}
