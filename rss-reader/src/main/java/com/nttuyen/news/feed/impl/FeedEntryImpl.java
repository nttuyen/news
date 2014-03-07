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

public class FeedEntryImpl implements FeedEntry {
    private final String title;
    private final String link;
    private final String description;
    private final Image image;
    private final String author;
    private final Date publishDate;
    private final String category;

    public FeedEntryImpl(String title, String link, String description, Image image, String author, Date publishDate, String category) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
        this.author = author;
        this.publishDate = publishDate;
        this.category = category;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public Date getPublishDate() {
        return this.publishDate;
    }

    @Override
    public String getCategory() {
        return this.category;
    }
}
