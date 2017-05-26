package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.redis.RedisConstants;
import com.pltfm.app.dao.EmMsgChannelDAO;
import com.pltfm.app.service.EmMsgChannelService;
import com.pltfm.app.vobject.EmMsgChannel;

import redis.clients.jedis.JedisCluster;

/**
 * 短信通道
 * 
 * @author wangkai
 * @since 2015-12-08
 */
@Component(value = "emMsgChannelService")
public class EmMsgChannelServiceImpl implements EmMsgChannelService {
  @Resource(name = "emMsgChannelDAO")
  private EmMsgChannelDAO emMsgChannelDAO;

  @Resource
  private JedisCluster jedisCluster;


  @Override
  public Page findEmMsgChannelPage(Page pageParam, EmMsgChannel vo) throws Exception {
    return null;
  }

  /**
   * 查所有通道集合
   */
  @Override
  public List<EmMsgChannel> selectEmMsgChannelList() throws Exception {
    return emMsgChannelDAO.findEmMsgChannelList();
  }

  /**
   * 修改通道信息
   * 
   * @return 影响条数
   * @param vo
   */
  @Override
  public int updateEmMsgChannel(EmMsgChannel vo) throws Exception {

    try {
      jedisCluster.del(RedisConstants.SMS_FINDEMMSGCHANNELLIST);
    }catch (Exception e){
      e.printStackTrace();
    }

    return emMsgChannelDAO.updateByPrimaryKeySelective(vo);
  }

  /**
   * 添加通道信息
   * 
   * @return 影响条数
   * @param vo
   */
  @Override
  public int insertEmMsgChannel(EmMsgChannel vo) throws Exception {
    return emMsgChannelDAO.insert(vo);
  }

  /**
   * 根据ID查询
   * 
   * @return EmMsgChannel
   * @param id
   */
  @Override
  public EmMsgChannel findByIdEmMsgChannel(int id) throws Exception {

    return emMsgChannelDAO.findByIdEmMsgChannel(id);
  }


}
