package com.kmzyc.b2b.service.impl;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.b2b.service.SendKmMsgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service("sendKmMsgService")
public class SendKmMsgServiceImpl implements SendKmMsgService {
  
  @Autowired
  private JmsTemplate jmsTemplate;

  @Resource(name = "emailMsg")
  private Destination destination;

  @Override
  public void sendKmMsg(KmMsg kmMsg) {
    Sender.send(kmMsg, destination, jmsTemplate);
  }

}
