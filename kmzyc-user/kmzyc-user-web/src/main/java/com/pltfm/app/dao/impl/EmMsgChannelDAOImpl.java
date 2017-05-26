package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.EmMsgChannelDAO;
import com.pltfm.app.vobject.EmMsgChannel;

@Component(value = "emMsgChannelDAO")
public class EmMsgChannelDAOImpl implements EmMsgChannelDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public int deleteByPrimaryKey(BigDecimal id) throws SQLException {
    EmMsgChannel key = new EmMsgChannel();
    key.setId(id.longValue());
    int rows = sqlMapClient.delete("EM_MSG_CHANNEL.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加通道
  public int insert(EmMsgChannel record) throws SQLException {
    long count = (Long) sqlMapClient.insert("EM_MSG_CHANNEL.ibatorgenerated_insert", record);
    return (int) count;
  }



  public EmMsgChannel selectByPrimaryKey(BigDecimal id) throws SQLException {
    EmMsgChannel key = new EmMsgChannel();
    key.setId(id.longValue());
    EmMsgChannel record = (EmMsgChannel) sqlMapClient
        .queryForObject("EM_MSG_CHANNEL.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }


  public int updateByPrimaryKeySelective(EmMsgChannel record) throws SQLException {
    int rows =
        sqlMapClient.update("EM_MSG_CHANNEL.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  public int updateByPrimaryKey(EmMsgChannel record) throws SQLException {
    int rows = sqlMapClient.update("EM_MSG_CHANNEL.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  public List<EmMsgChannel> findEmMsgChannelList() {
    List<EmMsgChannel> list;
    try {
      list = sqlMapClient.queryForList("EM_MSG_CHANNEL.ibatorgenerated_selectList");
    } catch (SQLException e) {
      return null;
    }
    return list;
  }

  @Override
  public EmMsgChannel findByIdEmMsgChannel(int id) throws Exception {

    EmMsgChannel record = (EmMsgChannel) sqlMapClient
        .queryForObject("EM_MSG_CHANNEL.ibatorgenerated_selectByPrimaryKey", id);
    return record;
  }
}
