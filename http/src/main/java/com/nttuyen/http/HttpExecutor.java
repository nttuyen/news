package com.nttuyen.http;

/**
 * @author nttuyen266@gmail.com
 */
public interface HttpExecutor {
    void execute(HttpRequest command, HttpCallback callback) throws HttpException;
}
