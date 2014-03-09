package com.nttuyen.news.feed;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author nttuyen266@gmail.com
 */
public interface FeedProvider {
	//TODO: what is name for this method
	String[] getSupportedTypes();
	Feed read(InputStream input) throws IOException, FeedException;
}
