package com.nttuyen.news;

import com.nttuyen.news.scheduler.FeedReaderJob;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) throws SchedulerException, IOException, URISyntaxException {
        log.debug("Starting application");

        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        URL[] urls = getAllFiles();
        for(URL url: urls) {
            String jsonText = readInputStream(url.openStream());
            JSONObject json = new JSONObject(jsonText);
            log.debug("JSONObject: " + json.toString());

            JSONArray jobs = json.getJSONArray("jobs");
            for(int i = 0; i < jobs.length(); i++) {
                JSONObject js = jobs.getJSONObject(i);
                String name = js.getString("name");
                int timeInterval = js.getInt("timeInterval");
                String feedRootType = js.getString("feedRootType");
                JSONArray linkJsons = js.getJSONArray("links");

                JobDetail job = JobBuilder
                        .newJob(FeedReaderJob.class)
                        .withIdentity(name, "FEED_READER_JOB_GROUP")
                        .usingJobData("name", name)
                        .usingJobData("feedRootType", feedRootType)
                        .usingJobData("links", linkJsons.toString())
                        .build();

                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(name, "FEED_READER_TRIGGER_GROUP")
                        .startAt(DateBuilder.futureDate(timeInterval, DateBuilder.IntervalUnit.MINUTE))
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timeInterval).repeatForever())
                        .forJob(job)
                        .build();

                scheduler.scheduleJob(job, trigger);
            }
        }

        log.debug("Application started");
    }

    private static URL[] getAllFiles() throws IOException, URISyntaxException {
        String path = "jobs";
        Enumeration<URL> urls = Main.class.getClassLoader().getResources(path);
        Set<URL> result = new HashSet<URL>();

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            log.debug("URL: " + url);

            if("file".equals(url.getProtocol())) {
                Queue<File> queue = new LinkedList<File>();
                queue.add(new File(url.toURI()));
                File f = null;
                while ((f = queue.poll()) != null) {
                    File[] files = f.listFiles();
                    for(File file : files) {
                        if(file.isFile()) {
                            String fullPath = url.toString() + "/" + file.getPath().replace(url.getPath(), "");
                            log.debug("Found file at: " + fullPath);
                            result.add(new URL(fullPath));
                        } else {
                            queue.add(file);
                        }
                    }
                }

            } else if("jar".equals(url.getProtocol())) {
                String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
                String jarURL = url.getPath().substring(0, url.getPath().indexOf("!") + 1);
                JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
                Enumeration<JarEntry> entries = jar.entries();
                while(entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(path) && !name.endsWith("/")) {
                        String fullPath = "jar:file:" + jarPath + "!/" + name;
                        log.debug("Found .json file at: " + fullPath);
                        result.add(new URL(fullPath));
                    }
                }
            }
        }
        return result.toArray(new URL[result.size()]);
    }

    private static String readInputStream(InputStream input) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }

}
