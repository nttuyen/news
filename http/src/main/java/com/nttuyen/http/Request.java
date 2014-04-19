package com.nttuyen.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class Request {
  public final Method method;
    public final String url;
    private Map<String, String> params;
    private Map<String, String> headers;

    public Request(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    public void addParam(String name, Object value) {
        if(params == null) {
            params = new HashMap<>();
        }
        params.put(name, String.valueOf(value));
    }
    public void addHeader(String name, Object value) {
        if(headers == null) {
            headers = new HashMap<>();
        }
        headers.put(name, String.valueOf(value));
    }

    public Map<String, String> getParams() {
        return this.params;
    }
    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
