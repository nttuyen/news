package com.nttuyen.news.http;

import com.nttuyen.news.http.impl.HttpExecutorImpl;

import java.util.ServiceLoader;

/**
 * @author nttuyen266@gmail.com
 */
public class HttpExecutorFactory {
    public static HttpExecutor createDefault() {
        HttpExecutor executor = new HttpExecutorImpl();
        ServiceLoader<HttpExecutorDecorator> serviceLoader = ServiceLoader.load(HttpExecutorDecorator.class);
        for(HttpExecutorDecorator decorator : serviceLoader) {
            decorator.setExecutor(executor);
            executor = decorator;
        }
        return executor;
    }
}
