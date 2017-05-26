package com.pltfm.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pltfm.app.service.FollowService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.Follow;

@Component(value = "followService")
public class FollowServiceImpl implements FollowService {

  @Override
  public List<Follow> findList() {
    List<Follow> followList = new ArrayList<Follow>();
    Follow follow = null;
    follow = new Follow();
    follow.setFollowContent("张三医生");
    follow.setFollowDate(DateTimeUtils.getCalendarInstance().getTime());
    follow.setFollowId(1001);
    follow.setFollowURL("http://localhost");
    follow.setN_LoginId(489);
    follow.setName("小米");
    followList.add(follow);

    follow = new Follow();
    follow.setFollowContent("王五医生");
    follow.setFollowDate(DateTimeUtils.getCalendarInstance().getTime());
    follow.setFollowId(1002);
    follow.setFollowURL("http://localhost");
    follow.setN_LoginId(490);
    follow.setName("斯沃");
    followList.add(follow);

    follow = new Follow();
    follow.setFollowContent("里德医生");
    follow.setFollowDate(DateTimeUtils.getCalendarInstance().getTime());
    follow.setFollowId(1002);
    follow.setFollowURL("http://localhost");
    follow.setN_LoginId(490);
    follow.setName("李喏");
    followList.add(follow);

    return followList;
  }

  @Override
  public String find() {
    return "Hi";
  }

}
