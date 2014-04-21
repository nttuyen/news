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

import java.io.IOException;
import java.util.ServiceLoader;

import com.nttuyen.content.api.impl.RestContentServices;
import com.nttuyen.http.*;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedException;
import com.nttuyen.news.feed.FeedReader;
import com.nttuyen.news.persistence.FeedPersistence;
import com.nttuyen.news.persistence.FeedPersistenceException;
import com.nttuyen.news.persistence.impl.FeedPersistenceImpl;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FeedReaderJob implements Job {
    private static final Logger log = Logger.getLogger(FeedReaderJob.class);

    //TODO: move these option to config
    public static final int MAX_TRY = 5;
    public static final int SLEEP_TIME = 5000;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getMergedJobDataMap();
        JSONArray links = new JSONArray(data.getString("links"));
        String feedRootType = data.getString("feedRootType");

        FeedReader reader = new FeedReader();
        Executor http = HTTP.createCrawler();
        FeedPersistence persistence = new FeedPersistenceImpl(new RestContentServices());

        for(int i = 0; i < links.length(); i++) {
            String link = links.getString(i);
            Request request = new Request(link, Method.GET);
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
                    StringBuilder type = new StringBuilder(feedRootType);
                    type.append(link.substring(link.indexOf('/') + 1));
                    Feed feed = reader.read(response.getInputStream(), type.toString());
                    persistence.persist(feed);
                } else {
                    log.error("Read RSS from link: " + link + " failed!");
                }
            } catch (HttpException
                    | IOException
                    | FeedException
                    | FeedPersistenceException
                    | InterruptedException ex) {
                log.error(ex);
            }
        }
    }
}
