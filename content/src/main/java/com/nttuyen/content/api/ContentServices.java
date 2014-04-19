package com.nttuyen.content.api;

import com.nttuyen.content.entity.Article;

/**
 * @author nttuyen266@gmail.com
 */
public interface ContentServices {
    Article nextCrawledArticle();
    void save(Article article) throws ContentServiceException;
}
