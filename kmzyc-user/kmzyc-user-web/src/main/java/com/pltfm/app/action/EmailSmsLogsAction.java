package com.pltfm.app.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.EmailSmsLogsService;
import com.pltfm.app.vobject.EmailSmsLogs;

/***
 * 
 * 邮件短信记录
 * 
 * @author Administrator
 *
 */
@Scope("prototype")
@Component(value = "emailSmsLogsAction")
public class EmailSmsLogsAction extends BaseAction implements ModelDriven {

  private EmailSmsLogs emailSmsLog;
  /**
   * 邮件短信记录业务逻辑接口
   */
  @Resource(name = "emailSmsLogsService")
  private EmailSmsLogsService emailSmsLogsService;
  /**
   * 分页类
   */
  private Page page;

  public String pageListSmsLogs() {

    try {
        String isMenu = getRequest().getParameter("isMenu");
        if(!"true".equals(isMenu)){
            page = emailSmsLogsService.getEmailSmsLogs(page, emailSmsLog);
        }else{
            getRequest().setAttribute("isMenu", "true");
        }
    } catch (Exception e) {
      e.printStackTrace();

    }
    return "smsLogsList";


  }



  public EmailSmsLogs getEmailSmsLog() {
    return emailSmsLog;
  }

  public void setEmailSmsLog(EmailSmsLogs emailSmsLog) {
    this.emailSmsLog = emailSmsLog;
  }



  public void setEmailSmsLogsService(EmailSmsLogsService emailSmsLogsService) {
    this.emailSmsLogsService = emailSmsLogsService;
  }



  @Override
public Object getModel() {
    // TODO Auto-generated method stub
    emailSmsLog = new EmailSmsLogs();
    return emailSmsLog;
  }

  @Override
public Page getPage() {
    return page;
  }


  @Override
public void setPage(Page page) {
    this.page = page;
  }


}
