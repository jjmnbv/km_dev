package com.kmzyc.mailmobile.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.mailmobile.dao.EmMsgChannelDAO;
import com.kmzyc.mailmobile.model.EmMsgChannel;
import com.kmzyc.redis.RedisConstants;

import redis.clients.jedis.JedisCluster;

@Component(value = "emMsgChannelDAO")
public class EmMsgChannelDAOImpl implements EmMsgChannelDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Resource(name="jedisCluster")
  private JedisCluster jedisCluster;

  // 添加通道
  @Override
public int insert(EmMsgChannel record) throws SQLException {
    long count = (Long) sqlMapClient.insert("EM_MSG_CHANNEL.ibatorgenerated_insert", record);
    return (int) count;
  }

  @Override
public List<EmMsgChannel> findEmMsgChannelList() {
    List<EmMsgChannel> list;
    try {
      if(jedisCluster.exists(RedisConstants.SMS_FINDEMMSGCHANNELLIST)) {

        String str = jedisCluster.get(RedisConstants.SMS_FINDEMMSGCHANNELLIST);
        if (!StringUtils.isEmpty(str)) {
          list =  JSONArray.parseArray(str, EmMsgChannel.class);
        } else {
          list =  new ArrayList<>();
        }
      }else{
        list = sqlMapClient.queryForList("EM_MSG_CHANNEL.ibatorgenerated_selectList");

        String array = JSONArray.toJSONString(list);
        jedisCluster.set(RedisConstants.SMS_FINDEMMSGCHANNELLIST, array);
        jedisCluster.expire(RedisConstants.SMS_FINDEMMSGCHANNELLIST,60*60*12);
      }

    } catch (SQLException e) {
      return null;
    }
    return list;
  }

}
