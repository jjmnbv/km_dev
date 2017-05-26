package com.pltfm.app.dao;

import com.pltfm.app.vobject.MailSendTask;
import com.pltfm.app.vobject.MsgSendTask;

import java.sql.SQLException;
import java.util.List;

public interface EmailMsgDao {


  /**
   * 获取短信总条数
   * 
   * @param msgSendTask
   * @return
   * @throws SQLException
   */
  public int getMsgCountByMsgSendTask(MsgSendTask msgSendTask) throws SQLException;


  /**
   * 查询短信发送任务
   * 
   * @param msgSendTask
   * @return
   * @throws SQLException
   */
  public List<MsgSendTask> queryMsgSendTaskList(MsgSendTask msgSendTask) throws SQLException;


  /**
   * 获取邮件总条数
   * 
   * @param msgSendTask
   * @return
   * @throws SQLException
   */
  public int getMailCountByMsgSendTask(MailSendTask mailSendTask) throws SQLException;


  /**
   * 查询邮件发送任务
   * 
   * @param msgSendTask
   * @return
   * @throws SQLException
   */
  public List<MsgSendTask> queryMailSendTaskList(MailSendTask mailSendTask) throws SQLException;


}
