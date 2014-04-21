package com.nttuyen.news.persistence.impl;

import com.nttuyen.common.Callback;
import com.nttuyen.content.api.ContentServiceException;
import com.nttuyen.content.api.ContentServices;
import com.nttuyen.content.entity.Article;
import com.nttuyen.http.Executor;
import com.nttuyen.http.HTTP;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Method;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.news.Consts;
import com.nttuyen.news.feed.Feed;
import com.nttuyen.news.feed.FeedEntry;
import com.nttuyen.news.feed.Image;
import com.nttuyen.news.persistence.FeedPersistence;
import com.nttuyen.news.persistence.FeedPersistenceException;
import com.nttuyen.news.util.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class FeedPersistenceImpl implements FeedPersistence {
    private static final Logger log = Logger.getLogger(FeedPersistenceImpl.class);

    private final ContentServices contentServices;

    public FeedPersistenceImpl(ContentServices contentServices) {
        this.contentServices = contentServices;
    }

	@Override
	public void persist(Feed feed) throws FeedPersistenceException {
        if(feed.getEntries() != null) {
            feed.getEntries().forEach(this::persist);
        }
	}

    protected void persist(FeedEntry entry) {
        Article article = new Article();
        article.setTitle(entry.getTitle());
        article.setIntro(entry.getDescription());
        article.setAuthor(entry.getAuthor());
        article.setOriginLink(entry.getLink());
        article.setPublishDate(entry.getPublishDate());
        if(entry.getImage() != null) {
            article.setIntroImage(entry.getImage().url);
        }
        try {
            contentServices.save(article);
        } catch (ContentServiceException ex) {
            log.error(ex);
        }
    }
}
