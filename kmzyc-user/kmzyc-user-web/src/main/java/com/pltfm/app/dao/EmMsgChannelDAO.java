package com.pltfm.app.dao;

import com.pltfm.app.vobject.EmMsgChannel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface EmMsgChannelDAO {

  int deleteByPrimaryKey(BigDecimal id) throws SQLException;

  int insert(EmMsgChannel record) throws SQLException;

  EmMsgChannel selectByPrimaryKey(BigDecimal id) throws SQLException;

  int updateByPrimaryKeySelective(EmMsgChannel record) throws SQLException;

  int updateByPrimaryKey(EmMsgChannel record) throws SQLException;

  List<EmMsgChannel> findEmMsgChannelList() throws SQLException;

  EmMsgChannel findByIdEmMsgChannel(int id) throws Exception;
}
