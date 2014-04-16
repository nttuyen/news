package com.nttuyen.http;

import com.nttuyen.http.impl.HttpClientExecutor;

import java.util.ServiceLoader;

/**
 * @author nttuyen266@gmail.com
 */
public class HTTP {
    public static Executor createDefault() {
        Executor executor = new HttpClientExecutor();
        ServiceLoader<ExecutorDecorator> serviceLoader = ServiceLoader.load(ExecutorDecorator.class);
        for(ExecutorDecorator decorator : serviceLoader) {
            decorator.setExecutor(executor);
            executor = decorator;
        }
        return executor;
    }
}
