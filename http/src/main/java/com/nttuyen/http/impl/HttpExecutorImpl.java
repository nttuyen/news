package com.nttuyen.http.impl;

import com.nttuyen.common.Callback;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.HttpExecutor;
import com.nttuyen.http.HttpRequest;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author nttuyen266@gmail.com
 */
public class HttpExecutorImpl implements HttpExecutor {
    private static final Logger log = Logger.getLogger(HttpExecutorImpl.class);
    private final CloseableHttpClient httpClient;

    public HttpExecutorImpl() {
        httpClient = HttpClients.createDefault();
    }

    @Override
    public void execute(HttpRequest request, Callback callback) throws HttpException {
        if(request.url == null) {
            log.error("Request URL must not be null");
            return;
        }

        if(request.method == HttpRequest.Method.POST) {
            post(request, callback);
        } else {
            get(request, callback);
        }
    }

    public void get(HttpRequest request, Callback callback) {
        Map<String, String> map = request.getParams();
        StringJoiner params = new StringJoiner("&");
        if(map != null && map.size() > 0) {
            for(Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getKey() != null) {
                    params.add(entry.getKey() + "=" + String.valueOf(entry.getValue()));
                }
            }
        }
        String url = request.url + (request.url.indexOf('?') != -1 ? "&" : "?") + params.toString();
        HttpGet get = new HttpGet(url);
        try(CloseableHttpResponse response = httpClient.execute(get)) {
            if(callback != null) {
                callback.callback(new HttpResponseImpl(response));
            }
        } catch (IOException ex) {
            log.error(ex);
        }
    }
    public void post(HttpRequest request, Callback callback) throws HttpException {
        List<NameValuePair> params = new LinkedList<>();
        Map<String, String> map = request.getParams();
        if(map != null && map.size() > 0) {
            for(Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getKey() != null) {
                    params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        HttpPost post = new HttpPost(request.url);
        post.setEntity(entity);

        try {
            try(CloseableHttpResponse response = httpClient.execute(post)) {
                if(callback != null) {
                    callback.callback(new HttpResponseImpl(response));
                }
            }
        } catch (IOException e) {
            log.error("IOException", e);
            throw new HttpException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        httpClient.close();
        super.finalize();
    }
}
