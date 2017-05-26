package com.kmzyc.mailmobile.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.mailmobile.model.EmMsgChannel;

public interface EmMsgChannelDAO {

  int insert(EmMsgChannel record) throws SQLException;

  List<EmMsgChannel> findEmMsgChannelList() throws SQLException;

}
