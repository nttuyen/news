package com.nttuyen.news.rest;

import java.util.HashMap;
import java.util.Map;

import com.nttuyen.common.Callback;
import com.nttuyen.http.Executor;
import com.nttuyen.http.HTTP;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Method;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.news.Consts;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author nttuyen266@gmail.com
 */
public class TestRestLoginApi {
    private static final Logger log = Logger.getLogger(TestRestLoginApi.class);
    private Executor executor;

    @Before
    public void before() {
        executor = HTTP.createDefault();
    }

    @Test
    public void testLogin() {
        final String url = Consts.REST_ROOT_URL;
        Request request = new Request(url, Method.POST);
        request.addParam("option", "com_login");
        request.addParam("task", "api.login");
        //request.addParam("format", "json");
        request.addParam("username", "crawler");
        request.addParam("password", "123456");
        try {
            Response response = executor.execute(request);
            Assert.assertEquals(200, response.getStatusCode());
        } catch (HttpException e) {
            log.error(e);
            Assert.fail("Exception when try login");
        }
    }
}
