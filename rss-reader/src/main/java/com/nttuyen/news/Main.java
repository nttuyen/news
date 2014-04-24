package com.nttuyen.news;

import com.nttuyen.news.scheduler.FeedReaderJob;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) throws SchedulerException, IOException, URISyntaxException {
        log.debug("Starting application");

        Config config = ConfigFactory.load().getConfig("news.feed-reader");
        final int maxTry = config.getInt("max-try");
        final int sleepTime = config.getInt("sleep-time");

        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        Config jobs = config.getConfig("jobs");
        jobs.root().entrySet().forEach((entry) -> {
            String key = entry.getKey();
            Config conf = jobs.getConfig(key);
            try {
                startJob(scheduler, conf, maxTry, sleepTime);
            } catch (SchedulerException ex) {
                log.error(ex);
            }
        });

        log.debug("Application started");
    }

    private static void startJob(Scheduler scheduler, Config config, int maxTry, int sleepTime) throws SchedulerException {
        final String name = config.getString("name");
        final String type = config.getString("type");
        final int frequence = config.getInt("frequence");
        Config feed = config.getConfig("feeds");

        JobDataMap data = new JobDataMap();
        data.put(FeedReaderJob.KEY_MAX_TRY, maxTry);
        data.put(FeedReaderJob.KEY_SLEEP_TIME, sleepTime);
        data.put(FeedReaderJob.KEY_NAME, name);
        data.put(FeedReaderJob.KEY_TYPE, type);

        Set<String> links = new HashSet<>();
        feed.root().entrySet().forEach((entry) -> {
            String key = entry.getKey();
            Config conf = feed.getConfig(key);
            String link = conf.getString("link");
            int category = conf.getInt("category");
            links.add(link);
            data.put(FeedReaderJob.KEY_DEFAULT_CATEGORY_PREFIX + link, category);
        });
        if(links.size() == 0) {
            return;
        }
        data.put(FeedReaderJob.KEY_LINKS, links);

        JobDetail job = JobBuilder
                .newJob(FeedReaderJob.class)
                .withIdentity(name, "FEED_READER_JOB_GROUP")
                .usingJobData(data)
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(name, "FEED_READER_TRIGGER_GROUP")
                .startAt(DateBuilder.futureDate(frequence, DateBuilder.IntervalUnit.MINUTE))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(frequence).repeatForever())
                .forJob(job)
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
