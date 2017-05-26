package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.vobject.UserChannel;

public interface UserChannelService {

  /**
   * 获取某个用户登录渠道
   * 
   * @param userId
   * @return
   * @throws Exception
   */
  public List<UserChannel> findByUserId(Long userId) throws Exception;

  /**
   * 保存用户的渠道
   * 
   * @param userChannelList
   * @throws Exception
   */
  public void saveUserChannel(List<UserChannel> userChannelList, List<Long> userIds)
      throws Exception;

}
