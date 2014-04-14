package com.nttuyen.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nttuyen266@gmail.com
 */
public class HttpRequest {
    public static enum Method {
        GET, POST
    }

    public final String url;
    public final Method method;
    private Map<String, String> params;

    public HttpRequest(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    public void addParam(String name, String value) {
        if(params == null) {
            params = new HashMap<>();
        }
        params.put(name, value);
    }

    public Map<String, String> getParams() {
        return this.params;
    }
}
