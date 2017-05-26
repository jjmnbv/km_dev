package com.pltfm.app.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.MailSendTask;
import com.pltfm.app.vobject.MsgSendTask;

public interface EmailMsgService {

  /**
   * 获取短信发送列表信息
   * 
   * @param pageParam
   * @param msgSendTask
   * @return
   */
  public Page getMsgSendTaskPage(Page pageParam, MsgSendTask msgSendTask) throws Exception;



  /**
   * 获取邮件发送列表信息
   * 
   * @param pageParam
   * @param mailSendTask
   * @return
   * @throws Exception
   */
  public Page getMailSendTaskPage(Page pageParam, MailSendTask mailSendTask) throws Exception;



}
