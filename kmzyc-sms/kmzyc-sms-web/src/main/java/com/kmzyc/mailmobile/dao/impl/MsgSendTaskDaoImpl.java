package com.kmzyc.mailmobile.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.mailmobile.dao.MsgSendTaskDao;
import com.kmzyc.mailmobile.model.MsgSendTask;

@Component
public class MsgSendTaskDaoImpl extends DaoImpl implements MsgSendTaskDao {

  static Logger logger = Logger.getLogger(MsgSendTaskDaoImpl.class);
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public Long saveMsgSendTask(MsgSendTask msgSendTask) throws SQLException {
    return (Long) sqlMapClient.insert("MsgSendTask.msgSendTask_insert", msgSendTask);
  }

  @Override
  public void updateMsgSendTask(MsgSendTask msgSendTask) throws SQLException {
    sqlMapClient.update("MsgSendTask.msgSendTask_update", msgSendTask);
  }

  @Override
  public MsgSendTask SelectMsgSendTask(Map map) throws SQLException {
    MsgSendTask msgSendTask = null;
    msgSendTask = (MsgSendTask) sqlMapClient.queryForObject("MsgSendTask.queryMsgSendTask", map);
    return msgSendTask;
  }

}
