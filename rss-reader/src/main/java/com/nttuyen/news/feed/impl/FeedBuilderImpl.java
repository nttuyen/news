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
import java.util.LinkedList;
import java.util.List;

import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;

@Deprecated
public class FeedBuilderImpl implements Feed.FeedBuilder {
    private String title;
    private String link;
    private String description;
    private Image image;
    private Date publishDate;
    private Date lastUpdateDate;
    private int ttl;
    private String category;
    private List<FeedEntry> entries;

    @Override
    public Feed.FeedBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Feed.FeedBuilder withLink(String linke) {
        this.link = linke;
        return this;
    }

    @Override
    public Feed.FeedBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Feed.FeedBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    @Override
    public Feed.FeedBuilder withPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    @Override
    public Feed.FeedBuilder withLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    @Override
    public Feed.FeedBuilder withTtl(int ttl) {
        this.ttl = ttl;
        return this;
    }

    @Override
    public Feed.FeedBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public Feed.FeedBuilder withEntries(List<FeedEntry> entries) {
        this.entries = entries;
        return this;
    }

    @Override
    public Feed.FeedBuilder addEntry(FeedEntry entry) {
        if(this.entries == null) {
            this.entries = new LinkedList<FeedEntry>();
        }
        this.entries.add(entry);
        return this;
    }

    @Override
    public Feed.FeedBuilder addEntry(FeedEntry entry, int index) {
        if(this.entries == null) {
            this.entries = new LinkedList<FeedEntry>();
        }
        this.entries.add(index, entry);
        return this;
    }

    @Override
    public Feed.FeedBuilder removeEntry(FeedEntry entry) {
        if(this.entries == null) {
            return this;
        }
        this.entries.remove(entry);
        return this;
    }

    @Override
    public Feed.FeedBuilder removeEntryAt(int index) {
        if(this.entries == null) {
            return this;
        }
        this.entries.remove(index);
        return this;
    }

    @Override
    public Feed build() {
        return new FeedImpl(title, link, description, image, publishDate, lastUpdateDate, ttl, category, entries);
    }
}
