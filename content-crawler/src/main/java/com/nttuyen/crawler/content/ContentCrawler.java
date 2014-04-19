package com.nttuyen.crawler.content;

import com.nttuyen.content.api.ContentServiceException;
import com.nttuyen.content.api.ContentServices;
import com.nttuyen.content.entity.Article;
import com.nttuyen.http.*;
import com.nttuyen.http.decorator.RandomProxyDecorator;
import com.nttuyen.http.impl.HttpClientExecutor;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @author nttuyen266@gmail.com
 */
public class ContentCrawler {
    private static final Logger log = Logger.getLogger(ContentCrawler.class);
    private static final JSONObject config;
    private static final JexlEngine jexl = new JexlEngine();
    static {
        jexl.setCache(512);
        jexl.setLenient(false);
        jexl.setSilent(false);

        StringBuilder conf = new StringBuilder();
        try {
            InputStream input = ContentCrawler.class.getClassLoader().getResourceAsStream("conf/content-crawler.json");
            if(input != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line;
                while((line = reader.readLine()) != null) {
                    conf.append(line);
                }
                reader.close();
            }

        } catch (Exception ex) {

        }
        if(conf.length() == 0) {
            config = new JSONObject();
        } else {
            config = new JSONObject(conf.toString());
        }
    }

    private final ContentServices contentServices;
    private final Executor http;

    public ContentCrawler(ContentServices contentServices) {
        this.contentServices = contentServices;

        RandomProxyDecorator crawler = new RandomProxyDecorator();
        crawler.setExecutor(new HttpClientExecutor());
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
        this.http = crawler;
    }

    public void executeCrawl() {
        Article next = contentServices.nextCrawledArticle();
        if(next == null) {
            return;
        }
        Article crawled = this.crawl(next);
        if(crawled != null) {
            this.persist(crawled);
        } else {
            log.error("Crawl content from link: " + next.getOriginLink() + " failed");
        }
    }

    protected Article crawl(Article article) {
        String originLink = article.getOriginLink();
        if(originLink == null || "".equals(originLink)) {
            return null;
        }

        JSONObject conf = this.getConfig(originLink);
        if(conf == null) {
            return null;
        }
        String rootURL = this.getRootURL(originLink);

        Request request = new Request(originLink, Method.GET);
        try {
            Response response = http.execute(request);
            String html = response.getResponse();
            Document doc = Jsoup.parse(html);
            JSONObject json;
            //. extract fulltext
            json = conf.getJSONObject("content");
            String content = this.extractHtml(doc, json.getString("selector"), json.getString("filter"));
            if(content != null && !"".equals(content)) {
                article.setFulltext(content);
            }

            //. author
            json = conf.getJSONObject("author");
            String author = this.extractHtml(doc, json.getString("selector"), json.getString("filter"));
            if(author != null && !"".equals(author)) {
                article.setAuthor(author);
            }

            //. Full image
            json = conf.getJSONObject("image");
            String image = this.extractAttr(doc, json.getString("selector"), json.getString("attr"));
            if(image != null && !"".equals(image)) {
                if(!image.startsWith("http://") && !image.startsWith("https://")) {
                    image = rootURL + image;
                }
                article.setFullImage(image);
            }

            return article;

        } catch (HttpException ex) {
            log.error(ex);
        }

        return null;
    }

    protected String getRootURL(String url) {
        String protocol = url.startsWith("https://") ? "https://" : "https://";
        url = url.replace(protocol, "");
        int index = url.indexOf('/');
        if(index < 0) {
            return protocol + url;
        }
        String domain = url.substring(0, index);
        return protocol + domain;
    }

    protected JSONObject getConfig(String url) {
        url = url.replace("http://", "");
        url = url.replace("https://", "");

        try {
            JSONObject crawlerConfig = config.getJSONObject("crawler");
            Set<String> keys = crawlerConfig.keySet();
            String match = "";
            for(String key : keys) {
                if(("*".equals(key) || url.startsWith(key)) && key.length() > match.length()) {
                    match = key;
                }
            }
            if("".equals(match)) {
                return null;
            } else {
                return crawlerConfig.getJSONObject(match);
            }
        } catch (JSONException ex) {
            log.error(ex);
            return null;
        }
    }

    /**
     * expression is: .select("#id_of_dom")
     *                  .select("expression_to_remove").remove()
     *                  .select("expression_to_remove").remove()
     *                  .select("expression_to_remove").remove()
     * @param doc
     * @param select
     * @param filterExpression
     * @return
     */
    public String extractHtml(Document doc, String select, String filterExpression) {
        //TODO: how to compile expression?
        Elements els = doc.select(select);
        if(els == null || els.size() == 0) {
            return "";
        }
        ElementWraper root = new ElementWraper(els.first());

        if(filterExpression != null && !"".equals(filterExpression)) {
            try {
                Expression exp = jexl.createExpression(filterExpression);
                JexlContext context = new MapContext();
                context.set("root", root);
                exp.evaluate(context);
            } catch (Exception ex) {
                //TODO: should return EMPTY here?
                log.error(ex);
                return "";
            }
        }

        return root.html();
    }
    public String extractAttr(Document doc, String selector, String attr) {
        Elements els = doc.select(selector);
        if(els == null || els.size() == 0) {
            return "";
        }
        Element root = els.first();
        return root.attr(attr);
    }

    protected void persist(Article article) {
        try {
            contentServices.save(article);
        } catch (ContentServiceException ex) {
            log.error(ex);
        }
    }

    public static class ElementWraper {
        private final Element element;

        public ElementWraper(Element el) {
            this.element = el;
        }

        public ElementWraper remove(String expression) {
            this.element.select(expression).remove();
            return this;
        }

        public String html() {
            return element.html();
        }
    }
}
