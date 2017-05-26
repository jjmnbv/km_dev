package com.pltfm.sys.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.bean.SysLogBean;
import com.pltfm.sys.model.SysLog;
import com.kmzyc.commons.page.Page;

import java.util.List;

public class SysLogAction extends ActionSupport implements ModelDriven {
	private static Logger logger = LoggerFactory.getLogger(SysLogAction.class);
    SysLog sysLog = new SysLog();    //model
    String rtnMessage;

    Page page = new Page();
    List logList;


    /**
     * 日志查询
     *
     * @return String
     */
    public String listLog() {
        try {
            //log.warn("================ in SysLogAction listLog()");
            if (sysLog == null || sysLog.getSyslogTime() == null) {
                return SUCCESS;
            }
            SysLogBean bean = SysLogBean.instance();
            page.setPageSize(20);
            page = bean.searchPageByVo(this.getPage(), sysLog);
            return SUCCESS;
        } catch (Exception e) {
        	logger.error("SysLogAction.listLog异常：" + e.getMessage(), e);
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

    public Object getModel() {
        return sysLog;
    }
}
