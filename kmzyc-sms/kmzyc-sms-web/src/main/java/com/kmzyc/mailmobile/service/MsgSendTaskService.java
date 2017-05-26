package com.kmzyc.mailmobile.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.mailmobile.model.MsgSendTask;

public interface MsgSendTaskService {


  /**
   * 获取需要要发送的短信任务，包括定时发送和定时发送失败了且发送次数没有达到上限的
   * 
   * @return
   */
  public List<MsgSendTask> getMsgSendTaskListTiming() throws SQLException;

  /**
   * 获取需要要发送的短信任务，包括立即发送失败了且发送次数没有达到上限的
   * 
   * @return
   */
  public List<MsgSendTask> getMsgSendTaskListTimely() throws SQLException;


  /**
   * 保存发送任务
   * 
   * @param msgSendTask
   */
  public Long saveMsgSendTask(MsgSendTask msgSendTask) throws SQLException;

  /**
   * 保存发送任务
   * 
   * @param msgSendTask
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
