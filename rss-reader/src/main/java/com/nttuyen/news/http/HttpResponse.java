package com.nttuyen.news.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author nttuyen266@gmail.com
 */
public interface HttpResponse {
    InputStream getInputStream() throws IOException;
    int getStatusCode();
    String getStatusString();
    String getHeader(String name);
    String getResponse();
}
