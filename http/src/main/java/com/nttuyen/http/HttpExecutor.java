package com.nttuyen.http;

import com.nttuyen.common.Callback;

/**
 * @author nttuyen266@gmail.com
 */
public interface HttpExecutor {
    void execute(HttpRequest command, Callback callback) throws HttpException;
}
