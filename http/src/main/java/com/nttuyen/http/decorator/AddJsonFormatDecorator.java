package com.nttuyen.http.decorator;

import com.nttuyen.http.Executor;
import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;
import com.nttuyen.http.impl.HttpClientExecutor;
import org.apache.http.HttpRequestInterceptor;

public class AddJsonFormatDecorator extends ExecutorDecorator {
  @Override
  public ExecutorDecorator setExecutor(Executor executor) {
    super.setExecutor(executor);
    if(this.getExecutor() instanceof HttpClientExecutor) {
      HttpClientExecutor exe = (HttpClientExecutor)this.getExecutor();
      exe.getBuilder().addInterceptorLast((HttpRequestInterceptor)(request, context) -> {
        request.getParams().setParameter("format", "json");
      });
    }
    return this;
  }

  @Override
  public Response execute(Request request) throws HttpException {
      request.addParam("format", "json");
    return executor.execute(request);
  }
}
