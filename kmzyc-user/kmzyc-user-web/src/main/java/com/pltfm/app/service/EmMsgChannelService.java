package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.EmMsgChannel;

public interface EmMsgChannelService {

  /**
   * 分页查询通道
   * 
   * @param 通道实体
   * @return
   * @throws Exception 异常
   */
  public Page findEmMsgChannelPage(Page pageParam, EmMsgChannel vo) throws Exception;

  /**
   * 查询通道列表
   * 
   * @param 通道实体
   * @return
   * @throws Exception 异常
   */
  public List<EmMsgChannel> selectEmMsgChannelList() throws Exception;

  public int updateEmMsgChannel(EmMsgChannel vo) throws Exception;

  public int insertEmMsgChannel(EmMsgChannel vo) throws Exception;

  public EmMsgChannel findByIdEmMsgChannel(int id) throws Exception;

}
