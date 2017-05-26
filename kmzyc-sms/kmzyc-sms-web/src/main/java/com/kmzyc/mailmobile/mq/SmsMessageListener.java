package com.kmzyc.mailmobile.mq;

import com.km.framework.mq.bean.KmMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kmzyc.mailmobile.mq.ConsumeEmailMsg.consumeMsg;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author min
 * @create 2016-11-30 10:08
 */
public class SmsMessageListener implements MessageListener {
    static Logger logger = LoggerFactory.getLogger(SmsMessageListener.class);
    @Override
    public void onMessage(Message message) {
        if(message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage)message;
            KmMsg kmMsg;
            try {
                kmMsg = (KmMsg)objectMessage.getObject();
                consumeMsg(kmMsg);
            } catch (Exception var5) {
                logger.error("接收MQ消息发生异常！", var5);
            }

        }

    }
}
