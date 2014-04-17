package com.nttuyen.crawler;

import com.nttuyen.crawler.job.ContentCrawlerJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author nttuyen266@gmail.com
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder
                .newJob(ContentCrawlerJob.class)
                .withIdentity("CONTENT_CRAWLER", "CONTENT_CRAWLER_JOB_GROUP")
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("CONTENT_CRAWLER", "CONTENT_CRAWLER_TRIGGER_GROUP")
                .startAt(DateBuilder.futureDate(20, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(20).repeatForever())
                .forJob(job)
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
