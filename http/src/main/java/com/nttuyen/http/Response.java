package com.nttuyen.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author nttuyen266@gmail.com
 */
public interface Response {
    InputStream getInputStream() throws IOException;
    int getStatusCode();
    String getStatusString();
    String getHeader(String name);
    String[] getHeaders(String name);
    String getResponse();
}
