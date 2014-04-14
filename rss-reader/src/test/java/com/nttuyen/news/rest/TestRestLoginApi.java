package com.nttuyen.news.rest;

import com.nttuyen.http.HttpException;
import com.nttuyen.http.HttpExecutor;
import com.nttuyen.http.HttpExecutorFactory;
import com.nttuyen.http.HttpRequest;
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
    private HttpExecutor executor;

    @Before
    public void before() {
        executor = HttpExecutorFactory.createDefault();
    }

    @Test
    public void testLogin() {
        final String url = Consts.REST_ROOT_URL;
        HttpRequest request = new HttpRequest(url, HttpRequest.Method.POST);
        request.addParam("option", "com_login");
        request.addParam("task", "api.login");
        //request.addParam("format", "json");
        request.addParam("username", "crawler");
        request.addParam("password", "123456");
        try {
            executor.execute(request, (response) -> {
                Assert.assertEquals(200, response.getStatusCode());
            });
        } catch (HttpException e) {
            log.error(e);
            Assert.fail("Exception when try login");
        }
    }
}
