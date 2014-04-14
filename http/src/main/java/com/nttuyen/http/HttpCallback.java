package com.nttuyen.http;

/**
 * @author nttuyen266@gmail.com
 */
@FunctionalInterface
public interface HttpCallback {
    void handle(HttpResponse response);
}
