package com.nttuyen.http.impl;

import com.nttuyen.http.Response;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class ResponseImpl implements Response {
    private static final Logger log = Logger.getLogger(ResponseImpl.class);

    private final HttpResponse response;
    private BufferedHttpEntity entity;
    private Map<String, String> headers = null;
    private String content = null;

    public ResponseImpl(HttpResponse response) {
        if(response == null) {
            throw new IllegalArgumentException("response null is not acceptable");
        }
        this.response = response;
        try {
            this.entity = new BufferedHttpEntity(this.response.getEntity());
        } catch (IOException ex) {
            log.error(ex);
            this.entity = null;
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.entity == null ? null : this.entity.getContent();
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
        if(this.entity == null) {
          this.content = "";
        } else {
          try {
          this.content = EntityUtils.toString(this.entity);
          } catch (IOException ex) {
            log.error(ex);
            this.content = "";
          }
        }
        return this.content;
    }
}
