package com.pltfm.app.util;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.pltfm.app.service.RiskManagementService;

public class OrderRiskManagementUtils implements MessageListener {
  private RiskManagementService riskManagementService;
  private static Logger logger = Logger.getLogger(OrderRiskManagementUtils.class);


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
        TextMessage tm = null;
        try {
          tm = session.createTextMessage(value);
        } catch (Exception e) {
          logger.error(e.getMessage());
          throw new JMSException("发送MQ消息发生异常," + e);
        }
        return tm;
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
    TextMessage tm = (TextMessage) message;
    try {
      String value = (String) tm.getText();
      riskManagementService.execRiskJudge(value);
    } catch (Exception e) {
      logger.error("接收MQ消息发生异常！", e);
    }
  }

  public RiskManagementService getRiskManagementService() {
    return riskManagementService;
  }

  public void setRiskManagementService(RiskManagementService riskManagementService) {
    this.riskManagementService = riskManagementService;
  }
}
