package com.nttuyen.crawler.job;

import com.nttuyen.content.api.ContentServices;
import com.nttuyen.content.api.impl.RestContentServices;
import com.nttuyen.crawler.content.ContentCrawler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author nttuyen266@gmail.com
 */
public class ContentCrawlerJob implements Job {
    private static final Logger log = Logger.getLogger(ContentCrawlerJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Config config = ConfigFactory.load();
        ContentServices contentServices = new RestContentServices();
        ContentCrawler crawler = new ContentCrawler(contentServices, config);
        crawler.executeCrawl();
    }
}
