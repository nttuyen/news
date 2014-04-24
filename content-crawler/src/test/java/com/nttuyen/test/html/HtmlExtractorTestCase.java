package com.nttuyen.test.html;

import com.nttuyen.crawler.content.ContentCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

/**
 * @author nttuyen266@gmail.com
 */
public class HtmlExtractorTestCase {
    @Test
    public void testExtractorHtml() {
        String html = "<html><head><title>test</title></head><body><div link=\"This is link\">Content here <p>need remove this</p><span>need remove this too</span><div>remove sub div</div></div></body>></html>";
        Document doc = Jsoup.parse(html);
        ContentCrawler crawler = new ContentCrawler(null, null);
        String select = "div";
        String filter = ".remove(\"p\").remove(\"span\").remove(\"div\")";
        String result = crawler.extract(doc, select, filter, ".html()");
        String link = crawler.extract(doc, select, filter, ".attr(\"link\")");
        System.out.println(result);
        System.out.println(link);
    }
}
