package com.kmzyc.mailmobile.service;

import com.kmzyc.mailmobile.model.MsgSendTask;

public interface HandleMsgSendTaskService {
	
	
	/**
	 * 发送组装好的短信发送任务
	 * @param msgSendTask
	 * @return
	 */
	public boolean handleMessage(MsgSendTask msgSendTask);
	
	/**
	 * 定时任务处理流程
	 * @param msgSendTask
	 * @return
	 */
	public boolean handleMsgSendTask(MsgSendTask msgSendTask);

}
