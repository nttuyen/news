package com.nttuyen.content.api;

import com.nttuyen.content.entity.Content;

/**
 * @author nttuyen266@gmail.com
 */
public interface ContentApi {
    Content nextCrawledContent();
    void persist(Content content);
}
