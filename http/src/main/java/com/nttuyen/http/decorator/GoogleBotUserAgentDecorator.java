package com.nttuyen.http.decorator;

import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;

/**
 * @author nttuyen266@gmail.com
 */
public class GoogleBotUserAgentDecorator extends ExecutorDecorator {
    @Override
    public Response execute(Request request) throws HttpException {
        request.addHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
        return executor.execute(request);
    }
}
