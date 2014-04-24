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

import java.util.Set;

import com.nttuyen.content.api.impl.RestContentServices;
import com.nttuyen.http.Executor;
import com.nttuyen.http.HTTP;
import com.nttuyen.http.Method;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedReader;
import com.nttuyen.news.persistence.FeedPersistence;
import com.nttuyen.news.persistence.impl.FeedPersistenceImpl;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FeedReaderJob implements Job {
    private static final Logger log = Logger.getLogger(FeedReaderJob.class);

    public static final String KEY_MAX_TRY = "max-try";
    public static final String KEY_SLEEP_TIME = "sleep-time";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LINKS = "links";
    public static final String KEY_DEFAULT_CATEGORY_PREFIX = "category#";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getMergedJobDataMap();

        Set<String> links = (Set<String>)data.get(KEY_LINKS);
        links.forEach((link) -> read(link, data));
    }

    protected void read(String rss, JobDataMap data) {
        final int MAX_TRY = data.getInt(KEY_MAX_TRY);
        final int SLEEP_TIME = data.getInt(KEY_SLEEP_TIME);
        final String type = data.getString(KEY_TYPE);

        FeedReader reader = new FeedReader();
        Executor http = HTTP.createCrawler();
        FeedPersistence persistence = new FeedPersistenceImpl(new RestContentServices());
        Request request = new Request(rss, Method.GET);

        try {
            int tried = 0;
            Response response;
            do {
                if(tried > 0) {
                    Thread.sleep(SLEEP_TIME);
                }
                tried++;
                response = http.execute(request);
            } while (response.getStatusCode() != 200 && tried <= MAX_TRY);

            if(response.getStatusCode() == 200) {
                StringBuilder sb = new StringBuilder(type);
                sb.append(rss.substring(rss.indexOf('/') + 1));
                Feed feed = reader.read(response.getInputStream(), type.toString());
                persistence.persist(feed);
            } else {
                log.error("Read RSS from link: " + rss + " failed!");
            }
        } catch (Exception ex) {
            log.warn(ex);
        }
    }
}
