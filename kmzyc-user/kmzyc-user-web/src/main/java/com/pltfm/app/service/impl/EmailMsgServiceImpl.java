package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.EmailMsgDao;
import com.pltfm.app.service.EmailMsgService;
import com.pltfm.app.vobject.MailSendTask;
import com.pltfm.app.vobject.MsgSendTask;


@Component(value = "emailMsgService")
public class EmailMsgServiceImpl implements EmailMsgService {

  @Resource(name = "emailMsgDao")
  private EmailMsgDao emailMsgDao;

  /**
   * 分页查询短信发送任务
   * 
   * @param pageParam
   * @param msgSendTask
   * @return
   * @throws Exception
   */
  public Page getMsgSendTaskPage(Page pageParam, MsgSendTask msgSendTask) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (msgSendTask == null) {
      msgSendTask = new MsgSendTask();
    }
    int totalNum = emailMsgDao.getMsgCountByMsgSendTask(msgSendTask);
    pageParam.setRecordCount(totalNum);
    msgSendTask.setSkip(pageParam.getStartIndex());
    msgSendTask.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(emailMsgDao.queryMsgSendTaskList(msgSendTask));
    return pageParam;
  }

  @Override
  public Page getMailSendTaskPage(Page pageParam, MailSendTask mailSendTask) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (mailSendTask == null) {
      mailSendTask = new MailSendTask();
    }
    int totalNum = emailMsgDao.getMailCountByMsgSendTask(mailSendTask);
    pageParam.setRecordCount(totalNum);
    mailSendTask.setSkip(pageParam.getStartIndex());
    mailSendTask.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(emailMsgDao.queryMailSendTaskList(mailSendTask));
    return pageParam;
  }



}
