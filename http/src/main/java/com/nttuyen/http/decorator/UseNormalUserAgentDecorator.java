package com.nttuyen.http.decorator;

import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;

/**
 * @author nttuyen266@gmail.com
 */
public class UseNormalUserAgentDecorator extends ExecutorDecorator {
    @Override
    public Response execute(Request request) throws HttpException {
        request.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
        return executor.execute(request);
    }
}
