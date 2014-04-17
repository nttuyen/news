package com.nttuyen.http.decorator;

import java.util.ArrayList;
import java.util.List;
import com.nttuyen.http.Executor;
import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.http.impl.HttpClientExecutor;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;

public class RandomProxyDecorator extends ExecutorDecorator {
    private final List<Proxy> proxies = new ArrayList<>();

    public RandomProxyDecorator addProxy(String host, int port) {
        proxies.add(new Proxy(host, port));
        return this;
    }

    @Override
    public ExecutorDecorator setExecutor(Executor executor) {
        super.setExecutor(executor);
        if (this.getExecutor() instanceof HttpClientExecutor) {
            HttpClientExecutor exe = (HttpClientExecutor) this.getExecutor();
            exe.getBuilder().setRoutePlanner(routePlanner);
        }
        return this;
    }

    @Override
    public Response execute(Request request) throws HttpException {
        //Force create new HttpClient
        if (this.getExecutor() instanceof HttpClientExecutor) {
            HttpClientExecutor exe = (HttpClientExecutor) this.getExecutor();
            HttpClient client = exe.getBuilder().build();
            exe.setHttpClient(client);
        }
        return this.executor.execute(request);
    }

    private HttpRoutePlanner routePlanner = (target, request, context) -> {
        if(proxies.size() > 0) {
            int index = (int)(System.currentTimeMillis() % proxies.size());
            Proxy proxy = proxies.get(index);
            return new HttpRoute(target, null, new HttpHost(proxy.host, proxy.port), "https".equalsIgnoreCase(target.getSchemeName()));
        } else {
            return new HttpRoute(target);
        }
    };

    private static class Proxy {
        final String host;
        final int port;

        Proxy(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public int hashCode() {
            return new StringBuilder(host).append(":").append(port).toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj instanceof Proxy) {
                Proxy p = (Proxy)obj;
                return ((host == null && p.host == null) || host.equals(p.host)) && port == p.port;
            }
            return false;
        }
    }
}
