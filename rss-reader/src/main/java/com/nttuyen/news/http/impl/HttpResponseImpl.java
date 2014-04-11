package com.nttuyen.news.http.impl;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class HttpResponseImpl implements com.nttuyen.news.http.HttpResponse {
    private static final Logger log = Logger.getLogger(HttpResponseImpl.class);

    private final HttpResponse response;
    private Map<String, String> headers = null;
    private String content = null;

    public HttpResponseImpl(HttpResponse response) {
        if(response == null) {
            throw new IllegalArgumentException("response null is not acceptable");
        }
        this.response = response;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        //TODO: should we get content before return input stream?
        try {
            return response.getEntity().getContent();
        } catch (IllegalStateException|IOException ex) {
            log.error(ex);
            if(this.content != null) {
                return new ByteArrayInputStream(this.content.getBytes("UTF-8"));
            } else {
                throw ex;
            }
        }
    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }
    public String getStatusString() {
        return response.getStatusLine().getReasonPhrase();
    }
    public String getHeader(String name) {
        Header header = response.getFirstHeader(name);
        return header == null ? null : header.getValue();
    }
    public String[] getHeaders(String name) {
        Header[] headers = response.getHeaders(name);
        if(headers == null || headers.length == 0) {
            return new String[0];
        }
        String[] values = new String[headers.length];
        for(int i = 0; i < headers.length; i++) {
            values[i] = headers[i] == null ? null : headers[i].getValue();
        }
        return values;
    }
    public String getResponse() {
        if(this.content != null) {
            return this.content;
        }
        InputStream input = null;
        try {
            input = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            this.content = builder.toString();
        } catch (IOException | IllegalStateException ex) {
            log.error(ex);
            this.content = "";
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
        }

        return this.content;
    }
}
