package com.pltfm.sys.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.bean.SysLogBean;
import com.pltfm.sys.model.SysLog;

@SuppressWarnings("unchecked")
public class SysLogAction extends ActionSupport implements ModelDriven {
  private static final long serialVersionUID = 1L;
  Logger log = Logger.getLogger(this.getClass());
  SysLog sysLog = new SysLog(); // model
  String rtnMessage;

  Page page = new Page();
  List logList;

  /**
   * 日志查询
   * 
   * @param
   * @return String
   * @exception
   */
  public String listLog() {
    try {
      // log.warn("================ in SysLogAction listLog()");
      if (sysLog == null || sysLog.getSyslogTime() == null) {
        return SUCCESS;
      }
      SysLogBean bean = new SysLogBean();
      page.setPageSize(20);
      page = bean.searchPageByVo(this.getPage(), sysLog);
      return SUCCESS;
    } catch (Exception e) {
      log.error(e);
      return ERROR;
    }
  }

  public String getRtnMessage() {
    return rtnMessage;
  }

  public void setRtnMessage(String rtnMessage) {
    this.rtnMessage = rtnMessage;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public List getLogList() {
    return logList;
  }

  public void setLogList(List logList) {
    this.logList = logList;
  }

  public String doDefault() throws Exception {
    return INPUT;
  }

  @Override
public Object getModel() {
    return sysLog;
  }
}
