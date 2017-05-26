package com.pltfm.app.jms;

import java.util.List;


/**
 * 消息发送者
 * @author river
 *
 */
public interface MessageSender {

	/**
	 * 发送消息
	 * @param code	发送消息的目标频道
	 * @param ids	消息体
	 * @param opType	消息体
	 */
	void sendMessage(final String code,final List<Long> ids, final String opType);
}