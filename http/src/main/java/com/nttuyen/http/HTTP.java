package com.nttuyen.http;

import com.nttuyen.http.decorator.*;
import com.nttuyen.http.impl.HttpClientExecutor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author nttuyen266@gmail.com
 */
public class HTTP {
    private static final Config config = ConfigFactory.load();

    public static Executor createDefault() {
        Executor executor = new HttpClientExecutor();
        return executor;
    }

    public static Request createRequest(Config config) {
        String url = config.getString("url");
        String method = config.getString("method").toUpperCase();

        Request req = new Request(url, Method.valueOf(method));

        //. Params
        Config params = config.getConfig("params");
        params.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            String val = params.getString(key);
            req.addParam(key, val);
        });

        //. Header
        Config headers = config.getConfig("headers");
        headers.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            String val = params.getString(key);
            req.addHeader(key, val);
        });

        return req;
    }

    public static Request createAuthenticationRequest() {
        Config authConfig = config.getConfig("news.http.requests.authentication");
        return createRequest(authConfig);
    }

    public static Executor createCrawler() {
        return createCrawler(new HashMap<>());
    }

    public static Executor createCrawler(Map<String, Integer> proxies) {
        RandomProxyDecorator exe = new RandomProxyDecorator();
        exe.setExecutor(new HttpClientExecutor());
        if(proxies != null) {
            proxies.forEach(exe::addProxy);
        }
        return new GoogleBotUserAgentDecorator().setExecutor(exe);
    }

    public static Executor createRestExecutor() {
        Executor exe = new HttpClientExecutor();
        exe = new AddJsonFormatDecorator().setExecutor(exe);
        exe = new AuthenticationRequiredDecorator(createAuthenticationRequest()).setExecutor(exe);
        exe = new MultiRequestDecorator().setExecutor(exe);
        return exe;
    }
}
