package com.pltfm.userquartz;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.service.EmailSmsLogsService;
import com.pltfm.app.service.EmailSmsPromotionService;
import com.pltfm.app.service.MessageInfoService;
import com.pltfm.app.vobject.EmailSmsLogs;
import com.pltfm.app.vobject.EmailSmsPromotion;

import redis.clients.jedis.JedisCluster;

/* 删除邮件业务 import com.kmzyc.mailmobile.service.EmailSubscriptionRemoteService; */

/***
 * 
 * 处理定时发送邮件短信任务
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Component(value = "emailSmsSendJob")
@Scope(value = "prototype")
public class EmailSmsSendJob extends ActionSupport {
    @Resource(name = "emailSmsLogsService")
    private EmailSmsLogsService emailSmsLogsService;
    @Resource(name = "emailSmsPromotionService")
    private EmailSmsPromotionService emailSmsPromotionService;

    /*
     * 删除邮件业务 @Autowired private EmailSubscriptionRemoteService emailSubscriptionRemoteService;
     */

    @Resource
    private MessageInfoService messageInfoService;

    @Resource
    private JedisCluster jedis;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


    private static Logger logger = LoggerFactory.getLogger(EmailSmsSendJob.class);


    /****
     * 定时执行发送短信，邮件
     * 
     * @return
     */
    public String executeEmailSmsSend() {

        String key = "com.km.user.executeEmailSmsSend".concat(simpleDateFormat.format(new Date()));
        // 处理集群下多次执行定时器问题,如果返回false直接退出
        /*
         * if(!redisTemplate.setNxEx(key, "1", 12*60*60)){ return null; }
         */
        Long re = jedis.setnx(key, "1");
        if (re <= 0) {
            return null;
        }

        EmailSmsPromotion emailSmsPromotion = new EmailSmsPromotion();
        emailSmsPromotion.setIsTime(1);
        emailSmsPromotion.setStatus(3);
        Integer promotion = null;
        EmailSmsPromotion emailSmsPromotion1 = null;
        Integer channelId = null;
        try {
            // 获取所有定时发送短信邮件的数据
            List<EmailSmsPromotion> emailSmsList =
                    emailSmsPromotionService.getListEmailSmsIsTime(emailSmsPromotion);
            if (emailSmsList != null && emailSmsList.size() > 0) {
                // 循环遍历定时发送短信邮件数据
                for (int i = 0; i < emailSmsList.size(); i++) {
                    emailSmsPromotion1 = emailSmsList.get(i);
                    promotion = emailSmsPromotion1.getPromotionType();
                    channelId = emailSmsPromotion1.getChannelId();


                    // 推广形式为短信的
                    if (promotion != null && promotion == 1) {
                        // 获取推广ID
                        Integer promotionId = emailSmsPromotion1.getPromotionId();
                        // 获取短信内容
                        String smsContext = emailSmsPromotion1.getSmsText();
                        Date SendDate = emailSmsPromotion1.getTimingDate();
                        DateFormat d1 = DateFormat.getDateInstance();
                        String str1 = d1.format(SendDate);
                        // 获取短信类型
                        Integer smsType = emailSmsPromotion1.getSmsType();
                        // 获取所有发送推广短信记录数据
                        EmailSmsLogs smsLogs = new EmailSmsLogs();
                        smsLogs.setPromotionId(promotionId);
                        List<EmailSmsLogs> emailSmsLogsList =
                                emailSmsLogsService.getEmailSmsLogs(smsLogs);
                        List<Long> uidLists = new ArrayList<Long>();
                        List<String> mobileList = new ArrayList<String>();
                        if (emailSmsLogsList != null && emailSmsLogsList.size() > 0) {
                            for (int j = 0; j < emailSmsLogsList.size(); j++) {
                                EmailSmsLogs smsLogs1 = emailSmsLogsList.get(j);
                                Integer loginId = smsLogs1.getLoginId();
                                Long uid = Long.parseLong(loginId.toString());
                                String mobile = smsLogs1.getMobile();
                                // 添加判断手机号码为空的情况
                                if (mobile != null) {
                                    Pattern mobilePattern = Pattern
                                            .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
                                    Matcher mobileMatcher = mobilePattern.matcher(mobile);
                                    if (mobileMatcher.matches()) {
                                        mobileList.add(mobile);
                                        uidLists.add(uid);
                                    } else {
                                        EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                        emailLogs2.setLoginId(loginId);
                                        emailLogs2.setStatus(2);
                                        emailLogs2.setMark("手机不符合发送条件");
                                        emailLogs2.setPromotionId(promotionId);
                                        emailLogs2.setSendDate(new Date());
                                        emailSmsLogsService.updateSendStatus(emailLogs2);
                                        emailSmsPromotion1.setStatus(4);
                                        emailSmsPromotionService.updateStatus(emailSmsPromotion1);
                                    }
                                } else {
                                    EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                    emailLogs2.setLoginId(loginId);
                                    emailLogs2.setStatus(2);
                                    emailLogs2.setMark("手机不符合发送条件");
                                    emailLogs2.setPromotionId(promotionId);
                                    emailLogs2.setSendDate(new Date());
                                    emailSmsLogsService.updateSendStatus(emailLogs2);
                                    emailSmsPromotion1.setStatus(4);
                                    emailSmsPromotionService.updateStatus(emailSmsPromotion1);
                                }
                            }
                        }
                        // 定时发送时间为每天早上8：00
                        // 判断定时发送时间
                        Date now = new Date();
                        String str2 = d1.format(now);
                        if (str1.equals(str2)) {
                            // 发送短信....
                            // 调用远程接口
                            if (uidLists != null && mobileList != null) {
                                Map<String, Object> sendMap1 = messageInfoService.sendMsgBySpread(
                                        uidLists, mobileList, smsContext, smsType, channelId);

                                for (int k = 0; k < mobileList.size(); k++) {
                                    String status = (sendMap1.get(mobileList.get(k)).toString());
                                    Long uid = uidLists.get(k);
                                    Integer uids = Integer.parseInt(uid.toString());
                                    if (status.equals("0")) {
                                        EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                        emailLogs2.setLoginId(uids);
                                        emailLogs2.setStatus(0);
                                        emailLogs2.setMark("短信发送失败");
                                        emailLogs2.setPromotionId(promotionId);
                                        emailLogs2.setSendDate(new Date());
                                        emailSmsLogsService.updateSendStatus(emailLogs2);
                                    } else if (status.equals("1")) {
                                        EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                        emailLogs2.setLoginId(uids);
                                        emailLogs2.setStatus(1);
                                        emailLogs2.setMark("短信发送成功");
                                        emailLogs2.setPromotionId(promotionId);
                                        emailLogs2.setSendDate(new Date());
                                        emailSmsLogsService.updateSendStatus(emailLogs2);
                                    } else if (status.equals("2")) {
                                        EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                        emailLogs2.setLoginId(uids);
                                        emailLogs2.setStatus(2);
                                        emailLogs2.setMark("短信未发送");
                                        emailLogs2.setPromotionId(promotionId);
                                        emailLogs2.setSendDate(new Date());
                                        emailSmsLogsService.updateSendStatus(emailLogs2);
                                    } else {
                                        EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                                        emailLogs2.setLoginId(uids);
                                        emailLogs2.setStatus(4);
                                        emailLogs2.setMark("短信发送异常");
                                        emailLogs2.setPromotionId(promotionId);
                                        emailLogs2.setSendDate(new Date());
                                        emailSmsLogsService.updateSendStatus(emailLogs2);
                                    }
                                    // 定时发送完成修改状态为已发布
                                    emailSmsPromotion1.setStatus(4);
                                    emailSmsPromotionService.updateStatus(emailSmsPromotion1);
                                } // for循环结束
                            } // 判断List不为空
                        } // 判断定时时间结束
                    } /*
                       * 删除邮件业务 else { // 获取推广ID Integer promotionId =
                       * emailSmsPromotion1.getPromotionId(); // 获取推广标题 String title =
                       * emailSmsPromotion1.getTitle(); // 获取邮件内容 String emailContext =
                       * emailSmsPromotion1.getEmailContext(); Date SendDate =
                       * emailSmsPromotion1.getTimingDate(); DateFormat d1 =
                       * DateFormat.getDateInstance(); String str1 = d1.format(SendDate); //
                       * 获取所有发送推广邮件记录数据 EmailSmsLogs smsLogs = new EmailSmsLogs();
                       * smsLogs.setPromotionId(promotionId); List<EmailSmsLogs> emailSmsLogsList1 =
                       * emailSmsLogsService.getEmailSmsLogs(smsLogs); List<String> loginNames1 =
                       * new ArrayList<String>(); List<String> emailLists = new ArrayList<String>();
                       * List<Long> uidLists = new ArrayList<Long>(); if (emailSmsLogsList1 != null
                       * && emailSmsLogsList1.size() > 0) { for (int j = 0; j <
                       * emailSmsLogsList1.size(); j++) { EmailSmsLogs smsLogs1 = (EmailSmsLogs)
                       * emailSmsLogsList1.get(j); Integer loginId = smsLogs1.getLoginId(); String
                       * uids = loginId.toString(loginId); String loginName =
                       * smsLogs1.getLoginName(); String email = smsLogs1.getEmail(); Long uid =
                       * Long.valueOf(uids).longValue(); if (email != null) { Pattern emailPattern =
                       * Pattern.compile(
                       * "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"
                       * ); Matcher emailMatcher = emailPattern.matcher(email); if
                       * (emailMatcher.matches()) { // 添加loginName到集合 loginNames1.add(loginName); //
                       * 添加邮件到集合 emailLists.add(email); // 添加用户到集合 uidLists.add(uid); } else {
                       * EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                       * emailLogs2.setLoginId(loginId); emailLogs2.setStatus(2);
                       * emailLogs2.setMark("邮件不符合发送条件"); emailLogs2.setPromotionId(promotionId);
                       * emailLogs2.setSendDate(new Date());
                       * emailSmsLogsService.updateSendStatus(emailLogs2);
                       * emailSmsPromotion1.setStatus(4);
                       * emailSmsPromotionService.updateStatus(emailSmsPromotion1); } } else {
                       * EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                       * emailLogs2.setLoginId(loginId); emailLogs2.setStatus(2);
                       * emailLogs2.setMark("邮件不符合发送条件"); emailLogs2.setPromotionId(promotionId);
                       * emailLogs2.setSendDate(new Date());
                       * emailSmsLogsService.updateSendStatus(emailLogs2);
                       * emailSmsPromotion1.setStatus(4);
                       * emailSmsPromotionService.updateStatus(emailSmsPromotion1); } } } //
                       * 定时发送时间为每天早上9：00 // 判断定时发送时间 // 获取系统当前时间 Date now = new Date(); String str2
                       * = d1.format(now); // 匹配数据库中定时发放的时间 if (str1.equals(str2)) { // 调用远程接口
                       * Map<String, Object> sendEmailMap = emailSubscriptionRemoteService
                       * .emailBySpread(uidLists, emailLists, loginNames1, title, emailContext,
                       * channelId);
                       * 
                       * // 发送邮件接口 for (int k = 0; k < emailLists.size(); k++) { Long uid =
                       * uidLists.get(k).longValue(); Integer uids =
                       * Integer.parseInt(uid.toString()); String status =
                       * (sendEmailMap.get(emailLists.get(k)).toString()); if (status.equals("0")) {
                       * EmailSmsLogs emailLogs2 = new EmailSmsLogs(); emailLogs2.setLoginId(uids);
                       * emailLogs2.setStatus(1); emailLogs2.setMark("邮件发送失败");
                       * emailLogs2.setPromotionId(promotionId); emailLogs2.setSendDate(new Date());
                       * emailSmsLogsService.updateSendStatus(emailLogs2); } else if
                       * (status.equals("1")) { EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                       * emailLogs2.setLoginId(uids); emailLogs2.setStatus(1);
                       * emailLogs2.setMark("邮件发送成功"); emailLogs2.setPromotionId(promotionId);
                       * emailLogs2.setSendDate(new Date());
                       * emailSmsLogsService.updateSendStatus(emailLogs2); } else if
                       * (status.equals("2")) { EmailSmsLogs emailLogs2 = new EmailSmsLogs();
                       * emailLogs2.setLoginId(uids); emailLogs2.setStatus(2);
                       * emailLogs2.setMark("邮件未发送"); emailLogs2.setPromotionId(promotionId);
                       * emailLogs2.setSendDate(new Date());
                       * emailSmsLogsService.updateSendStatus(emailLogs2); } else { EmailSmsLogs
                       * emailLogs2 = new EmailSmsLogs(); emailLogs2.setLoginId(uids);
                       * emailLogs2.setStatus(4); emailLogs2.setMark("邮件发送异常");
                       * emailLogs2.setPromotionId(promotionId); emailLogs2.setSendDate(new Date());
                       * emailSmsLogsService.updateSendStatus(emailLogs2); } // 定时发送完成修改状态为已发布
                       * emailSmsPromotion1.setStatus(4);
                       * emailSmsPromotionService.updateStatus(emailSmsPromotion1); } // for结束 } //
                       * 判断时间结束 }
                       */
                }
            } // else
        } catch (Exception ex) {
            logger.error("定时发送推广短信邮件异常" + ex.getMessage(), ex);
        }
        return null;
    }

    public EmailSmsLogsService getEmailSmsLogsService() {
        return emailSmsLogsService;
    }

    public void setEmailSmsLogsService(EmailSmsLogsService emailSmsLogsService) {
        this.emailSmsLogsService = emailSmsLogsService;
    }

    public EmailSmsPromotionService getEmailSmsPromotionService() {
        return emailSmsPromotionService;
    }

    public void setEmailSmsPromotionService(EmailSmsPromotionService emailSmsPromotionService) {
        this.emailSmsPromotionService = emailSmsPromotionService;
    }
}
