package com.nttuyen.http.impl;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import com.nttuyen.http.Executor;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Method;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class HttpClientExecutor implements Executor {
    private static final Logger log = Logger.getLogger(HttpClientExecutor.class);
    private HttpClient client;
    private final HttpClientBuilder builder;

    public HttpClientExecutor() {
        this.builder = HttpClients.custom();
    }

    public void setHttpClient(HttpClient client) {
        this.client = client;
    }

    public HttpClientBuilder getBuilder() {
        return this.builder;
    }

    public HttpClient getHttpClient() {
        if (this.client == null) {
            synchronized (this) {
                if (this.client == null) {
                    this.client = builder.build();
                }
            }
        }
        return this.client;
    }

    @Override
    public Response execute(Request request) throws HttpException {
        try {
            HttpUriRequest req = this.createHttpRequest(request);
            if (req == null) {
                log.error("Can not create HTTP request for method: " + request.method);
                throw new HttpException("We do not support method: " + request.method);
            }

            //TODO: whe should call #setHeader() or #addHeader()?
            Map<String, String> headers = request.getHeaders();
            if(headers != null && headers.size() > 0) {
                headers.forEach((name, value) -> req.setHeader(name, value));
            }

            return new ResponseImpl(getHttpClient().execute(req));
        } catch (IOException ex) {
            log.error(ex);
            throw new HttpException(ex);
        }
    }

    public HttpUriRequest createHttpRequest(Request request) {
        if (request.method == Method.GET) {
            Map<String, String> map = request.getParams();
            StringJoiner params = new StringJoiner("&");
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey() != null) {
                        params.add(entry.getKey() + "=" + String.valueOf(entry.getValue()));
                    }
                }
            }
            String url = request.url + (request.url.indexOf('?') != -1 ? "&" : "?") + params.toString();
            HttpGet get = new HttpGet(url);
            return get;
        } else if (request.method == Method.POST) {
            List<NameValuePair> params = new LinkedList<>();
            Map<String, String> map = request.getParams();
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey() != null) {
                        params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                    }
                }
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
            HttpPost post = new HttpPost(request.url);
            post.setEntity(entity);
            return post;
        }
        return null;
    }
}
