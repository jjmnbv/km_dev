package com.kmzyc.promotion.app.jms;

import com.km.framework.mq.bean.KmMsg;

/**
 * 消息发送者
 * 
 * @author river
 * 
 */
public interface MessageSender {

  /**
   * 发送Queue消息
   */
  public void sendQueueMessage(String destinationId, KmMsg kmMsg);

}
