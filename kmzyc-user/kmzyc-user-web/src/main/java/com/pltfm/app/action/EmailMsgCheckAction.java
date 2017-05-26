package com.pltfm.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.framework.constants.EmailSendType;
import com.pltfm.app.service.EmailMsgService;
import com.pltfm.app.vobject.MailSendTask;
import com.pltfm.app.vobject.MsgSendTask;


@Component(value = "emailMsgCheckAction")
@Scope(value = "prototype")
public class EmailMsgCheckAction {


    private Page page;
    private MsgSendTask msgSendTask;
    private MailSendTask mailSendTask;

    private Map<String, String> mailTemplateMap = new HashMap<String, String>();
    private Map<Integer, String> msgTypeMap = new HashMap<Integer, String>();

    @Resource(name = "emailMsgService")
    private EmailMsgService emailMsgService;

    /**
     * 获取短信发送列表信息
     * 
     * @return
     */
    public String queryMsgSendList() {
        try {
            page = emailMsgService.getMsgSendTaskPage(page, msgSendTask);
            msgTypeMap = EmailSendType.getMsgTypeMap();
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }

    /**
     * 获取短信发送列表信息
     * 
     * @return
     */
    /*
     * 删除邮件业务 public String queryEmailSendList() { try { page =
     * emailMsgService.getMailSendTaskPage(page, mailSendTask); mailTemplateMap =
     * EmailTemplateStatus.getEmailTemplateStatus(); } catch (Exception e) { return "fail"; } return
     * "success"; }
     */



    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public MsgSendTask getMsgSendTask() {
        return msgSendTask;
    }

    public void setMsgSendTask(MsgSendTask msgSendTask) {
        this.msgSendTask = msgSendTask;
    }


    public MailSendTask getMailSendTask() {
        return mailSendTask;
    }

    public void setMailSendTask(MailSendTask mailSendTask) {
        this.mailSendTask = mailSendTask;
    }

    public Map<String, String> getMailTemplateMap() {
        return mailTemplateMap;
    }

    public void setMailTemplateMap(Map<String, String> mailTemplateMap) {
        this.mailTemplateMap = mailTemplateMap;
    }

    public Map<Integer, String> getMsgTypeMap() {
        return msgTypeMap;
    }

    public void setMsgTypeMap(Map<Integer, String> msgTypeMap) {
        this.msgTypeMap = msgTypeMap;
    }



}
