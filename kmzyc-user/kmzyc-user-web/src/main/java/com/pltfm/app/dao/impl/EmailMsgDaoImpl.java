package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.EmailMsgDao;
import com.pltfm.app.vobject.MailSendTask;
import com.pltfm.app.vobject.MsgSendTask;
import com.pltfm.sys.model.SysModelUtil;

@Component(value = "emailMsgDao")
public class EmailMsgDaoImpl implements EmailMsgDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  public int getMsgCountByMsgSendTask(MsgSendTask msgSendTask) throws SQLException {
    @SuppressWarnings("rawtypes")
    List list = sqlMapClient.queryForList("MsgSendTask.getMsgTaskCount", msgSendTask);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int totalCount = countResult.getTheCount().intValue();
    return totalCount;
  }

  /**
   * 根据msgSendTask条件查询短信发送任务表
   * 
   * @param msgSendTask
   * @return
   * @throws SQLException
   */
  public List<MsgSendTask> queryMsgSendTaskList(MsgSendTask msgSendTask) throws SQLException {
    @SuppressWarnings("unchecked")
    List<MsgSendTask> msgSendTaskList =
        (List<MsgSendTask>) sqlMapClient.queryForList("MsgSendTask.queryMsgTaskList", msgSendTask);
    return msgSendTaskList;
  }



  @Override
  public int getMailCountByMsgSendTask(MailSendTask mailSendTask) throws SQLException {
    @SuppressWarnings("rawtypes")
    List list = sqlMapClient.queryForList("MailSendTask.getMailTaskCount", mailSendTask);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int totalCount = countResult.getTheCount().intValue();
    return totalCount;
  }

  /**
   * 根据mailSendTask条件查询短信发送任务表
   * 
   * @param msgSendTask
   * @return
   * @throws SQLException
   */
  public List<MsgSendTask> queryMailSendTaskList(MailSendTask mailSendTask) throws SQLException {
    @SuppressWarnings("unchecked")
    List<MsgSendTask> msgSendTaskList = (List<MsgSendTask>) sqlMapClient
        .queryForList("MailSendTask.queryMailTaskList", mailSendTask);
    return msgSendTaskList;
  }



}
