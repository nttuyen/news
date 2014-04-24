package com.nttuyen.test.http.config;

import com.nttuyen.http.HTTP;
import com.nttuyen.http.Request;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

public class TypeSafeConfigTestCase {
    @Test
    public void test() {
        Config config = ConfigFactory.load();
        Config auth = config.getConfig("news.http.requests.authentication");
        System.out.println(auth.getString("url"));
        Config params = auth.getConfig("params");
        params.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            String val = params.getString(key);
            System.out.println(key + " = " + val);
        });
    }

    @Test
    public void testCreateAuthRequest() {
        Request req = HTTP.createAuthenticationRequest();
        System.out.println(req);
    }
}
