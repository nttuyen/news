package com.nttuyen.crawler.content;

import com.nttuyen.content.api.ContentServiceException;
import com.nttuyen.content.api.ContentServices;
import com.nttuyen.content.entity.Article;
import com.nttuyen.http.*;
import com.nttuyen.http.decorator.GoogleBotUserAgentDecorator;
import com.nttuyen.http.decorator.RandomProxyDecorator;
import com.nttuyen.http.impl.HttpClientExecutor;
import com.typesafe.config.Config;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author nttuyen266@gmail.com
 */
public class ContentCrawler {
    private static final Logger log = Logger.getLogger(ContentCrawler.class);
    private static final JexlEngine jexl = new JexlEngine();
    private final Config conf;
    static {
        jexl.setCache(512);
        jexl.setLenient(false);
        jexl.setSilent(false);
    }

    private final ContentServices contentServices;
    private final Executor http;

    public ContentCrawler(ContentServices contentServices, Config conf) {
        this.contentServices = contentServices;
        this.conf = conf;

        Map<String, Integer> proxies = new HashMap<>();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("proxy/proxy.txt");
        if(input != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            try {
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if(parts.length == 2) {
                        int port = Integer.parseInt(parts[1]);
                        proxies.put(parts[0], port);
                    }
                }
            } catch (IOException ex) {
                log.error(ex);
            }
        }

        this.http = HTTP.createCrawler(proxies);
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

        Config con = this.getConfig(originLink);
        if(con == null) {
            return null;
        }
        String rootURL = this.getRootURL(originLink);

        Request request = new Request(originLink, Method.GET);
        try {
            Response response = http.execute(request);
            String html = response.getResponse();
            Document doc = Jsoup.parse(html);
            Config c;
            //. extract fulltext
            c = con.getConfig("content");
            String content = this.extract(doc, c.getString("selector"), c.getString("filter"), c.getString("return"));
            if(content != null && !"".equals(content)) {
                article.setFulltext(content);
            }

            //. author
            c = con.getConfig("author");
            String author = this.extract(doc, c.getString("selector"), c.getString("filter"), c.getString("return"));
            if(author != null && !"".equals(author)) {
                article.setAuthor(author);
            }

            //. Full image
            c = con.getConfig("image");
            String image = this.extract(doc, c.getString("selector"), c.getString("filter"), c.getString("return"));
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
        String protocol = url.startsWith("https://") ? "https://" : "http://";
        url = url.replace(protocol, "");
        int index = url.indexOf('/');
        if(index < 0) {
            return protocol + url;
        }
        String domain = url.substring(0, index);
        return protocol + domain;
    }

    protected Config getConfig(String url) {
        url = url.replace("http://", "");
        url = url.replace("https://", "");
        String matchedKey = "";
        Config crawler = conf.getConfig("news.content-crawler.crawler");
        for(Map.Entry<String, ?> entry : crawler.root().entrySet()) {
            String key = entry.getKey();
            if(url.startsWith(key) || "*".equals(key)) {
                if(matchedKey.length() < key.length()) {
                    matchedKey = key;
                }
            }
        }
        if("".equals(matchedKey)) {
            return null;
        } else {
            return crawler.getConfig(matchedKey);
        }
    }

    /**
     * expression is: .select("#id_of_dom")
     *                  .select("expression_to_remove").remove()
     *                  .select("expression_to_remove").remove()
     *                  .select("expression_to_remove").remove()
     * @param d
     * @param select
     * @param filterExpression
     * @return
     */
    public String extract(Document d, String select, String filterExpression, String ret) {
        Document doc = d.clone();
        Elements els = doc.select(select);
        if(els == null || els.size() == 0) {
            return "";
        }
        ElementWraper root = new ElementWraper(els.first());
        final String param = "root";

        if(filterExpression != null && !"".equals(filterExpression)) {
            try {
                String[] expressions = filterExpression.split(";");
                StringJoiner joiner = new StringJoiner(";");
                for(String exps : expressions) {
                    if("".equals(exps)) {
                        continue;
                    }
                    if(exps.charAt(0) == '.') {
                        joiner.add(param + exps);
                    } else {
                        joiner.add(exps);
                    }
                }

                Expression exp = jexl.createExpression(joiner.toString());
                JexlContext context = new MapContext();
                context.set(param, root);
                exp.evaluate(context);
            } catch (Exception ex) {
                log.error(ex);
                return "";
            }
        }

        if(ret != null && !"".equals(ret)) {
            //is method call
            if(ret.indexOf('(') > 0 && ret.indexOf(')') > 0) {
                if(ret.charAt(0) == '.') {
                    ret = param + ret;
                }
                //ret = "return " + ret;
                try {
                    Expression exp = jexl.createExpression(ret);
                    JexlContext context = new MapContext();
                    context.set(param, root);
                    return (String)exp.evaluate(context);
                } catch (Exception ex) {
                    log.error(ex);
                    return "";
                }
            }
        }

        return root.html();
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

        public String attr(String attributeKey) {
            return element.attr(attributeKey);
        }
    }
}
