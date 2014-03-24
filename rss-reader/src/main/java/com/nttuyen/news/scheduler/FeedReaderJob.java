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
package com.nttuyen.news.scheduler;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.FeedReader;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FeedReaderJob implements Job {
    private static final Logger log = Logger.getLogger(FeedReaderJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getMergedJobDataMap();
        String[] links = data.getString("links").split("###");
        String feedRootType = data.getString("feedRootType");
        for(String link : links) {
            try {
                URL url = new URL(link);
                InputStream input = url.openStream();
                FeedReader reader = new FeedReader();

                StringBuilder type = new StringBuilder(feedRootType);
                type.append(link.substring(link.indexOf('/') + 1));
                log.debug("Feed type: " + type.toString());

                Feed feed = reader.read(input, type.toString());
                log.debug("Read Feed: " + feed.getLink());
                List<FeedEntry> entries = feed.getEntries();
                log.debug("No Entry: " + entries.size());

            } catch (Exception e) {
                log.error("Exception", e);
            }
        }
    }
}
