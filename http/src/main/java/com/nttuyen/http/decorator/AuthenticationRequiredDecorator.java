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
      return executor.execute(request);
    }
    throw new HttpException("Can not authentication");
  }
}
