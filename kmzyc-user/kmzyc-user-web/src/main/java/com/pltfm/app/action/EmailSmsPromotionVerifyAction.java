package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.EmailSmsLogsService;
import com.pltfm.app.service.EmailSmsPromotionService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.service.MessageInfoService;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.EmailSmsLogs;
import com.pltfm.app.vobject.EmailSmsPromotion;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.sys.model.SysUser;

/* 删除邮件业务 import com.kmzyc.mailmobile.service.EmailSubscriptionRemoteService; */



/***
 * 
 * 推广邮件短信审核功能
 * 
 * @author luotao
 * 
 */

@Scope("prototype")
@Component(value = "emailSmsPromotionVerifyAction")
public class EmailSmsPromotionVerifyAction extends BaseAction implements ModelDriven {
    @Resource(name = "loginInfoService")
    private LoginInfoService loginInfoService;
    @Resource(name = "emailSmsLogsService")
    private EmailSmsLogsService emailSmsLogsService;

    @Resource
    private MessageInfoService messageInfoService;

    // 中药材
    // private static final String
    private static Logger logger = LoggerFactory.getLogger(EmailSmsPromotionVerifyAction.class);

    public EmailSmsLogsService getEmailSmsLogsService() {
        return emailSmsLogsService;
    }

    public void setEmailSmsLogsService(EmailSmsLogsService emailSmsLogsService) {
        this.emailSmsLogsService = emailSmsLogsService;
    }

    // 用户信息
    String loginIds;
    String loginNames;
    // 推广邮件实体
    private EmailSmsPromotion emailSmsPromotion;

    @Override
    public Object getModel() {
        emailSmsPromotion = new EmailSmsPromotion();
        return emailSmsPromotion;
    }

    /**
     * 分页类
     */
    private Page page;
    /**
     * 推广邮件业务逻辑接口
     */
    @Resource(name = "emailSmsPromotionService")
    private EmailSmsPromotionService emailSmsPromotionService;

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public String pageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            String isMenu = getRequest().getParameter("isMenu");
            if (!"true".equals(isMenu)) {
                emailSmsPromotion.setStatus(1);
                Integer count =
                        emailSmsPromotionService.selectListPromotionCount(emailSmsPromotion);
                page.setRecordCount(count);
                emailSmsPromotion.setSkip(page.getStartIndex());
                emailSmsPromotion.setMax(page.getEndIndex());
                List list = emailSmsPromotionService.selectListPromotion(emailSmsPromotion);
                page.setDataList(list);
            } else {
                getRequest().setAttribute("isMenu", "true");
            }
        } catch (Exception e) {
            logger.error("获取推广邮件审核列表异常" + e.getMessage(), e);
        }
        return "pageList";
    }


    public String preVerify() throws Exception {
        try {
            emailSmsPromotion = emailSmsPromotionService.queryemailSmsPromotion(emailSmsPromotion);
            // 得到客户类别
            Integer customerType = emailSmsPromotion.getPublicType();
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
            logger.error("获取查看邮件审核列表异常" + e.getMessage(), e);
        }
        return "preVerify";
    }

    public String preSerch() throws Exception {
        try {
            emailSmsPromotion = emailSmsPromotionService.queryemailSmsPromotion(emailSmsPromotion);
            // 得到客户类别
            Integer customerType = emailSmsPromotion.getPublicType();
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
            logger.error("获取查看邮件审核列表异常" + e.getMessage(), e);
        }
        return "preSerch";
    }

    // 直接提交审核（同意）
    @Token
    public String applyVerifyAgree() throws Exception {
        emailSmsPromotion = emailSmsPromotionService.queryemailSmsPromotion(emailSmsPromotion);
        emailSmsPromotion.setStatus(3);
        return EditVerifyStatus();
    }

    // 直接提交审核（拒绝）
    @Token
    public String applyVerifyRefuse() throws Exception {
        emailSmsPromotion = emailSmsPromotionService.queryemailSmsPromotion(emailSmsPromotion);
        emailSmsPromotion.setStatus(2);
        return EditVerifyStatus();
    }

    // 提交审核状态

    @Token
    public String EditVerifyStatus() {
        try {
            // 当审核不通过的时候
            if (emailSmsPromotion.getStatus() == 2) {
                // 修改审核状态为审核不通过
                emailSmsPromotion.setStatus(5);
                emailSmsPromotionService.updateStatus(emailSmsPromotion);
            } else if (emailSmsPromotion.getIsTime() == 1) // 判断是否定时发送,如果等于定时发送
            {
                emailSmsPromotion.setStatus(3);
                emailSmsPromotionService.updateStatus(emailSmsPromotion);
            } else {
                // 设置状态为已发布状态
                emailSmsPromotion.setStatus(4);
                emailSmsPromotionService.updateStatus(emailSmsPromotion);
            }
            // 如果发布对象为所有，生成数据到emailSmsLogs
            if (emailSmsPromotion.getStatus() == 4 || emailSmsPromotion.getStatus() == 3) {
                // 获取所有手机号码不能为空的数据
                if (emailSmsPromotion.getPublicType() == 1) {
                    List list = loginInfoService.getAllMobileUser();
                    for (int i = 0; i < list.size(); i++) {
                        EmailSmsLogs smsLogs = new EmailSmsLogs();
                        LoginInfo loginInfo = (LoginInfo) list.get(i);
                        smsLogs = new EmailSmsLogs();
                        smsLogs.setEmail(loginInfo.getEmail());
                        smsLogs.setMobile(loginInfo.getMobile());
                        smsLogs.setLoginId(loginInfo.getN_LoginId());
                        SysUser sysuser = (SysUser) session.get("sysUser");
                        smsLogs.setCreatedId(sysuser.getUserId());
                        smsLogs.setCreatedDate(new Date());
                        smsLogs.setModifyId(sysuser.getUserId());
                        smsLogs.setModifyDate(new Date());
                        smsLogs.setPromotionId(emailSmsPromotion.getPromotionId());
                        emailSmsLogsService.insert(smsLogs);
                    }
                }
            }
            // 判断推广方式为短信
            if (emailSmsPromotion.getPromotionType() == 1 && emailSmsPromotion.getStatus() != 0
                    && emailSmsPromotion.getIsTime() != 1) {// 处理推广形式为短信的
                if (emailSmsPromotion.getStatus() == 4 && emailSmsPromotion.getIsTime() == 0) {
                    // 指定人员信息全部用户通道
                    EmailSmsLogs smsLogs = new EmailSmsLogs();
                    smsLogs.setPromotionId(emailSmsPromotion.getPromotionId());
                    List list1 = emailSmsLogsService.getEmailSmsLogs(smsLogs);
                    String smsText = "";
                    Integer smsType = null;
                    Integer promotionId = null;
                    List<Long> uidLists = new ArrayList<Long>();
                    List<String> mobileList = new ArrayList<String>();
                    if (null != list1 && list1.size() > 0) {
                        for (int i = 0; i < list1.size(); i++) {
                            EmailSmsLogs smsLogs1 = (EmailSmsLogs) list1.get(i);
                            Integer loginId = smsLogs1.getLoginId();
                            Long uid = Long.parseLong(loginId.toString());
                            String mobile = smsLogs1.getMobile();
                            // 判断手机号码为空的情况
                            if (mobile != null) {
                                Pattern mobilePattern = Pattern
                                        .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
                                Matcher mobileMatcher = mobilePattern.matcher(mobile);
                                if (mobileMatcher.matches()) {
                                    promotionId = emailSmsPromotion.getPromotionId();
                                    smsText = emailSmsPromotion.getSmsText();
                                    smsType = emailSmsPromotion.getSmsType();
                                    mobileList.add(mobile);
                                    uidLists.add(uid);
                                } else {
                                    EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                    emailLogs2.setLoginId(loginId);
                                    emailLogs2.setStatus(2);
                                    emailLogs2.setMark("手机不符合发送条件");
                                    emailLogs2.setPromotionId(emailSmsPromotion.getPromotionId());
                                    emailLogs2.setSendDate(new Date());
                                    emailSmsLogsService.updateSendStatus(emailLogs2);
                                }
                            } else {
                                EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                emailLogs2.setLoginId(loginId);
                                emailLogs2.setStatus(2);
                                emailLogs2.setMark("手机不符合发送条件");
                                emailLogs2.setPromotionId(emailSmsPromotion.getPromotionId());
                                emailLogs2.setSendDate(new Date());
                                emailSmsLogsService.updateSendStatus(emailLogs2);
                            }
                        } // for循环结束
                    }
                    // 调用远程接口判断List是否为空的情况
                    if (uidLists != null && mobileList != null) {
                        Integer channelId = emailSmsPromotion.getChannelId();
                        // 发送短信
                        Map<String, Object> sendMap = messageInfoService.sendMsgBySpread(uidLists,
                                mobileList, smsText, smsType, channelId);
                        for (int i = 0; i < mobileList.size(); i++) {
                            String status = (sendMap.get(mobileList.get(i)).toString());
                            Long uid = uidLists.get(i).longValue();
                            Integer uids = Integer.parseInt(uid.toString());
                            switch (status) {
                                case "0": {
                                    EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                    emailLogs2.setLoginId(uids);
                                    emailLogs2.setStatus(0);
                                    emailLogs2.setMark("短信发送失败");
                                    emailLogs2.setPromotionId(promotionId);
                                    emailLogs2.setSendDate(new Date());
                                    emailSmsLogsService.updateSendStatus(emailLogs2);
                                    break;
                                }
                                case "1": {
                                    EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                    emailLogs2.setLoginId(uids);
                                    emailLogs2.setStatus(1);
                                    emailLogs2.setMark("短信发送成功");
                                    emailLogs2.setPromotionId(promotionId);
                                    emailLogs2.setSendDate(new Date());
                                    emailSmsLogsService.updateSendStatus(emailLogs2);
                                    break;
                                }
                                case "2": {
                                    EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                    emailLogs2.setLoginId(uids);
                                    emailLogs2.setStatus(2);
                                    emailLogs2.setMark("短信未发送");
                                    emailLogs2.setPromotionId(promotionId);
                                    emailLogs2.setSendDate(new Date());
                                    emailSmsLogsService.updateSendStatus(emailLogs2);
                                    break;
                                }
                                default: {
                                    EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                    emailLogs2.setLoginId(uids);
                                    emailLogs2.setStatus(4);
                                    emailLogs2.setMark("短信发送异常");
                                    emailLogs2.setPromotionId(promotionId);
                                    emailLogs2.setSendDate(new Date());
                                    emailSmsLogsService.updateSendStatus(emailLogs2);
                                    break;
                                }
                            }
                        } // for循环结束
                    }
                }
            }
            // 否则处理发送邮件的情况
            /*
             * 删除邮件业务 else { // 判断推广形式为邮件的，审核状态为已拒绝的 if (emailSmsPromotion.getPromotionType() == 2
             * && emailSmsPromotion.getStatus() != 0 && emailSmsPromotion.getIsTime() != 1) { //
             * 处理推广形式邮件的 if (emailSmsPromotion.getStatus() == 4 && emailSmsPromotion.getIsTime() ==
             * 0) { // 指定人员信息 EmailSmsLogs smsLogs = new EmailSmsLogs();
             * smsLogs.setPromotionId(emailSmsPromotion.getPromotionId()); List list1 =
             * emailSmsLogsService.getEmailSmsLogs(smsLogs); List<String> loginNames1 = new
             * ArrayList<String>(); List<String> emailLists = new ArrayList<String>(); List<Long>
             * uidLists = new ArrayList<Long>(); String title = ""; String emailContext1 = "";
             * Integer promotionId = null; if (null != list1 && list1.size() > 0) { for (int i = 0;
             * i < list1.size(); i++) { EmailSmsLogs smsLogs1 = (EmailSmsLogs) list1.get(i); Integer
             * loginId = smsLogs1.getLoginId(); String uids = loginId.toString(loginId); String
             * loginName = smsLogs1.getLoginName(); emailContext1 =
             * emailSmsPromotion.getEmailContext(); title = emailSmsPromotion.getTitle();
             * promotionId = smsLogs1.getPromotionId(); Long uid = Long.valueOf(uids).longValue();
             * String email = smsLogs1.getEmail(); if (email != null) { Pattern emailPattern =
             * Pattern.compile(
             * "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"
             * ); Matcher emailMatcher = emailPattern.matcher(email); if (emailMatcher.matches()) {
             * // 添加loginName到集合 loginNames1.add(loginName); // 添加邮件到集合 emailLists.add(email); //
             * 添加用户到集合 uidLists.add(uid); } else { EmailSmsLogs emailLogs2 = new EmailSmsLogs();
             * emailLogs2.setLoginId(loginId); emailLogs2.setStatus(2);
             * emailLogs2.setMark("邮件不符合发送条件"); emailLogs2.setPromotionId(promotionId);
             * emailLogs2.setSendDate(new Date()); emailSmsLogsService.updateSendStatus(emailLogs2);
             * } // 为空的情况修改状态直接不调用发送邮件接口 } else { EmailSmsLogs emailLogs2 = new EmailSmsLogs();
             * emailLogs2.setLoginId(loginId); emailLogs2.setStatus(2);
             * emailLogs2.setMark("邮件不符合发送条件"); emailLogs2.setPromotionId(promotionId);
             * emailLogs2.setSendDate(new Date()); emailSmsLogsService.updateSendStatus(emailLogs2);
             * } } // for循环结束 } // 判断集合是否为空的情况 if (uidLists != null && emailLists != null &&
             * loginNames1 != null) { // 调用远程接口 Integer channelId =
             * emailSmsPromotion.getChannelId(); Map<String, Object> sendEmailMap =
             * emailSubscriptionRemoteService.emailBySpread(uidLists, emailLists, loginNames1,
             * title, emailContext1, channelId);
             * 
             * for (int i = 0; i < emailLists.size(); i++) { Long uid = uidLists.get(i).longValue();
             * Integer uids = Integer.parseInt(uid.toString()); String status =
             * (sendEmailMap.get(emailLists.get(i)).toString()); if (status.equals("0")) {
             * EmailSmsLogs emailLogs2 = new EmailSmsLogs(); emailLogs2.setLoginId(uids);
             * emailLogs2.setStatus(1); emailLogs2.setMark("邮件发送失败");
             * emailLogs2.setPromotionId(promotionId); emailLogs2.setSendDate(new Date());
             * emailSmsLogsService.updateSendStatus(emailLogs2); } else if (status.equals("1")) {
             * EmailSmsLogs emailLogs2 = new EmailSmsLogs(); emailLogs2.setLoginId(uids);
             * emailLogs2.setStatus(1); emailLogs2.setMark("邮件发送成功");
             * emailLogs2.setPromotionId(promotionId); emailLogs2.setSendDate(new Date());
             * emailSmsLogsService.updateSendStatus(emailLogs2); } else if (status.equals("2")) {
             * EmailSmsLogs emailLogs2 = new EmailSmsLogs(); emailLogs2.setLoginId(uids);
             * emailLogs2.setStatus(2); emailLogs2.setMark("邮件未发送");
             * emailLogs2.setPromotionId(promotionId); emailLogs2.setSendDate(new Date());
             * emailSmsLogsService.updateSendStatus(emailLogs2); } else { EmailSmsLogs emailLogs2 =
             * new EmailSmsLogs(); emailLogs2.setLoginId(uids); emailLogs2.setStatus(4);
             * emailLogs2.setMark("邮件发送异常"); emailLogs2.setPromotionId(promotionId);
             * emailLogs2.setSendDate(new Date()); emailSmsLogsService.updateSendStatus(emailLogs2);
             * } } // for循环结束 } // 判断集合为空的结束 } } }
             */
        } catch (Exception e) {
            logger.error("推广邮件审核异常" + e.getMessage(), e);
        }
        emailSmsPromotion.setTitle("");
        emailSmsPromotion.setPromotionType(null);
        return pageList();
    }

    public void setEmailSmsPromotionService(EmailSmsPromotionService emailSmsPromotionService) {
        this.emailSmsPromotionService = emailSmsPromotionService;
    }

    public EmailSmsPromotion getEmailSmsPromotion() {
        return emailSmsPromotion;
    }

    public void setEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) {
        this.emailSmsPromotion = emailSmsPromotion;
    }

    public EmailSmsPromotionService getEmailSmsPromotionService() {
        return emailSmsPromotionService;
    }

    public LoginInfoService getLoginInfoService() {
        return loginInfoService;
    }

    public void setLoginInfoService(LoginInfoService loginInfoService) {
        this.loginInfoService = loginInfoService;
    }

    public String getLoginIds() {
        return loginIds.substring(0, loginIds.length() - 1);
    }

    public void setLoginIds(String loginIds) {
        this.loginIds = loginIds;
    }

    public String getLoginNames() {
        return loginNames.substring(0, loginNames.length() - 1);
    }

    public void setLoginNames(String loginNames) {
        this.loginNames = loginNames;
    }
}
