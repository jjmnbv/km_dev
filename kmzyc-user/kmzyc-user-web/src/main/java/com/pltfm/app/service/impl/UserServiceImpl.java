package com.pltfm.app.service.impl;

import com.pltfm.app.service.UserService;

public class UserServiceImpl implements UserService {

  @Override
  public String doUserName(String name) {
    return "helloword" + name;
  }

  @Override
  public String hello(String user) {
    return "Hi" + user;
  }

}
