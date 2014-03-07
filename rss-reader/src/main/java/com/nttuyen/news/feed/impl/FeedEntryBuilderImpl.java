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

import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;

public class FeedEntryBuilderImpl implements FeedEntry.FeedEntryBuilder {
    private String title;
    private String link;
    private String description;
    private Image image;
    private String author;
    private Date publishDate;
    private String category;

    @Override
    public FeedEntry.FeedEntryBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public FeedEntry.FeedEntryBuilder withLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public FeedEntry.FeedEntryBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public FeedEntry.FeedEntryBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    @Override
    public FeedEntry.FeedEntryBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public FeedEntry.FeedEntryBuilder withPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    @Override
    public FeedEntry.FeedEntryBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public FeedEntry build() {
        return new FeedEntryImpl(title, link, description, image, author, publishDate, category);
    }
}
