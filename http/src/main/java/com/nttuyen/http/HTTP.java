package com.nttuyen.http;

import com.nttuyen.http.decorator.*;
import com.nttuyen.http.impl.HttpClientExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author nttuyen266@gmail.com
 */
public class HTTP {
    public static Executor createDefault() {
        Executor executor = new HttpClientExecutor();
        return executor;
    }

    public static Request createAuthenticationRequest() {
        Request auth = new Request("http://127.0.0.1:86/administrator/index.php", Method.POST);
        auth.addParam("option", "com_login");
        auth.addParam("task", "api.login");
        auth.addParam("username", "crawler");
        auth.addParam("password", "123456");
        auth.addParam("format", "json");

        return auth;
    }

    public static Executor createCrawler() {
        return createCrawler(new HashMap<String, Integer>());
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
