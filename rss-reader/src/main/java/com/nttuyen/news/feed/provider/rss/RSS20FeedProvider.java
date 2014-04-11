package com.nttuyen.news.feed.provider.rss;

import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.FeedException;
import com.nttuyen.news.feed.FeedProvider;
import com.nttuyen.news.feed.impl.FeedBuilder;
import com.nttuyen.news.feed.impl.FeedEntryBuilder;
import com.nttuyen.news.feed.impl.ImageBuilder;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
public class RSS20FeedProvider implements FeedProvider {
    private static final Logger log = Logger.getLogger(RSS20FeedProvider.class);
    @Override
    public String[] getSupportedTypes() {
        return new String[]{
                "rss/2.0"
        };
    }

    @Override
    public Feed read(InputStream input) throws IOException, FeedException {
        FeedBuilder builder = new FeedBuilder();
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(input);
            Element root = doc.getRootElement();
            Node chanel = root.selectSingleNode("channel");
            Node node;

            //.
            node = chanel.selectSingleNode("title");
            if(node != null) {
                builder.withTitle(node.getStringValue());
            }
            //.
            node = chanel.selectSingleNode("link");
            if(node != null) {
                builder.withLink(node.getStringValue());
            }
            //.
            node = chanel.selectSingleNode("description");
            if(node != null) {
                builder.withDescription(node.getStringValue());
            }
            //.
            node = chanel.selectSingleNode("pubDate");
            if(node != null) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                    Date date = df.parse(node.getStringValue());
                    builder.withPublishDate(date);
                } catch (Exception ex) {
                    log.error(ex);
                }
            }
            //.
            node = chanel.selectSingleNode("lastBuildDate");
            if(node != null) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                    Date date = df.parse(node.getStringValue());
                    builder.withLastUpdateDate(date);
                } catch (Exception ex) {
                    log.error(ex);
                }
            }
            //.
            node = chanel.selectSingleNode("category");
            if(node != null) {
                builder.withCategory(node.getStringValue());
            }
            //.
            node = chanel.selectSingleNode("ttl");
            if(node != null) {
                int ttl = Integer.parseInt(node.getStringValue());
                builder.withTtl(ttl);
            }
            //.
            node = chanel.selectSingleNode("image");
            if(node != null) {
                ImageBuilder imgBuilder = new ImageBuilder();
                Node n = node.selectSingleNode("url");
                if(n != null) {
                    imgBuilder.withURL(n.getStringValue());
                }
                n = node.selectSingleNode("title");
                if(n != null) {
                    imgBuilder.withTitle(n.getStringValue());
                }
                n = node.selectSingleNode("link");
                if(n != null) {
                    imgBuilder.withLink(n.getStringValue());
                }
                n = node.selectSingleNode("width");
                if(n != null) {
                    int width = Integer.parseInt(n.getStringValue());
                    imgBuilder.withWidth(width);
                }
                n = node.selectSingleNode("height");
                if(n != null) {
                    int height = Integer.parseInt(n.getStringValue());
                    imgBuilder.withHeight(height);
                }
                n = node.selectSingleNode("description");
                if(n != null) {
                    imgBuilder.withDescription(n.getStringValue());
                }
                builder.withImage(imgBuilder.build());
            }

            //. Entries
            List<Node> entryNodes = chanel.selectNodes("item");
            for(Node n : entryNodes) {
                FeedEntry entry = this.buildEntry(n);
                if(entry != null) {
                    builder.addEntry(entry);
                }
            }

            return builder.build();

        } catch (DocumentException e) {
            log.error(e);
            throw new FeedException(e);
        }
    }

    protected FeedEntry buildEntry(Node entry) {
        FeedEntryBuilder builder = new FeedEntryBuilder();
        Node node;
        //.
        node = entry.selectSingleNode("title");
        if(node != null) {
            builder.withTitle(node.getStringValue());
        }
        //.
        node = entry.selectSingleNode("link");
        if(node != null) {
            builder.withLink(node.getStringValue());
        }
        //.
        node = entry.selectSingleNode("description");
        if(node != null) {
            builder.withDescription(node.getStringValue());
        }
        //.
        node = entry.selectSingleNode("author");
        if(node != null) {
            builder.withAuthor(node.getStringValue());
        }
        //.
        node = entry.selectSingleNode("category");
        if(node != null) {
            builder.withCategory(node.getStringValue());
        }
        //.
        node = entry.selectSingleNode("pubDate");
        if(node != null) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                Date date = df.parse(node.getStringValue());
                builder.withPublishDate(date);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

        return builder.build();
    }
}
