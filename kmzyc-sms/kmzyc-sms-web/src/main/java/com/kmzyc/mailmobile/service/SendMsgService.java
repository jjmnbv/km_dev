package com.kmzyc.mailmobile.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.mailmobile.model.MsgSendTask;

public interface SendMsgService {

  /**
   * 处理发送信息
   * 
   * @param msgSendTask
   * @return
   */
  public Map<String, Object> sendMsg(MsgSendTask msgSendTask);

  public int reInt(String type);

}
