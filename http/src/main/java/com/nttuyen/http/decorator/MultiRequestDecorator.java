package com.nttuyen.http.decorator;

import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.http.impl.HttpClientExecutor;
import org.apache.http.client.HttpClient;

public class MultiRequestDecorator extends ExecutorDecorator {
  //Cached httpClient
  private HttpClient httpClient;

  @Override
  public Response execute(Request request) throws HttpException {
    if(this.getExecutor() instanceof HttpClientExecutor) {
      HttpClientExecutor executor = (HttpClientExecutor)this.getExecutor();
      if(this.httpClient == null) {
        //Create new instance of HttpClient
        this.httpClient = executor.getBuilder().build();
      }
      executor.setHttpClient(httpClient);
    }
    return this.executor.execute(request);
  }
}
