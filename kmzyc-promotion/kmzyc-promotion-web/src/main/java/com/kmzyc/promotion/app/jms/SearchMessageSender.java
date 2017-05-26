package com.kmzyc.promotion.app.jms;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.promotion.sys.util.AppApplicationContextUtil;

/**
 * 消息发送者
 * 
 * @author river
 * 
 */
@Component
public class SearchMessageSender implements MessageSender {

  @Resource(name = "jmsQueueTemplate")
  private JmsTemplate jmsQueueTemplate;

  @Override
  public void sendQueueMessage(String destinationId, KmMsg kmMsg) {
    // 消息队列Bean
    Destination destination =
        (Destination) AppApplicationContextUtil.getApplicationContext().getBean(destinationId);

    // 消息发送
    Sender.send(kmMsg, destination, jmsQueueTemplate);
  }
}
