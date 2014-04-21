package com.nttuyen.news.http.decorator;

import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;

/**
 * @author nttuyen266@gmail.com
 */
@Deprecated
public class JSONFormatDecorator extends ExecutorDecorator {
    @Override
    public Response execute(Request request) throws HttpException {
        if(request.getParams() == null || !"json".equals(request.getParams().get("format"))) {
            request.addParam("json", "json");
        }
        return executor.execute(request);
    }
}
