package com.kmzyc.mailmobile.dao;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.mailmobile.model.MsgSendTask;


public interface MsgSendTaskDao extends Dao {


  /**
   * 保存发送短信任务
   * 
   * @param msgSendTask
   * @throws SQLException
   */
  public Long saveMsgSendTask(MsgSendTask msgSendTask) throws SQLException;


  /**
   * 更新发送短信任务
   * 
   * @param msgSendTask
   * @throws SQLException
   */
  public void updateMsgSendTask(MsgSendTask msgSendTask) throws SQLException;

  /**
   * 根据返回码和手机号查发送短信任务
   * 
   * @param msgSendTask
   * @throws SQLException
   */
  public MsgSendTask SelectMsgSendTask(Map map) throws SQLException;


}
