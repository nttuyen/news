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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedException;
import com.nttuyen.news.feed.FeedReader;

public class FeedReaderImpl implements FeedReader {
    @Override
    public Feed parse(InputStream input) throws IOException, FeedException {
        return this.parse(input, new FeedBuilderImpl());
    }

    @Override
    public Feed parse(InputStream input, Feed.FeedBuilder builder) throws IOException, FeedException {
        return null;
    }

    @Override
    public Feed parse(String link) throws IOException, FeedException {
        return this.parse(new URL(link).openStream());
    }
}
