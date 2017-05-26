package com.kmzyc.b2b.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.action.common.AbstractCommonAction;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.util.StringUtil;

@Controller("mobileVaildCodeAction")
@Scope("prototype")
public class MobileVaildCodeAction extends AbstractCommonAction {
    private static final long serialVersionUID = 1L;

    // 短信校验有效时间
    @Value("${b2b_common_msg_valid_time}")
    private int messageValidTime;

    // 短信校验前台有效时间
    @Value("${b2b_common_page_msg_valid_time}")
    private int messagePageValidTime;

    private String msgCode;// 邮箱、手机验证码
    /** 发送校验信息的来源，1 重置密码，2免注册转会员 */
    private String sendSource;
    private String mobile;// 手机号码
    private String mobileKey;// 手机验证码键
    private String mobileValidDateKey;// 手机验证有效时间键

    private String sendWay;// add by hl,注册的区别

    @Resource(name = "resetPwdServiceImpl")
    private ResetPwdService resetPwdService;


    /**
     * 通用发送短信验证码 支付验证码、手机验证、WAP手机验证、wap支付验证码
     * 
     * @return
     * @throws Exception
     */
    public String sendMobile() throws Exception {
        try {
            long uid = getAttribute(Constants.SESSION_USER_ID);

            String url = getReferUrl();
            if (illegalReferUrl(url, mobile) || illegalPayVerifyPwd(uid, url, mobile) // 为支付密码的才需要验证
                    || illegalSendMessageTimeOnMobile(mobile)
                    || abnormalSendMessageByMobile(mobile)) {
                return null;
            }

            // 去掉语音验证
            /*
             * if (containsAny(url, "goPayVerifyPassword.action", "goWapPayVerifyPassword.action",
             * "postMobileVerifyPassword.action", "goWapVerifyMobile.action")) { sendSource =
             * "voice"; }
             */

            if (!StringUtil.isEmpty(sendSource) && ("wapRegist".equals(sendWay)
                    || "CMSPhoneRegist".equals(sendWay) || "phoneRegist".equals(sendWay))) { // 手机注册
                avoidSendAgain();

                sendMobileVerifyCode();
            }

            if (!StringUtil.isEmpty(sendSource) && !"wapRegist".equals(sendSource)) {
                User tempUser = resetPwdService.getUserByLoginId(uid);
                if (null != tempUser && !StringUtil.isEmpty(tempUser.getMobile())
                        && StringUtil.isEmpty(mobile)) {
                    mobile = tempUser.getMobile();
                }

                // 避免短信重复发送
                if (avoidSendAgain()) {
                    return null;
                }

                sendMobileVerifyCode();
            }
        } catch (Exception e) {
            logger.error("忘记密码发送短信存在异常：" + e.getMessage(), e);
        }

        mobileValidDateKey = null;
        mobile = null;

        return null;
    }

    /**
     * 避免重复发送
     * 
     * @return
     * @throws IOException
     */
    private boolean avoidSendAgain() throws IOException {
        if (StringUtil.isEmpty(mobileValidDateKey)) {
            mobileValidDateKey = "MOBILE-VALID-DATE";
        }

        Date date = getAttribute(mobileValidDateKey);
        // 避免重复发送
        if (null != date && (System.currentTimeMillis() - date.getTime()) < (messagePageValidTime
                * 60 * 1000)) {
            printJson("0");
            return true;
        }

        return false;
    }

    /**
     * 为支付密码的才需要验证
     * 
     * @param uid
     * @param url
     * @param mobile
     * @return
     * @throws Exception
     */
    private boolean illegalPayVerifyPwd(long uid, String url, String mobile) throws Exception {
        if (containsAny(url, "goPayVerifyPassword.action", "goWapPayVerifyPassword.action")) {
            User user = resetPwdService.getUserByLoginId(uid);
            if (null != user && !user.getMobile().equals(mobile)) {
                logger.info("非正常来源 ,传值： " + mobile + " 实际：" + user.getMobile());
                printJson("0");
                return true;
            }
        }

        return false;
    }

    /**
     * 通用校验手机验证码
     * 
     * @return
     * @throws Exception
     */
    public String validMobile() throws Exception {
        if (StringUtil.isEmpty(mobileValidDateKey)) {
            mobileValidDateKey = "MOBILE-VALID-DATE";
        }
        String code = getAttribute(mobileKey);
        Date date = getAttribute(mobileValidDateKey);
        if (null != date && !StringUtil.isEmpty(msgCode) && msgCode.equals(code)) {
            if ((System.currentTimeMillis() - date.getTime()) > messageValidTime * 60 * 1000l) {
                printJson("0");
            } else {
                printJson("1");
            }
        }
        return null;
    }

    /**
     * wap发送短信
     * 
     * @return
     */
    public String wapSendValidInfo() {
        String info = "";
        removeAttribute("B2B_FINDPWD");
        if (mobile != null) {
            try {
                String url = getReferUrl();
                if (url.indexOf("forgetPassword.jsp") > 0
                        && illegalGraphValidCode(getParameter("sessionCode"))
                        || illegalReferUrl(url, mobile) || illegalSendMessageTimeOnMobile(mobile)
                        || abnormalSendMessageByMobile(mobile)) {
                    return null;
                }

                boolean result = false;
                // 获取短信内容
                MessageTypeEnum messageType = getMessageType(sendSource);
                if (messageType != null) {
                    result = messageRemoteService.sendMobileVerifyCode(mobile, getSession(),
                            messageType.getStatus());
                }
                if (result) {
                    String mCode = getAttribute("OrderTrailMobileVerifyCode");
                    logger.info("wap端忘记密码发送短信码成功，发送校验码为：" + mCode);
                    setAttribute("RESET-PWD-VALID-CODE", mCode);
                    setAttribute("RESET-MOBILE", mobile);
                    setAttribute("RESET-PWD-VALID-DATE", new Date());
                }

                setAttribute("B2B_FINDPWD", "1");
                info = "{\"msg\":\"1\"}";
            } catch (Exception e) {
                logger.error("web页忘记密码发送短信存在异常：" + e.getMessage(), e);
            }
            try {
                printJson(info);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return ERROR;
            }
        }
        return null;
    }

    /**
     * wap注册发送短信验证码
     * 
     * @return
     * @throws Exception
     */
    public String wapsendMobile() throws Exception {
        try {
            String url = getReferUrl();
            if (containsAny(url, "loginPage.htm")) {
                HttpSession session = ServletActionContext.getRequest().getSession();
                mobile = (String) session.getAttribute(Constants.SESSION_KM_MOBILE);
            }
            if (illegalReferUrl(url, mobile) || illegalSendMessageTimeOnMobile(mobile)
                    || abnormalSendMessageByMobile(mobile)
                    || illegalGraphValidCode(getParameter("veCode"))) {
                return null;
            }

            if (!StringUtil.isEmpty(sendSource)
                    && ("wapRegist".equals(sendWay) || "phoneRegist".equals(sendWay))) { // 手机注册
                // 避免重复发送
                if ("wapRegist".equals(sendWay) && avoidSendAgain()) {
                    return null;
                }
                // 发送
                sendMobileVerifyCode();
                removeAttribute("wapRegistCopy");
            }

            Long uid = getAttribute(Constants.SESSION_USER_ID);
            if (!StringUtil.isEmpty(sendSource) && null != uid && !"wapRegist".equals(sendSource)) {
                User tempUser = resetPwdService.getUserByLoginId(uid);
                if (null != tempUser && !StringUtil.isEmpty(tempUser.getMobile())
                        && StringUtil.isEmpty(mobile)) {
                    mobile = tempUser.getMobile();
                }

                // 避免重复发送
                if (avoidSendAgain()) {
                    return null;
                }

                // 发送
                sendMobileVerifyCode();
            }
        } catch (Exception e) {
            logger.error("忘记密码发送短信存在异常：" + e.getMessage(), e);
        }
        mobileValidDateKey = null;
        mobile = null;

        return null;
    }

    private void sendMobileVerifyCode() throws IOException {
        // 发送
        boolean result =
                messageRemoteService.sendMobileVerifyCode(mobile, getSession(), sendSource);
        if (result) {
            setAttribute(mobileKey, getAttribute("OrderTrailMobileVerifyCode"));// 验证码
            setAttribute(mobileValidDateKey, new Date());
        }
        printJson("1");
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getSendSource() {
        return sendSource;
    }

    public void setSendSource(String sendSource) {
        this.sendSource = sendSource;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileKey() {
        return mobileKey;
    }

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public String getMobileValidDateKey() {
        return mobileValidDateKey;
    }

    public void setMobileValidDateKey(String mobileValidDateKey) {
        this.mobileValidDateKey = mobileValidDateKey;
    }

    public String getSendWay() {
        return sendWay;
    }

    public void setSendWay(String sendWay) {
        this.sendWay = sendWay;
    }
}
