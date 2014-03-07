package com.nttuyen.news.rss;

import java.util.List;

/**
 * @author nttuyen266@gmail.com
 */
@Deprecated
public interface FeedReader {
	NewsChanel read(String url) throws FeedReaderException;
}
