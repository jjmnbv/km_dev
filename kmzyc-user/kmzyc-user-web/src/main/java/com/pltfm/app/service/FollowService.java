package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.Follow;

public interface FollowService {
  public List<Follow> findList();

  public String find();

}
