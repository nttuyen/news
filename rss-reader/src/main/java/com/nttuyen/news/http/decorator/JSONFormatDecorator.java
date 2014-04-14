package com.nttuyen.news.http.decorator;

import com.nttuyen.http.HttpCallback;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.HttpExecutorDecorator;
import com.nttuyen.http.HttpRequest;

/**
 * @author nttuyen266@gmail.com
 */
public class JSONFormatDecorator extends HttpExecutorDecorator {
    @Override
    public void execute(HttpRequest command, HttpCallback callback) throws HttpException {
        if(command.getParams() == null || !"json".equals(command.getParams().get("format"))) {
            command.addParam("json", "json");
        }
        executor.execute(command, callback);
    }
}
