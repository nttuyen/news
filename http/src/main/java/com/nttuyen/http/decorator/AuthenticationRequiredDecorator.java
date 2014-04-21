package com.nttuyen.http.decorator;

import com.nttuyen.http.ExecutorDecorator;
import com.nttuyen.http.HttpException;
import com.nttuyen.http.Request;
import com.nttuyen.http.Response;

public class AuthenticationRequiredDecorator extends ExecutorDecorator {
  private boolean isLoggedIn = false;
  private final Request authentication;

  public AuthenticationRequiredDecorator(Request authentication) {
    this.authentication = authentication;
  }

  @Override
  public Response execute(Request request) throws HttpException {
    if(!isLoggedIn) {
      if(authentication != null) {
        Response response = executor.execute(authentication);
        //TODO: authentication success always return 200 OK
        if(response.getStatusCode() == 200) {
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
      }
    }
    if(isLoggedIn) {
      Response response = executor.execute(request);
      //401 Unauthorized
      if(response.getStatusCode() == 401) {
          isLoggedIn = false;
      }
      return response;
    }
    throw new HttpException("Can not authentication");
  }
}
