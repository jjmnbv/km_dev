package com.kmzyc.promotion.sys.util;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.km.framework.mq.bean.KmMsg;

public class SenderUtil {

  public SenderUtil() {}

  private static final Logger logger = LoggerFactory.getLogger(SenderUtil.class);

  public static void sendMessage(final KmMsg kmMsg, Destination destination,
      JmsTemplate jmsTemplate, long delay) {
    logger.info("发送消息开始。消息体{}。", kmMsg.getMsgData());

    jmsTemplate.send(destination, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        try {
          ObjectMessage objectMessage = session.createObjectMessage(kmMsg);
          logger.info("延迟[{}]秒接收。", delay / 1000);
          objectMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
          return objectMessage;
        } catch (JMSException e) {
          logger.error("发送消息异常,错误信息:{}。", e);
          throw new JMSException("发送MQ消息发生异常," + e);
        }
      }
    });
    logger.info("发送消息结束。");
  }

}


/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from:
 * D:\apache-maven-3.0.5\repository\km\km-communication\1.0.6\km-communication-1.0.6.jar Total time:
 * 102 ms Jad reported messages/errors: Exit status: 0 Caught exceptions:
 */
