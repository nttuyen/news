package com.nttuyen.news.feed;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author nttuyen266@gmail.com
 */
public interface FeedProvider {
	String[] getSupportedTypes();
	Feed read(InputStream input) throws IOException, FeedException;
}
