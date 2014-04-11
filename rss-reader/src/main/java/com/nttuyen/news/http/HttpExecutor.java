package com.nttuyen.news.http;

/**
 * @author nttuyen266@gmail.com
 */
public interface HttpExecutor {
    void execute(HttpRequest command, HttpCallback callback) throws HttpException;
}
