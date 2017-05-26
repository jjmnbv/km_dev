package com.kmzyc.b2b.weibo;

import com.kmzyc.b2b.weibo.http.HttpClient;

public class Weibo implements java.io.Serializable {

  private static final long serialVersionUID = 4282616848978535016L;

  public HttpClient client = new HttpClient();

  public void setToken(String token) {
    client.setToken(token);
  }

}
