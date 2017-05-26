package com.kmzyc.b2b.service;

import com.km.framework.mq.bean.KmMsg;

public interface SendKmMsgService {

  /**
   * mq发送消息
   * 
   * @param kmMsg
   */
  public void sendKmMsg(KmMsg kmMsg);

}
