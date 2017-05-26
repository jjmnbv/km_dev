package com.kmzyc.framework.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.kmzyc.framework.mq.bean.KmMsg;

public class Sender {
    static Logger logger = LoggerFactory.getLogger(Sender.class);
//	public static ApplicationContext application;
//	private static JmsTemplate jmsTemplate;
//	private static Destination destination;
//	static{
//		try{
//		application = new ClassPathXmlApplicationContext("spring/applicationContext-mq.xml");
//		jmsTemplate = (JmsTemplate)application.getBean("jmsTemplate");
//		destination = (Destination)application.getBean("destination");
//		}catch (Exception e) {
//			logger.error("获取MQ配置文件出错："+e);
//		}
//	}

    /**
     * 消息发送方法
     */
    public static void send(final KmMsg kmMsg, Destination destination, JmsTemplate jmsTemplate) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                //JSONObject jsONObject = JSONObject.fromObject(kmMsg);
                //String msgString = jsONObject.toString();
                ObjectMessage objectMessage = null;
                try {
                    objectMessage = session.createObjectMessage(kmMsg);
                    kmMsg.setSendCount(kmMsg.getSendCount() + 1);
                } catch (JMSException e) {
                    logger.error("发送MQ消息发生异常！", e);
                    throw new JMSException("发送MQ消息发生异常," + e);
                }
                return objectMessage;
            }
        });
    }
}
