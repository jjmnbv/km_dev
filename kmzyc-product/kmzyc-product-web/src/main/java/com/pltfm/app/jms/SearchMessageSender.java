package com.pltfm.app.jms;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.pltfm.app.jms.MessageSender;
import com.pltfm.sys.util.AppApplicationContextUtil;

/**
 * 消息发送者
 * @author river
 *
 */
@Component
public class SearchMessageSender implements MessageSender {

	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource
	private Destination destination;
	
	@Override
	public void sendMessage(final String code,final List<Long> ids, final String opType){
		KmMsg kmMsg = new KmMsg(code, true);//报文编号为2000标识产品系统
		kmMsg.getMsgData().put("ids",ids);
		kmMsg.getMsgData().put("opType",opType);
		Sender.send(kmMsg, destination, jmsTemplate);
	}
	
}