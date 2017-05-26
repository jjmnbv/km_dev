package com.kmzyc.express.action;


import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.service.ExpressSubscriptionService;

@Controller("testAction")
@Scope("prototype")
public class TestAction {

    @Resource
    private ExpressSubscriptionService expressSubscriptionService;
    private ExpressSubscription expressSubscription = new ExpressSubscription();
    private String logisticsCode;
    private String logisticsNo; 

    public void testQuerySubWithDetail() throws ActionException {
        PrintWriter pw=null;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String retString = "";
        try {
            pw = response.getWriter();
                List resultList =
                        expressSubscriptionService.queryExpressSubWithTrackDetail(
                            logisticsCode , logisticsNo);
                if (resultList != null && resultList.size() > 0) {
                    expressSubscription = (ExpressSubscription) resultList.get(0);
                    
                }
                retString = "0"; 
        } catch (Exception ex) {
            retString = "1";
            throw new ActionException("查询物流跟踪信息异常：" + ex.getMessage());
        }finally{
            pw.write(retString);
        }
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public ExpressSubscriptionService getExpressSubscriptionService() {
        return expressSubscriptionService;
    }

    public void setExpressSubscriptionService(ExpressSubscriptionService expressSubscriptionService) {
        this.expressSubscriptionService = expressSubscriptionService;
    }
}
