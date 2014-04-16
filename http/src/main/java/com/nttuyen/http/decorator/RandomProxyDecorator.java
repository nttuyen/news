/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.nttuyen.http.decorator;

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
  @Override
  public ExecutorDecorator setExecutor(Executor executor) {
    super.setExecutor(executor);
    if(this.getExecutor() instanceof HttpClientExecutor) {
      HttpClientExecutor exe = (HttpClientExecutor)this.getExecutor();
      exe.getBuilder().setRoutePlanner(routePlanner);
    }
      return this;
  }

  @Override
  public Response execute(Request request) throws HttpException {
    //Force create new HttpClient
    if(this.getExecutor() instanceof HttpClientExecutor) {
      HttpClientExecutor exe = (HttpClientExecutor)this.getExecutor();
      HttpClient client = exe.getBuilder().build();
      exe.setHttpClient(client);
    }
    return this.executor.execute(request);
  }

  private HttpRoutePlanner routePlanner = (target, request, context) -> {
        //TODO: how to pick random proxy
        return new HttpRoute(target, null,  new HttpHost("someproxy", 8080),
              "https".equalsIgnoreCase(target.getSchemeName()));
    };
}
