package com.kmzyc.b2b.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.action.common.AbstractCommonAction;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.util.MailUtils;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.util.StringUtil;
import com.pltfm.app.vobject.EmailInfo;
import com.whalin.MemCached.MemCachedClient;

@Controller("resetpwdAction")
@Scope("prototype")
public class ResetPwdAction extends AbstractCommonAction {
    private static final long serialVersionUID = 1L;

    /**
     * session 前缀
     */
    private static final String RESET_PWD_USER = "RESET-PWD-USER";

    // 短信校验有效时间
    @Value("${b2b_common_msg_valid_time}")
    private int messageValidTime;

    // 短信校验前台有效时间
    @Value("${b2b_common_page_msg_valid_time}")
    private int messagePageValidTime;

    @Resource(name = "resetPwdServiceImpl")
    private ResetPwdService resetPwdService;
    @Resource(name = "registServiceImp")
    private RegistService registservice;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource
    private CustomerRemoteService customerRemoteService;

    private String loginAccount;// 用户名
    private String veCode;// 验证码
    private String msgCode;// 邮箱、手机验证码
    private String sendMethod;// 发送类型
    /**
     * 发送校验信息的来源，1 重置密码，2免注册转会员
     */
    private String sendSource;
    private String mobile;// 手机号码

    private String info;
    private UserBaseInfo ckUser;
    private User user;

    private ReturnResult<HashMap<String, Object>> returnResult;
    private int reFalg = 0;

    /**
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String validNEMIsexists() {
        try {
            if (loginAccount != null && StringUtil.isMobile(loginAccount)) {

                User user = loginService.queryUserInfoByNEM(loginAccount);
                if (null != user) {
                    if (user.getCustomerTypeId().intValue() == Constants.CUSTOMER_TYPE_SD_MEMBER
                            .intValue()) {
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                                "时代会员找回密码请拨打客服热线4006-111-412", null);
                    } else {
                        returnResult =
                                new ReturnResult(InterfaceResultCode.SUCCESS, "手机号未注册", null);
                    }
                } else {
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, "手机号未注册", null);
                }

            } else {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "手机号码格式错误", null);
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "查询异常", null);
            logger.error("查询用户信息异常", e);
        }
        return SUCCESS;
    }

    /**
     * 检查用户
     */
    public String queryUser() {
        removeAttribute(RESET_PWD_USER);

        // 对验证码进行校验
        String veCode = getParameter("veCode");
        if (null != veCode && null != loginAccount) {
            String code = getAttribute("regImg");
            if (StringUtil.isEmpty(code)) {
                code = getAttribute("veCode");
            }
            if (!code.equals(veCode)) {// 验证码校验
                return ERROR;
            }

            removeAttribute("regImg");
            removeAttribute("veCode");
            try {
                User user = loginService.queryUserInfoByNEM(loginAccount);
                if (null != user) {

                    removeAttribute("RESET1_" + user.getLoginId());

                    ckUser = new UserBaseInfo();
                    ckUser.setLoginId(user.getLoginId());
                    ckUser.setLoginAccount(user.getLoginAccount());
                    // 隐藏邮箱
                    ckUser.setEmail(MailUtils.genSecretEmail(user.getEmail()));
                    // 隐藏电话号码
                    ckUser.setMobile(MailUtils.genSecretMobile(user.getMobile()));

                    setAttribute(RESET_PWD_USER, user);
                    setAttribute("RESET1_" + user.getLoginId(), SUCCESS);
                }
            } catch (Exception e) {
                logger.error("验证用户名是否存在异常：" + e.getMessage(), e);
                return ERROR;
            }
        }

        return "SENDINFO";
    }

    /**
     * 找回密码发送验证信息
     */
    public String sendValidInfo() {
        info = "";
        boolean isTourist = true;

        User user = getAttribute(RESET_PWD_USER);
        if (null != user) {
            if ("2".equals(sendSource)) {// 免注册转会员
                // 校验是否是游客
                try {
                    isTourist = isTourist(user.getEmail(), user.getMobile());
                } catch (Exception e) {
                    logger.error("查询客户类型异常：" + e.getMessage(), e);
                    return ERROR;
                }
            }

            if (isTourist && user.getStatus() == 1 && !StringUtil.isEmpty(loginAccount)
                    && loginAccount.equalsIgnoreCase(user.getLoginAccount())) {

                // TODO 邮件相关
                /*
                 * if ("email".equals(sendMethod)) {// 发送密码到邮箱 try {
                 * handleEmailByResetPwdOrNoneRegister(sendSource, user); info =
                 * "{\"msg\":\"1\",\"mailLink\":\"" + MailUtils.getMailLink(user.getEmail()) +
                 * "\"}"; } catch (Exception e) { logger.error("忘记密码发送邮件存在异常：" + e.getMessage(), e);
                 * } } else
                 */

                if ("mobile".equals(sendMethod)) {
                    // 发送密码到手机
                    try {
                        if (illegalReferUrl(getReferUrl(), user.getMobile()) // 非法来源的请求
                                || illegalSendMessageTimeOnMobile(user.getMobile()) // 非正常时间短信发现
                                || abnormalSendMessageByMobile(user.getMobile()) // 号码短信发送异常
                                || illegalGraphValidCode(veCode)) { // 非法图形验证码
                            return null;
                        }

                        boolean result = false;
                        // 获取短信内容
                        MessageTypeEnum messageType = getMessageType(sendSource);
                        if (messageType != null) {
                            result = messageRemoteService.sendMobileVerifyCode(user.getMobile(),
                                    getSession(), messageType.getStatus());
                        }
                        if (result) {
                            setAttribute("RESET-PWD-VALID-CODE",
                                    getAttribute("OrderTrailMobileVerifyCode"));
                            setAttribute("RESET-PWD-VALID-DATE", new Date());
                        }

                        info = "{\"msg\":\"1\"}";
                    } catch (Exception e) {
                        logger.error("忘记密码发送短信存在异常：" + e.getMessage(), e);
                    }
                }
            }
        }

        try {
            printJson(info);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return ERROR;
        }

        return null;
    }

    /**
     * 判断当前用户是否为游客
     */
    private boolean isTourist(String email, String mobile) throws Exception {
        User temp = new User();
        temp.setEmail(StringUtil.isEmpty(email) ? mobile : email);

        if (6 != registservice.queryCustomerType(temp)) {
            info = "{\"msg\":\"0\"}";
            return false;
        }

        return true;
    }

    /**
     * 检查信息
     */
    public String validInfo() {
        if (StringUtil.isEmpty(loginAccount)) {
            return ERROR;
        }

        User user = getAttribute(RESET_PWD_USER);
        if (user == null) {
            return ERROR;
        }

        removeAttribute("code-valided");
        removeAttribute("RESET2_" + user.getLoginId());

        String success = getAttribute("RESET1_" + user.getLoginId());
        if (success == null || !success.equals(SUCCESS)) {
            logger.info("第1步没通过------禁止继续");
            return ERROR;
        }

        removeAttribute("RESET1_" + user.getLoginId());

        if (!StringUtil.isEmpty(msgCode) && loginAccount.equalsIgnoreCase(user.getLoginAccount())) {
            String code = getAttribute("RESET-PWD-VALID-CODE");
            Date date = getAttribute("RESET-PWD-VALID-DATE");
            if (!msgCode.equals(code)) {
                reFalg = 1;
                return ERROR;
            } else if (date != null
                    && (System.currentTimeMillis() - date.getTime()) <= messageValidTime * 60
                            * 1000l) {
                setAttribute("code-valided", SUCCESS);
                setAttribute("RESET2_" + user.getLoginId(), SUCCESS);
                return SUCCESS;
            }
        }

        return ERROR;
    }

    /**
     * 跳转到重置密码页面
     */
    public String goReset() {
        // 第三步跳转weijunlong
        User sessionUser = getAttribute(RESET_PWD_USER);
        if (sessionUser == null) {// app邮件验证邮件
            sessionUser = (User) memCachedClient.get(RESET_PWD_USER + user.getLoginId());
        }

        String code = getAttribute("RESET-PWD-VALID-CODE");
        // String type = getAttribute("VALID-TYPE-RESET");
        String targetAccount = getAttribute("TARGET_ACCOUNT");
        if (sessionUser != null && !StringUtil.isEmpty(code)/* || "Y".equals(type) */) {
            String success = getAttribute("RESET2_" + sessionUser.getLoginId());
            if (success == null || !success.equals(SUCCESS)) {
                logger.info("第2步没通过------禁止继续");
                return FAIL;
            }

            String loginStr = sessionUser.getLoginAccount().toLowerCase();
            if (!StringUtil.withinRangeByte(loginStr, 6, 25)
                    || null != targetAccount && !loginStr.equals(targetAccount.toLowerCase())) {
                return FAIL;
            }

            if (null != user) {
                try {
                    sessionUser = resetPwdService.getUserByLoginId(user.getLoginId());
                } catch (Exception e) {
                    logger.error("查询登录用户异常" + e.getMessage(), e);
                    return ERROR;
                }
                if (null == sessionUser) {
                    return FAIL;
                }

                setAttribute(RESET_PWD_USER, sessionUser);
                loginStr = sessionUser.getLoginAccount().toLowerCase();
            }
            setAttribute("code-valided", SUCCESS);

            ckUser = new UserBaseInfo();
            int len = loginStr.length();
            loginStr = loginStr.substring(0, 2) + "****" + loginStr.substring(len - 3, len);
            loginAccount = loginStr;

            return "RESETPWD";
        }

        return FAIL;
    }

    /**
     * 判断新修改的登录密码是否和支付密码相同
     */
    public String checkSamePwd() {
        User user = getAttribute(RESET_PWD_USER);
        AccountInfo accountInfo = securityCentreService.getAccountInfo(user);

        String password = MD5.getMD5Str(getParameter("password"));
        try {

            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(user.getLoginId());
            userBaseInfo.setPassword(password);
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "pay");
            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
            if (null == userBaseInfo) {
                printJson("0");
            } else {

                if (StringUtil.equals(userBaseInfo.getPassword(), accountInfo.getPaymentpwd())) {
                    printJson("0");
                } else {
                    printJson("1");
                }
            }
        } catch (Exception e) {
            logger.error("出现异常：" + e.getMessage(), e);
            return ERROR;
        }

        return null;
    }

    /**
     * 重置密码
     */
    public String resetPwd() throws Exception {
        info = "fail";
        String valided = getAttribute("code-valided");
        if (SUCCESS.equals(valided) && null != ckUser
                && !StringUtil.isEmpty(ckUser.getLoginAccount())
                && !StringUtil.isEmpty(ckUser.getLoginPassword())) {
            User sessionUser = getAttribute(RESET_PWD_USER);
            String success = getAttribute("RESET2_" + sessionUser.getLoginId());
            if (success == null || !success.equals("success")) {
                logger.info("第2步没通过------禁止继续");
                return ERROR;
            }

            removeAttribute("RESET_2" + sessionUser.getLoginId());
            removeAttribute("RESET-PWD-VALID-CODE");
            removeAttribute(RESET_PWD_USER);
            removeAttribute("code-valided");

            if (1 == sessionUser.getStatus()) {
                UserBaseInfo userInfo = new UserBaseInfo();
                userInfo.setLoginId(sessionUser.getLoginId());
                userInfo.setLoginPassword(MD5.getMD5Str(ckUser.getLoginPassword()));
                // 修改密码
                boolean isSuccess = resetPwdService.resetPassword(userInfo);
                if (isSuccess) {
                    info = SUCCESS;
                } else {
                    logger.info("修改密码失败！");
                    return ERROR;
                }
            }
        }

        return "RESULT";
    }

    /**
     * 进入 免注册购物转为会员
     */
    public String goUnRegMember() throws Exception {
        removeAttribute("RESET-PWD-USER");
        if (null != ckUser) {
            if (!StringUtil.isEmpty(ckUser.getEmail())
                    && !StringUtil.validEmail(ckUser.getEmail())) {
                ckUser.setEmail("");
            }
            if (!StringUtil.isEmpty(ckUser.getMobile())
                    && !StringUtil.equalLen(ckUser.getMobile(), 11)) {
                ckUser.setMobile("");
            }
        }

        return "REG";
    }

    /**
     * 根据输入查询临时用户
     */
    public String validExistsUser() throws Exception {
        removeAttribute(RESET_PWD_USER);

        if (null != ckUser && !StringUtil.isEmpty(ckUser.getEmail())
                && !StringUtil.isEmpty(ckUser.getMobile())) {
            ckUser = new UserBaseInfo();
            User params = new User();
            params.setMobile(ckUser.getMobile());
            params.setEmail(ckUser.getEmail());

            User userInfo = registservice.queryTempUser(params);
            if (null != userInfo && 1 == userInfo.getStatus()) {// 该用户未禁用
                userInfo.setLoginAccount("temp");// 标识临时用户
                setAttribute(RESET_PWD_USER, userInfo);

                // 隐藏邮箱
                ckUser.setEmail(MailUtils.genSecretEmail(userInfo.getEmail()));
                // 隐藏电话号码
                ckUser.setMobile(MailUtils.genSecretMobile(userInfo.getMobile()));
                ckUser.setLoginAccount(userInfo.getLoginAccount());
                ckUser.setLoginId(userInfo.getLoginId());
                return "VALIDINFO";
            }
        } else {
            ckUser = new UserBaseInfo();
        }

        return FAIL;
    }

    /**
     * 邮箱校验
     */
    /*
     * public String validMail() { // 邮箱验证链接操作weijunlong info = "0";// 重置info if (null == user ||
     * null == user.getLoginId()) { return ERROR; }
     * 
     * EmailInfo mailInfo = null; try { mailInfo =
     * registservice.queryValidMailByLoginId(user.getLoginId()); } catch (Exception e) {
     * logger.error("查询邮箱异常：" + e.getMessage(), e); return ERROR; }
     * 
     * String result = SUCCESS; if (null != mailInfo) { info = "2"; String reg =
     * mailInfo.getTattedCode() + "&validType=reg"; String reset = mailInfo.getTattedCode() +
     * "&validType=reset"; String setreg = mailInfo.getTattedCode() + "&validType=setreg"; if
     * (MD5.getMD5Str(reg).equals(veCode) && !StringUtil.isEmpty(user.getLoginAccount())) { try {
     * validRegistMail(user, mailInfo); } catch (Exception e) { logger.error(e.getMessage(), e);
     * return ERROR; } info = "1"; result = SUCCESS; } else if (MD5.getMD5Str(reset).equals(veCode))
     * { validResetMail(user); info = "1"; result = "resetPwd"; } else if
     * (MD5.getMD5Str(setreg).equals(veCode)) { setAttribute("VALID-TYPE-SETREG", "Y"); info = "1";
     * result = "setunRegPage"; }
     * 
     * // TODO 邮件相关
     *//*
       * if (updateMail) { try { mailService.updateByPrimaryKeySelective(mailInfo); } catch
       * (Exception e) { logger.error(e.getMessage(), e); return ERROR; } }
       *//*
         * 
         * try { printJson(info); } catch (IOException e) { logger.error(e.getMessage(), e); return
         * ERROR; } }
         * 
         * return result; }
         */
    //
    // private void validResetMail(User user) {
    // User sessionUser = getAttribute(RESET_PWD_USER, User.class);
    // if (sessionUser == null) {// 邮件验证邮件
    // sessionUser = (User) memCachedClient.get(RESET_PWD_USER + user.getLoginId());
    // }
    //
    // // 设置 跳转到重置密码页面 需要的用户信息
    // memCachedClient.set(RESET_PWD_USER + sessionUser.getLoginId(), sessionUser, new Date(
    // messageValidTime * 60 * 1000l));
    //
    // setAttribute("VALID-TYPE-RESET", "Y");
    // setAttribute("TARGET_ACCOUNT", sessionUser.getLoginAccount());
    // setAttribute("RESET2_" + sessionUser.getLoginId(), "success");
    // }
    private void validRegistMail(User user, EmailInfo mailInfo)
            throws MalformedURLException, ClassNotFoundException, Exception {
        User params = new User();
        params.setLoginId(user.getLoginId());
        params.setEmail(mailInfo.getUrlAddress());

        // userGrowingService.updateUserScoreInfo("RU0002", 1,
        // user.getLoginAccount().toLowerCase());

        registservice.updateTempUserInfo(params);

        setAttribute("VALID-TYPE-REG", "Y");
    }

    /**
     * 跳转到信息设置页面
     */
    public String setunRegPage() throws Exception {
        User userInfo = getAttribute(RESET_PWD_USER);
        if (userInfo == null) {
            removeAttribute("RESET-PWD-VALID-CODE");
            removeAttribute("code-valided");
            removeAttribute(RESET_PWD_USER);
            setAttribute("UN-REGMEMBER-METHOD", "mail");

            User params = new User();
            params.setLoginId(user.getLoginId());
            userInfo = registservice.queryTempUser(params);

            if (userInfo == null) {
                return FAIL;
            }
        }

        String code = getAttribute("RESET-PWD-VALID-CODE");
        String type = getAttribute("VALID-TYPE-SETREG");
        if (!StringUtil.isEmpty(code) || "Y".equals(type)) {
            setAttribute("RESET-PWD-VALID-CODE", SUCCESS);
            setAttribute("code-valided", SUCCESS);
            setAttribute(RESET_PWD_USER, userInfo);

            ckUser = new UserBaseInfo();
            // 隐藏邮箱
            ckUser.setEmail(MailUtils.genSecretEmail(userInfo.getEmail()));
            // 隐藏电话号码
            ckUser.setMobile(MailUtils.genSecretMobile(userInfo.getMobile()));
            ckUser.setLoginId(userInfo.getLoginId());
            return SUCCESS;
        }

        return FAIL;
    }

    /**
     * 免注册购物转为会员
     */
    public String unRegMember() throws Exception {
        User userInfo = getAttribute(RESET_PWD_USER);
        String code = getAttribute("RESET-PWD-VALID-CODE");
        String valided = getAttribute("code-valided");

        removeAttribute(RESET_PWD_USER);
        removeAttribute("RESET-PWD-VALID-CODE");
        removeAttribute("code-valided");

        info = "fail";
        if (SUCCESS.equals(valided) && null != ckUser && null != userInfo
                && !StringUtil.isEmpty(code) && !StringUtil.isEmpty(userInfo.getLoginAccount())) {
            try {
                String method = getAttribute("UN-REGMEMBER-METHOD");
                String ruleId = null;

                String uname = StringUtil.decode(ckUser.getLoginAccount());
                if (!StringUtil.withinRangeByte(uname, 6, 25)) {
                    return SUCCESS;
                }
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                userBaseInfo.setLoginId(userInfo.getLoginId());
                userBaseInfo.setLoginAccount(userInfo.getLoginAccount());
                userBaseInfo.setMobile(userInfo.getMobile());

                userBaseInfo.setPassword(ckUser.getLoginPassword());
                userBaseInfo =
                        this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");
                // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
                if (null == userBaseInfo) {
                    return SUCCESS;
                }

                User updateUser = new User();
                updateUser.setLoginAccount(uname);
                updateUser.setLoginPassword(userBaseInfo.getPassword());
                updateUser.setCustomerTypeId(Constants.CUSTOMER_TYPE_COMMON_MEMBER);
                updateUser.setLoginId(userInfo.getLoginId());

                if ("mail".equals(method)) {
                    updateUser.setEmail(userInfo.getEmail());
                    ruleId = "RU0002";
                } else {
                    updateUser.setMobile(userInfo.getMobile());
                    ruleId = "RU0003";
                }

                User params = new User();
                params.setEmail(userInfo.getEmail());
                params.setMobile(userInfo.getMobile());
                long CustomerType = registservice.queryCustomerType(params);

                if (Constants.CUSTOMER_TYPE_TOURIST.intValue() == CustomerType) {
                    registservice.updateTempUserInfo(updateUser);

                    setAttribute(Constants.SESSION_USER_ID, userInfo.getLoginId());
                    setAttribute(Constants.SESSION_USER_NAME, uname);
                    // 设置 免注册购物转为会员 cookie
                    setCookies(ckUser.getLoginAccount(), getSession().getId());

                    /*
                     * try { // 验证积分 userGrowingService.updateUserScoreInfo(ruleId, 1,
                     * userInfo.getLoginAccount()); } catch (Exception ex) {
                     * logger.error("免注册购物转为会员异常：" + ex.getMessage(), ex); }
                     */
                    info = SUCCESS;
                }
            } catch (Exception e) {
                logger.error("免注册购物转为会员异常：" + e.getMessage(), e);
            }
        }
        return SUCCESS;
    }

    /**
     * 修改密码，开放给第三方
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String resetPass() {
        String uName = getParameter("uName");
        String oldPass = getParameter("oldPass");
        String newPass = getParameter("newPass");
        String resultCode = null;
        String content = null;
        try {
            if (StringUtil.isEmpty(uName) || StringUtil.isEmpty(oldPass)
                    || StringUtil.isEmpty(newPass)) {
                resultCode = InterfaceResultCode.FAILED;
                content = "传入参数不能为空";
            } else {
                User user = new User();
                user.setLoginAccount(uName);
                user.setLoginPassword(oldPass);
                user = loginService.login(user);
                if (null == user) {
                    resultCode = InterfaceResultCode.FAILED;
                    content = "原始密码错误！";
                } else {
                    UserBaseInfo userInfo = new UserBaseInfo();
                    userInfo.setLoginId(user.getLoginId());
                    userInfo.setLoginPassword(newPass);
                    boolean isSuccess = resetPwdService.resetPassword(userInfo);
                    if (isSuccess) {
                        resultCode = InterfaceResultCode.SUCCESS;
                        content = "密码修改成功！";
                    } else {
                        resultCode = InterfaceResultCode.FAILED;
                        content = "修改密码失败！";
                    }
                }
            }
        } catch (Exception e) {
            logger.error("修改密码存在异常：" + e.getMessage(), e);
            resultCode = InterfaceResultCode.FAILED;
            content = "修改密码存在异常";
            return ERROR;
        }

        returnResult = new ReturnResult(resultCode, content, null);
        return SUCCESS;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = StringUtil.decode(loginAccount);
    }

    public String getVeCode() {
        return veCode;
    }

    public void setVeCode(String veCode) {
        this.veCode = veCode;
    }

    public String getSendMethod() {
        return sendMethod;
    }

    public void setSendMethod(String sendMethod) {
        this.sendMethod = sendMethod;
    }

    public UserBaseInfo getCkUser() {
        return ckUser;
    }

    public void setCkUser(UserBaseInfo ckUser) {
        this.ckUser = ckUser;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public int getReFalg() {
        return reFalg;
    }

    public void setReFalg(int reFalg) {
        this.reFalg = reFalg;
    }
}
