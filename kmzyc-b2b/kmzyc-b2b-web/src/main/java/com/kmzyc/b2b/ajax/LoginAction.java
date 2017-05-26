package com.kmzyc.b2b.ajax;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.shopcart.util.ShopCartUtil;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings("unchecked")
@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends UserBaseAction {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

    private String loginName;// 帐号
    private String passwordcode;// 密码
    private String veCode;// 图形验证码
    private String smsCode;// 验证码
    private String info;
    private String loginPassword;
    private String confirmpasswords;
    private ReturnResult<HashMap<String, Object>> returnResult;
    private static final String VCODE_KEY = "loginImg";

    @Resource(name = "loginServiceImp")
    private LoginService loginService;


    /**
     * 登录
     */
    @SuppressWarnings("finally")
    public String unionLogin() throws Exception {
        info = "0";
        int message = 0;
        int registerDevice = Constants.REGISTER_DEVICE_PC;
        try {
            HttpServletResponse response = getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            HttpServletRequest request = getRequest();
            String url = getRequest().getHeader("Referer");
            if (null != url) {
                if (url.contains("wapLogin.jsp")) {
                    registerDevice = Constants.REGISTER_DEVICE_WAP;
                }
            }
            logger.info("({})会员登录开始",loginName);
            // 是否开启ssl   如果开户了SSL则请求域名必须是 www.kmb2b.com(原先是login.kmb2b.com) =====
            boolean ENABLE_SSL = "1".equals(ConfigurationUtil.getString("enable_ssl"));
            if (null != loginName && (!ENABLE_SSL || request.getRequestURL().indexOf("www.kmb2b.com") > 0)) {

                // 校验 s
                loginName = java.net.URLDecoder.decode(loginName, "UTF-8").toLowerCase();
                if (!StringUtil.withinRangeByte(loginName, 6, 50) ||
                        !StringUtil.equalLen(passwordcode, 32)) {
                    info = "0";
                    return SUCCESS;
                } else if (0 == (message = getErrorCount(loginName))) {
                    info = "-2";
                    return SUCCESS;
                }
                // 校验 e

                // 查询用户信息 s
                int userType = 1;
                User user;
                if (StringUtil.isTimesUName(loginName)) {
                    user = loginService
                            .timeLogin(loginName, passwordcode.toLowerCase(), registerDevice);
                    userType = 2;
                } else {
                    user = new User();
                    //if (StringUtil.isMobile(loginName)) {
                    user.setMobile(loginName);
                    //}
                    /* else if (loginName.indexOf("@") > 0) {
                        user.setEmail(loginName);
                    } else {
                        user.setLoginAccount(loginName);
                    }*/
                    user.setLoginPassword(passwordcode.toLowerCase());
                    user = loginService.userLogin(user);
                    // 判断是否是已经绑定康美账号的时代会员
                    if (user != null && user.getEarInfo() != null &&
                            user.getEarInfo().getEraNo() != null) {
                        userType = 2;
                    }
                }
                // 查询用户信息 e

                // 登录
                if (null != user && 1 == user.getStatus()) {
                    String ip = HttpUtils.getIP(request);
                    writeLoginCoodie(user, "1".equals(request.getParameter("isKeep")), ip);
                    info = "1." + userType;
                    loginService.doAfterLogin(user,
                            ShopCartUtil.getTempUserId(getRequest(), getResponse()), null, ip);
                } else if (null != user) {
                    info = "-1";
                } else {
                    message++;
                    if (0 == message) {
                        message = 1;
                    }
                    if (incrErrorCount(loginName)) {
                        info = "-2";
                    }
                }
                HttpSession session = request.getSession();
                session.removeAttribute(VCODE_KEY);
            }else{
                logger.error("{}登录失败,请检查是否启用SSL",loginName);
            }
        } catch (Exception e) {
            logger.error("登录发生异常" + e.getMessage(), e);
        } finally {
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, String.valueOf(message),info);
        }
        logger.info("({})会员登录结束,登录结果({})",loginName,info);
        return SUCCESS;
    }

    /**
     * 退出
     */
    public String loginout() throws Exception {
        try {
            removeLoginInfo();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
        return SUCCESS;
    }

    /*public String kmCardNoLogin() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        try {
            if (isInteger(loginName) && loginName.length() == 12) {
                int flag = kmLogin(loginName, passwordcode.toLowerCase());
                if (1 == flag || 2 == flag) {// B2B没有或密码为手机号 看总有没有
                    Map<String, String> map =
                            loginService.queryWebServiceCardNumAndMobile(loginName, passwordcode);
                    if (null != map) {
                        session.setAttribute(Constants.SESSION_KM_CARDNUM, loginName);
                        session.setAttribute(Constants.SESSION_KM_MOBILE, map.get("MESSAGECONTENT"));
                        // if (securityCentreService.mobileIsUsed(map.get("MESSAGECONTENT"))) {//
                        // 此号码是否已经被注册
                        // returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null,
                        // "-3");// 登陆页面
                        // return SUCCESS;
                        // }
                        session.removeAttribute(Constants.SESSION_KM_CARDNUM_02);
                        session.setAttribute(Constants.SESSION_KM_CARDNUM_01, true);

                        if (flag == 1) {
                            returnResult =
                                    new ReturnResult(
                                            InterfaceResultCode.SUCCESS,
                                            map.get("MESSAGECONTENT") + "," + loginName + "," + "0",
                                            "1.2");
                        } else {
                            returnResult =
                                    new ReturnResult(
                                            InterfaceResultCode.SUCCESS,
                                            map.get("MESSAGECONTENT") + "," + loginName + "," + "1",
                                            "1.2");
                        }

                        // 手机验证注册
                        return SUCCESS;
                    } else {
                        // 总部没有这个卡号的用户
                        returnResult =
                                new ReturnResult(InterfaceResultCode.SUCCESS, null, 1 == flag
                                        ? "-1"
                                        : "-2");
                        // 登陆页面
                        return SUCCESS;
                    }
                } else if (0 == flag) {
                    logger.info("登录成功loginName：" + loginName);
                    HttpServletRequest request = getRequest();
                    String ip = HttpUtils.getIP(request);
                    writeLoginCoodie(user, false, ip);
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, "1");
                    loginService.doAfterLogin(user, null, null, ip);
                    return SUCCESS;
                    // 直接登陆
                }
            } else {
                logger.info("帐号基本格式不对loginName：" + loginName);
                // 帐号基本格式不对
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, "-1");
                return SUCCESS;
            }
        } catch (Exception e) {
            logger.error("KM用户登陆异常：" + e.getMessage());
        }
        return SUCCESS;
    }*/

    /*public String mobileCodeSubmit() {

        *//*
        HttpSession session = ServletActionContext.getRequest().getSession();
        String sessionMobileVerifyCode =
                (String) session.getAttribute("OrderTrailMobileVerifyCode");
        String sCode = (String) session.getAttribute("regImg");
        try {
            Boolean flag = (Boolean) session.getAttribute(Constants.SESSION_KM_CARDNUM_01);
            if (flag) {
                if (sessionMobileVerifyCode == null || smsCode == null
                        || !smsCode.trim().equals(sessionMobileVerifyCode.trim())
                        || (null == veCode || !veCode.equals(sCode))) {
                    session.removeAttribute(Constants.SESSION_KM_CARDNUM_01);
                    session.removeAttribute(Constants.SESSION_KM_CARDNUM_02);
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, "1");// 失败
                } else {
                    session.setAttribute(Constants.SESSION_KM_CARDNUM_02, true);
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, "0");// 成功
                }
            } else {
                session.removeAttribute(Constants.SESSION_KM_CARDNUM_01);
                session.removeAttribute(Constants.SESSION_KM_CARDNUM_02);
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, "1");// 失败
            }
        } catch (Exception e) {
            logger.error("KM手机验证码验证" + e.getMessage());
            session.removeAttribute(Constants.SESSION_KM_CARDNUM_01);
            session.removeAttribute(Constants.SESSION_KM_CARDNUM_02);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, "1");// 失败
        }
        return SUCCESS;
    }*/

    /**
     * 获取错误登录次数
     */
    public String getLoginTime() {
        info = String.valueOf(Integer.MAX_VALUE);
        try {
            info = getErrorCount(loginName).toString();
            if (Objects.equals("-1", info)) {
                info = "0";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
        return SUCCESS;
    }

    /**
     * 康美通登录账号校验
     *
     * <pre>
     *  1：会员ID错误
     *  2：手机号错误
     *  3：会员ID和手机号至少一个不为空
     *  4：密码错误
     *  5：密码不能为空
     *  6：用户已被禁用
     *  7：登录频繁
     * </pre>
     */
    public String checkLoginInfoForKMT() {
        HttpServletRequest request = getRequest();
        try {
            String loginId = request.getParameter("loginId");
            String mobile = request.getParameter("mobile");
            String loginPassword = request.getParameter("loginPassword");

            if (StringUtil.isEmpty(loginId) && StringUtil.isEmpty(mobile)) {
                returnResult = new ReturnResult("0", "会员ID和手机号至少一个不为空", "3");
                return SUCCESS;
            }

            if (!StringUtil.isMobile(mobile)) {
                returnResult = new ReturnResult("0", "手机号错误", "2");
                return SUCCESS;
            }

            if (StringUtil.isEmpty(loginPassword)) {
                returnResult = new ReturnResult("0", "密码不能为空", "5");
                return SUCCESS;
            }

            if (!StringUtil.equalLen(loginPassword, 32)) {
                returnResult = new ReturnResult("0", "密码错误", "4");
                return SUCCESS;
            }

            User user = new User();

            String loginName;

            if (!StringUtil.isEmpty(loginId)) {
                user.setLoginId(Long.valueOf(loginId));
                loginName = loginId;
            } else {
                user.setMobile(mobile);
                loginName = mobile;
            }

            int errorCount = getErrorCount(loginName);
            if (errorCount == 0) {
                returnResult = new ReturnResult("0", "请在电商平台找回登入密码", "7");
                return SUCCESS;
            }


            user = this.loginService.queryUser(user);
            if (null == user) {
                if (!StringUtil.isEmpty(loginId)) {
                    returnResult = new ReturnResult("0", "会员ID错误", "1");
                    return SUCCESS;
                } else {
                    returnResult = new ReturnResult("0", "手机号错误", "2");
                    return SUCCESS;
                }
            } else {
                if (user.getStatus() != 1) {
                    returnResult = new ReturnResult("0", "用户已被禁用", "6");
                    return SUCCESS;
                }
                if (loginPassword.equalsIgnoreCase(user.getLoginPassword())) {

                    Map<String, String> map = new HashMap<>();
                    map.put("loginId", String.valueOf(user.getLoginId()));
                    map.put("mobile", user.getMobile());
                    map.put("email", user.getEmail());
                    map.put("partner", ConfigurationUtil.getString("kmt_pay_partner"));// 商户号
                    map.put("src", "KMDIRSIR");
                    map.put("srcAccount", user.getLoginAccount());
                    returnResult = new ReturnResult("1", "验证成功", map);

                } else {
                    if (incrErrorCount(loginName)) {
                        returnResult = new ReturnResult("0", "请在电商平台找回登入密码", "7");
                    } else {
                        errorCount = getErrorCount(loginName);
                        if (errorCount == -1) {
                            errorCount = 0;
                        }
                        returnResult = new ReturnResult("0",
                                "登录密码有误，连续再输错" + (MAX_NAME_ERROR_COUNT - errorCount) + "次，帐号将锁2小时",
                                "4");
                    }
                    return SUCCESS;
                }
            }


        } catch (Exception e) {
            logger.error("康美通登录账号校验异常", e);
        }

        return SUCCESS;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswordcode() {
        return passwordcode;
    }

    public void setPasswordcode(String passwordcode) {
        this.passwordcode = passwordcode;
    }

    public String getVeCode() {
        return veCode;
    }

    public void setVeCode(String veCode) {
        this.veCode = veCode;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getConfirmpasswords() {
        return confirmpasswords;
    }

    public void setConfirmpasswords(String confirmpasswords) {
        this.confirmpasswords = confirmpasswords;
    }

}
