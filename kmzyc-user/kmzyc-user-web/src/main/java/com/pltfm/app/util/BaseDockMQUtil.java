package com.pltfm.app.util;

import com.pltfm.app.service.BaseDockService;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public class BaseDockMQUtil implements MessageListener {
  private BaseDockService baseDockService = null;

  private static final Logger log = Logger.getLogger(BaseDockMQUtil.class);

  /**
   * 发送mq消息
   * 
   * @param json
   * @param destination
   * @param jmsTemplate
   */
  public static void send(final String value, Destination destination, JmsTemplate jmsTemplate) {
    jmsTemplate.send(destination, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = null;
        try {
          objectMessage = session.createObjectMessage(value);
        } catch (Exception e) {
          log.error(e.getMessage());
          throw new JMSException("发送MQ消息发生异常," + e);
        }
        return objectMessage;
      }
    });
  }

  /**
   * 接收mq消息
   * 
   * @param message
   */
  @Override
  public void onMessage(Message message) {
//    try {
//      ObjectMessage objectMessage = (ObjectMessage) message;
//      String str = (String) objectMessage.getObject();
//      /*删除推送总部会员系统  baseDockService.baseDockReceiver(str);*/
//    } catch (Exception e) {
//      log.error("接收MQ消息发生异常！", e);
//    }
  }

  public BaseDockService getBaseDockService() {
    return baseDockService;
  }

  public void setBaseDockService(BaseDockService baseDockService) {
    this.baseDockService = baseDockService;
  }
}
