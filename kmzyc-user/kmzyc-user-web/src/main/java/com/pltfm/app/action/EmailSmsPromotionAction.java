package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.EmailSmsLogsService;
import com.pltfm.app.service.EmailSmsPromotionService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.EmailSmsLogs;
import com.pltfm.app.vobject.EmailSmsPromotion;
import com.pltfm.sys.model.SysUser;

@Scope("prototype")
@Component(value = "emailSmsPromotionAction")
public class EmailSmsPromotionAction extends BaseAction implements ModelDriven {
    private static Logger logger = LoggerFactory.getLogger(EmailSmsPromotionAction.class);
    /**
     * 分页类
     */
    private Page page;
    // 推广邮件实体
    private EmailSmsPromotion emailSmsPromotion;
    /**
     * 推广邮件业务逻辑接口
     */
    @Resource(name = "emailSmsPromotionService")
    private EmailSmsPromotionService emailSmsPromotionService;
    /**
     * 邮件短信记录业务逻辑接口
     */
    @Resource(name = "emailSmsLogsService")
    private EmailSmsLogsService emailSmsLogsService;
    // 用户信息
    String loginIds;
    String loginNames;
    /**
     * 推荐邮件主键ID集合
     */
    private List<Integer> promotionIds;

    public List<Integer> getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(List<Integer> promotionIds) {
        this.promotionIds = promotionIds;
    }

    @Override
    public Object getModel() {
        emailSmsPromotion = new EmailSmsPromotion();
        return emailSmsPromotion;
    }

    /***
     * 
     * 显示推广短信内容
     * 
     * @return
     */
    public String pageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            String isMenu = getRequest().getParameter("isMenu");
            if(!"true".equals(isMenu)){
            Integer count = emailSmsPromotionService.selectListPromotionCount(emailSmsPromotion);
            page.setRecordCount(count);
            emailSmsPromotion.setSkip(page.getStartIndex());
            emailSmsPromotion.setMax(page.getStartIndex() + page.getPageSize());
            List list = emailSmsPromotionService.selectListPromotion(emailSmsPromotion);
            page.setDataList(list);
            }else{
                getRequest().setAttribute("isMenu", "true");
            }
        } catch (Exception e) {
            logger.error("获取推广邮件列表异常" + e.getMessage(), e);
        }
        return "pageList";
    }

    // 进入新增页面
    public String add() {
        return "add";
    }

    // 新增方法
    @Token
    public String addPromotion() throws Exception {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            emailSmsPromotion.setCreateId(sysuser.getUserId());
            emailSmsPromotion.setCreateDate(new Date());
            // 如何选择是短信就设置短信内容
            if (emailSmsPromotion.getPromotionType() == 1) {
                emailSmsPromotion.setSmsText(emailSmsPromotion.getSmsText());
                emailSmsPromotion.setEmailContext("");
            } else {
                emailSmsPromotion.setEmailContext(emailSmsPromotion.getEmailContext());
                emailSmsPromotion.setSmsText("");
                emailSmsPromotion.setSmsType(null);
            }
            emailSmsPromotion.setPublicType(emailSmsPromotion.getPublicType());
            emailSmsPromotionService.addPromotion(emailSmsPromotion);
            this.addActionMessage(ConfigureUtils.getMessageConfig("emailSmsPrompt.add.succ"));
        } catch (SQLException e) {
            logger.error("添加推广邮件短信异常" + e.getMessage(), e);
            this.addActionMessage(ConfigureUtils.getMessageConfig("emailSmsPrompt.add.fail"));
        }
        return "addPromotionSucc";

    }

    // 进入修改
    public String preEdit() throws Exception {
        try {
            emailSmsPromotion = emailSmsPromotionService.queryemailSmsPromotion(emailSmsPromotion);
            // 得到客户类别
            Integer publicType = emailSmsPromotion.getPublicType();
            // 指定人员信息
            EmailSmsLogs smsLogs = new EmailSmsLogs();
            smsLogs.setPromotionId(emailSmsPromotion.getPromotionId());
            List list = emailSmsLogsService.getEmailSmsLogs(smsLogs);
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    EmailSmsLogs smsLogs1 = (EmailSmsLogs) list.get(i);
                    if (!"".equals(smsLogs1.getLoginName()) && smsLogs1.getLoginName() != null) {
                        if (null != loginIds && null != loginNames) {
                            loginIds += smsLogs1.getLoginId() + ",";
                            loginNames += smsLogs1.getLoginName() + ",";
                        } else {
                            loginIds = smsLogs1.getLoginId() + ",";
                            loginNames = smsLogs1.getLoginName() + ",";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("进入修改推广邮件异常" + e.getMessage(), e);
        }
        return "edit";
    }


    public String preSeach() throws Exception {
        try {
            emailSmsPromotion = emailSmsPromotionService.queryemailSmsPromotion(emailSmsPromotion);
            // 得到客户类别
            Integer publicType = emailSmsPromotion.getPublicType();
            // 指定人员信息
            EmailSmsLogs smsLogs = new EmailSmsLogs();
            smsLogs.setPromotionId(emailSmsPromotion.getPromotionId());
            List list = emailSmsLogsService.getEmailSmsLogs(smsLogs);
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    EmailSmsLogs smsLogs1 = (EmailSmsLogs) list.get(i);
                    if (null != loginIds && null != loginNames) {
                        loginIds += smsLogs1.getLoginId() + ",";
                        loginNames += smsLogs1.getLoginName() + ",";
                    } else {
                        loginIds = smsLogs1.getLoginId() + ",";
                        loginNames = smsLogs1.getLoginName() + ",";
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("查看推广邮件短信异常" + e.getMessage(), e);
        }
        return "preSeach";

    }

    // 提交审核

    public String preSubmit() throws Exception {
        try { // 修改状态
            emailSmsPromotion.setStatus(1);
            emailSmsPromotionService.updateStatus(emailSmsPromotion);
        } catch (Exception ex) {
            logger.error("提交审核异常" + ex.getMessage(), ex);
        }
        emailSmsPromotion.setStatus(null);
        return pageList();
    }


    // 修改

    public String edit() throws Exception {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            emailSmsPromotion.setCreateId(sysuser.getUserId());
            emailSmsPromotion.setCreateDate(new Date());
            emailSmsPromotion.setModifyId(sysuser.getUserId());
            emailSmsPromotion.setModifyDate(new Date());
            emailSmsPromotion.setPublicType(emailSmsPromotion.getPublicType());
            // 如何选择是短信就设置短信内容
            if (emailSmsPromotion.getPromotionType() == 1) {
                emailSmsPromotion.setSmsText(emailSmsPromotion.getSmsText());
                emailSmsPromotion.setEmailContext("");
            } else {
                emailSmsPromotion.setEmailContext(emailSmsPromotion.getEmailContext());
                emailSmsPromotion.setSmsText("");
                emailSmsPromotion.setSmsType(null);
            }
            emailSmsPromotionService.updateEmailSmsPromotion(emailSmsPromotion, loginIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("emailSmsPrompt.update.succ"));
        } catch (SQLException e) {
            logger.error("修改推广邮件短信失败" + e.getMessage(), e);
            this.addActionMessage(ConfigureUtils.getMessageConfig("emailSmsPrompt.update.fail"));
        }
        return "editSuccess";
    }

    // 删除操作
    public String delete() throws Exception {
        try {
            Integer promotionId = emailSmsPromotion.getPromotionId();
            if (null != promotionId) {
                // 删除推荐短信
                emailSmsPromotionService.deleteById(promotionId);
                // 删除推荐短信邮件记录
                emailSmsLogsService.deleteSmsLog(promotionId);
            } else {
                emailSmsPromotionService.delete(promotionIds);
            }
            this.addActionMessage(ConfigureUtils.getMessageConfig("emailSmsPrompt.del.succ"));
        } catch (SQLException e) {
            logger.error("删除推广邮件短信失败" + e.getMessage(), e);
            this.addActionMessage(ConfigureUtils.getMessageConfig("emailSmsPrompt.del.fail"));
        }
        return pageList();
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public EmailSmsPromotion getEmailSmsPromotion() {
        return emailSmsPromotion;
    }

    public void setEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) {
        this.emailSmsPromotion = emailSmsPromotion;
    }

    public void setEmailSmsPromotionService(EmailSmsPromotionService emailSmsPromotionService) {
        this.emailSmsPromotionService = emailSmsPromotionService;
    }

    public String getLoginIds() {
        return loginIds.substring(0, loginIds.length() - 1);
    }

    public void setLoginIds(String loginIds) {
        this.loginIds = loginIds;
    }

    public void setEmailSmsLogsService(EmailSmsLogsService emailSmsLogsService) {
        this.emailSmsLogsService = emailSmsLogsService;
    }

    public String getLoginNames() {
        return loginNames.substring(0, loginNames.length() - 1);
    }

    public void setLoginNames(String loginNames) {
        this.loginNames = loginNames;
    }
}
