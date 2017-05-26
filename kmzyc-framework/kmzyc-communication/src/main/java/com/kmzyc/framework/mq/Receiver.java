package com.kmzyc.framework.mq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.drools.RulesEngine;
import com.kmzyc.framework.mq.bean.KmMsg;

public class Receiver implements MessageListener {
    static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            KmMsg kmMsg = null;
            try {
                //String msgText = textMessage.getText();
                kmMsg = (KmMsg) objectMessage.getObject();//JSONObject.toBean( JSONObject.fromObject(msgText), KmMsg.class);
            } catch (Exception e) {
                logger.error("接收MQ消息发生异常！", e);
            }
            RulesEngine.insert(kmMsg);
        }
    }

}

