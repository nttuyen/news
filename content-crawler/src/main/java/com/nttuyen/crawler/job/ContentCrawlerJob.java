package com.nttuyen.crawler.job;

import com.nttuyen.http.*;
import com.nttuyen.http.decorator.AddJsonFormatDecorator;
import com.nttuyen.http.decorator.AuthenticationRequiredDecorator;
import com.nttuyen.http.decorator.MultiRequestDecorator;
import com.nttuyen.http.decorator.RandomProxyDecorator;
import com.nttuyen.http.impl.HttpClientExecutor;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author nttuyen266@gmail.com
 */
public class ContentCrawlerJob implements Job {
    private static final Logger log = Logger.getLogger(ContentCrawlerJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Request auth = new Request("http://127.0.0.1:86/administrator/index.php", Method.POST);
        auth.addParam("option", "com_login");
        auth.addParam("task", "api.login");
        auth.addParam("username", "nttuyen");
        auth.addParam("password", "adminpass");
        auth.addParam("format", "json");

        Executor rest = new HttpClientExecutor();
        rest = new AddJsonFormatDecorator().setExecutor(rest);
        rest = new AuthenticationRequiredDecorator(auth).setExecutor(rest);
        rest = new MultiRequestDecorator().setExecutor(rest);

        RandomProxyDecorator crawler = new RandomProxyDecorator();
        crawler.setExecutor(new HttpClientExecutor());
        crawler.addProxy("112.78.11.42", 808);
        crawler.addProxy("103.31.126.35", 80);
        crawler.addProxy("111.1.36.27", 81);
        crawler.addProxy("183.207.224.22", 83);
        crawler.addProxy("183.207.228.6", 8093);
        crawler.addProxy("37.239.46.26", 80);
        crawler.addProxy("183.207.228.7", 8037);
        crawler.addProxy("211.152.50.70", 80);
        //TODO: process proxy
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("proxy/proxy.txt");
        if(input != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if(parts.length == 2) {
                        int port = Integer.parseInt(parts[1]);
                        crawler.addProxy(parts[0], port);
                    }
                }
            } catch (IOException ex) {
                log.error(ex);
            }
        }


        //Get content need to crawl
        Request fetch = new Request("http://127.0.0.1:86/administrator/index.php", Method.GET);
        fetch.addParam("option", "com_contentapi");
        fetch.addParam("view", "nextcontent");
        fetch.addParam("format", "json");

        try {
            Response response = rest.execute(fetch);
            if(response.getStatusCode() != 200) {
                return;
            }
            String jsonString = response.getResponse();
            if("".equals(jsonString)) {
                return;
            }
            JSONObject json = new JSONObject(jsonString);
            String link = json.getString("originLink");
            if(link == null || "".equals(link)) {
                return;
            }
            Map<String, String> form = this.convertToFormMap(json, null, "jform");

            Request r = new Request(link, Method.GET);
            Response res = crawler.execute(r);
            if(res.getStatusCode() != 200) {
                return;
            }

            String html = res.getResponse();
            if(html == null || "".equals(html)) {
                return;
            }

            Map<String, String> map = new HashMap<>();
            map.put("#abody", "jform[fulltext]");
            map.put(".author > .name > a", "jform[metadata][author]");


            form = this.extract(html, map, form);
            //TODO: how to save form
            //Set ID
            if(!"".equals(form.get("jform[id]"))) {
                form.put("id", form.get("jform[id]"));
            }
            form.put("originLink", form.get("jform[originLink]"));

            //TODO: remove some not used key
            String intro = form.get("jform[introtext]");
            String fulltext = form.get("jform[fulltext]");
            if(fulltext == null) fulltext = "";
            String articleText = "".equals(fulltext.trim()) ? intro : intro + "<hr id=\"system-readmore\" />" + fulltext;
            form.put("jform[articletext]", articleText);
            form.remove("jform[introtext]");
            form.remove("jform[fulltext]");

            Request save = new Request("http://127.0.0.1:86/administrator/index.php", Method.POST);
            save.addParam("option", "com_content");
            save.addParam("task", "article.apply");
            save.addParam("format", "json");
            for(Map.Entry<String, String> entry : form.entrySet()) {
                save.addParam(entry.getKey(), entry.getValue());
            }

            Response re = rest.execute(save);
            System.out.println(re.getStatusCode());
        } catch (HttpException ex) {
            log.error(ex);
        }

    }

    private Map<String, String> extract(String html, Map<String, String> map, Map<String, String> content) {
        if(content == null || map == null || html == null || "".equals(html)) {
            return null;
        }
        Document doc = Jsoup.parse(html);
        Element body = doc.body();

        Elements els;
        for(Map.Entry<String, String> entry : map.entrySet()) {
            String selector = entry.getKey();
            String key = entry.getValue();

            els = body.select(selector);
            if(els != null && els.size() > 0) {
                content.put(key, els.get(0).html());
            }
        }

        return content;
    }
    private Map<String, String> convertToFormMap(JSONObject json, Map<String, String> map, String prefix) {
        if(json == null) {
            return null;
        }
        if(map == null) {
            map = new HashMap<>();
        }
        boolean hasPrefix = prefix != null && !"".equals(prefix);
        Set<String> keys = json.keySet();
        for(String key : keys) {
            String mapKey = hasPrefix ? prefix + "[" + key + "]" : key;
            try {
                JSONObject j = json.getJSONObject(key);
                convertToFormMap(j, map, mapKey);
            } catch (Exception ex) {
                String val = "";
                try {
                    val = json.getString(key);
                } catch (Exception e) {
                    log.error(e);
                }
                map.put(mapKey, val);
            }
        }
        return map;
    }

}
