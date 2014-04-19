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
        String html = "<html><head><title>test</title></head><body><div>Content here <p>need remove this</p><span>need remove this too</span><div>remove sub div</div></div></body>></html>";
        Document doc = Jsoup.parse(html);
        ContentCrawler crawler = new ContentCrawler(null);
        String select = "div";
        String filter = "root.remove(\"p\").remove(\"span\").remove(\"div\")";
        String result = crawler.extractHtml(doc, select, filter);
        System.out.println(result);
    }
}
