package com.nttuyen.http;

/**
 * @author nttuyen266@gmail.com
 */
public interface Executor {
    Response execute(Request request) throws HttpException;
}
