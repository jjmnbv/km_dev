package com.kmzyc.b2b.ajax;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.MobileCodeInfRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.vobject.MobileCodeInf;
import com.whalin.MemCached.MemCachedClient;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Controller("cmsGetLoginInfoAction")
public class CMSGetLoginInfAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CMSGetLoginInfAction.class);

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource(name = "registServiceImp")
    private RegistService registservice;

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAccountInfoService;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "messageRemoteService")
    private MessageRemoteService messageRemoteService;
    @Resource(name = "resetPwdServiceImpl")
    private ResetPwdService resetPwdService;

    @Resource
    private MobileCodeInfRemoteService mobileCodeInfRemoteService;

    /**
     * 单个session可请求手机是否接口次数前缀
     */
    private static final String VALID_MOBILE_TIME_KEY = "B2B_REDIS_VMTIME_";

    /* str是否包含searchChars */
    static boolean containsAny(String str, String searchChars) {
        return str.contains(searchChars);
    }

    private User user;
    private String veCode;
    private String registEmail;
    private String loginAccount;
    private String registPassword;
    private String mobile;
    private String mobileKey;// 手机验证码键
    private String mobileValidDateKey;// 手机验证有效时间键
    private String sendSource;// 发送校验信息的来源，1 重置密码，2免注册转会员


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSessionVarName() {
        return sessionVarName;
    }

    public void setSessionVarName(String sessionVarName) {
        this.sessionVarName = sessionVarName;
    }

    private String sendWay;// add by hl,注册的区别
    private String verificationCode;
    private String mobileNumber;
    private String sessionVarName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegistPassword() {
        return registPassword;
    }

    public void setRegistPassword(String registPassword) {
        this.registPassword = registPassword;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getRegistEmail() {
        return registEmail;
    }

    public void setRegistEmail(String registEmail) {
        this.registEmail = registEmail;
    }

    public String getVeCode() {
        return veCode;
    }

    public void setVeCode(String veCode) {
        this.veCode = veCode;
    }

    private ReturnResult<HashMap<String, Object>> returnResult;

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    private Map<String, Object> dataMap = new HashMap<String, Object>();
    private String SKUCode;
    private Long productSkuId;

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    private List SKUCodeList;

    public List getSKUCodeList() {
        return SKUCodeList;
    }

    public void setSKUCodeList(List sKUCodeList) {
        SKUCodeList = sKUCodeList;
    }

    public String getSKUCode() {
        return SKUCode;
    }

    public void setSKUCode(String sKUCode) {
        SKUCode = sKUCode;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 外接用户注册类型
     */
    private String getOutRegistType(String web) {
        String B2B_OUT_REGIST_TYPE = ConfigurationUtil.getString("b2b_out_regist_type");
        if (!StringUtil.isEmpty(B2B_OUT_REGIST_TYPE) && !StringUtil.isEmpty(web)) {
            String[] types = B2B_OUT_REGIST_TYPE.split(";");
            if (null != types && types.length > 0) {
                for (String type : types) {
                    if (type.indexOf(web) > 0) {
                        return type.split("@")[1];
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取客户信息 mark：1，表示客户已经登录，session有效期内，直接从session中获取user对象；2，表示在cookie中有信息保存， 在系统中午对应信息记录，
     * 返回的是只有用户名的user对象；0，表示没有获得user对象
     */
    public String getLoginInfo() throws Exception {
        String mark = "0";
        user = new User();
        user.setLoginAccount("");
        dataMap.clear();
        HttpSession session = ServletActionContext.getRequest().getSession();
        Long uid = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        String uname = (String) session.getAttribute(Constants.SESSION_USER_NAME);
        String nickName = (String) session.getAttribute(Constants.SESSION_NICK_NAME);

        String userHeadPic = (String) session.getAttribute(Constants.SESSION_USER_HEAD_PIC);
        user.setHeadSculpture(userHeadPic);
        if (!StringUtil.isEmpty(uname) && uid > 0) {
            uname = uname.toLowerCase();
            user.setLoginAccount(uname);
            mark = "1";
        } else {
            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            Cookie[] cs = this.getRequest().getCookies();
            String sessionId = null;
            uname = null;
            if (cs != null) {
                for (Cookie c : cs) {
                    if (c.getName().equals(Constants.COOKIE_SESSION_ID)) {
                        sessionId = c.getValue();
                    } else if (c.getName().equals(Constants.COOKIE_USER_NAME)) {
                        uname = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                    } else if (c.getName().equals(Constants.COOKIE_NICK_NAME)) {
                        nickName = java.net.URLDecoder.decode(c.getValue(), "UTF-8").toLowerCase();
                    }
                }
            }
            if (!StringUtil.isEmpty(sessionId)) {
                String UserName = (String) memCachedClient
                        .get(ConfigurationUtil.getString("b2b_login_name_memcached_key_prex")
                                + sessionId);
                String password = (String) memCachedClient
                        .get(ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex")
                                + sessionId);
                String clientIp = (String) memCachedClient
                        .get(ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex")
                                + sessionId);
                if (!StringUtil.isEmpty(UserName) && UserName.equalsIgnoreCase(uname)
                        && !StringUtil.isEmpty(password)) {
                    UserName = UserName.toLowerCase();
                    User params = new User();
                    params.setLoginAccount(UserName);
                    params.setLoginPassword(password);
                    User dataUser = loginService.login(params);
                    if (null != dataUser && 1 == dataUser.getStatus()) {
                        logger.info("登录名：" + UserName + ";客户端ip为：" + clientIp);
                        nickName = null == dataUser.getNickName() ? "" : dataUser.getNickName();
                        session.setAttribute(Constants.SESSION_USER_ID, dataUser.getLoginId());
                        session.setAttribute(Constants.SESSION_USER_NAME,
                                dataUser.getLoginAccount());
                        session.setAttribute(Constants.SESSION_NICK_NAME, nickName);
                        user.setLoginAccount(dataUser.getLoginAccount());
                        try {
                            if (null == uid
                                    || uid.longValue() != dataUser.getLoginId().longValue()) {
                                loginService.loginRecord(dataUser, clientIp);
                            }
                            memCachedClient.set(
                                    ConfigurationUtil.getString("b2b_login_name_memcached_key_prex")
                                            + sessionId,
                                    UserName);
                            memCachedClient.set(
                                    ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex")
                                            + sessionId,
                                    password);
                            memCachedClient.set(
                                    ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex")
                                            + sessionId,
                                    clientIp);
                        } catch (Exception e) {
                            logger.error("获取客户信息异常：" + e.getMessage(), e);
                        }
                        mark = "1";
                    } else {
                        Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, "");
                        Cookie d = new Cookie(Constants.COOKIE_USER_NAME, "");
                        Cookie e = new Cookie(Constants.COOKIE_NICK_NAME, "");
                        c.setPath("/");
                        d.setPath("/");
                        e.setPath("/");
                        String cookieDomain = ConfigurationUtil.getString("cookie_domain");
                        c.setDomain(cookieDomain);
                        d.setDomain(cookieDomain);
                        e.setDomain(cookieDomain);
                        int B2B_LOGIN_MEMCACHED_TIME = Integer
                                .parseInt(ConfigurationUtil.getString("b2b_login_memCached_time"));
                        c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
                        d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
                        e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
                        response.addCookie(c);
                        response.addCookie(d);
                        response.addCookie(e);
                        mark = "2";
                    }
                } else {
                    mark = "2";
                    user.setLoginAccount(uname);
                }
            } else {
                mark = "0";
                user.setLoginAccount("");
            }
        }
        user.setNickName(nickName);
        dataMap.put("mark", mark);
        dataMap.put("user", user);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", dataMap);
        return SUCCESS;
    }

    /**
     * 标识CMS端是否显示PV值
     */
    public String PVRrmarkToCMS() {
        String mark = "0";
        dataMap.clear();
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (Constants.LOGINTYPE.equals(session.getAttribute(Constants.SESSION_B2B_OR_ERA))) {
            mark = "1";
        }
        dataMap.put("mark", mark);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", dataMap);
        return SUCCESS;
    }

    public String veCode() throws Exception {
        Map session = ActionContext.getContext().getSession();
        String veCodes = (String) session.get("registVeCode");
        // 移除掉保存在session中的 验证码
        // session.remove("veCode");
        String info = "";
        if (null != veCodes && veCodes.equals(veCode)) {
            info = "1"; // 验证码验证成功
        } else {
            info = "0";
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
        return SUCCESS;
    }

    public String validemailRegist() throws Exception {
        Long result = 1L;
        if (null != user && !StringUtil.isEmpty(user.getEmail())) {
            User u = new User();
            u.setEmail(user.getEmail().toLowerCase());
            result = registservice.queryCustomerType(u);
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", result.toString());
        return SUCCESS;
    }

    /**
     * 验证用户名是否已注册
     */
    public String validvloginAccountRegist() throws Exception {
        int result = 1;
        if (!StringUtil.isEmpty(loginAccount)) {
            String uname = java.net.URLDecoder.decode(loginAccount, "UTF-8").toLowerCase();
            result = registservice.verilyName(uname);
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", result);
        return SUCCESS;
    }

    public String CMSregist() throws Exception {
        // 验证注册信息合法性
        if (StringUtil.isEmpty(registEmail) || StringUtil.isEmpty(loginAccount)
                || StringUtil.isEmpty(registPassword)) {
            logger.error("注册过程中出错：" + "注册信息非法");
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "注册信息错误！", "");
            return SUCCESS;
        }
        HttpServletRequest request = this.getRequest();

        logger.info("注册用户" + loginAccount + "IP是" + HttpUtils.getIP(request));
        HttpSession session = request.getSession();
        String veCode = request.getParameter("veCode");
        String sVeCode = (String) session.getAttribute("registVeCode");
        session.removeAttribute("registVeCode");
        String referer = request.getHeader("Referer");
        String host = "";
        if (null != referer && referer.indexOf("/") > 0) {
            String[] temp = referer.replaceFirst("//", "/").split("/");
            host = temp[1];
        }
        if (StringUtil.isEmpty(host)
                || ConfigurationUtil.getString("b2b_no_veCode_web").indexOf(host) < 0
                        && (null == veCode || !veCode.equals(sVeCode))) {
            logger.error("注册过程中出错：" + "注册信息非法");
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "验证码错误！", "");
            return SUCCESS;
        }
        User user = new User();
        user.setEmail(registEmail.toLowerCase());
        user.setLoginAccount(java.net.URLDecoder.decode(loginAccount, "UTF-8").toLowerCase());
        user.setLoginPassword(MD5.getMD5Str(registPassword));
        user.setCustomerTypeId(Constants.CUSTOMER_TYPE_COMMON_MEMBER);
        String info = "0";
        if (registservice.checkUserExists(user) > 0) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "注册失败！", info);
            return SUCCESS;
        }
        Map<String, String> o = registservice.regist(user);
        if (o.get("success") == null) {
            logger.error("注册过程中出错：" + o.get("Exception"));
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "注册失败！", "");
            return SUCCESS;
        } else {
            info = "1";
            user.setLoginId(Long.valueOf(o.get("loginId")));
            session.setAttribute(Constants.SESSION_USER_ID, user.getLoginId());
            session.setAttribute(Constants.SESSION_USER_NAME, user.getLoginAccount());
            session.setAttribute(Constants.SESSION_NICK_NAME, user.getLoginAccount());

            String sessionID = session.getId();
            // 写入cookie
            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionID);
            Cookie d = new Cookie(Constants.COOKIE_USER_NAME,
                    java.net.URLEncoder.encode(user.getLoginAccount(), "UTF-8"));
            Cookie e = new Cookie(Constants.COOKIE_NICK_NAME,
                    java.net.URLEncoder.encode(user.getLoginAccount().toLowerCase(), "UTF-8"));
            c.setPath("/");
            d.setPath("/");
            e.setPath("/");
            String cookieDomain = ConfigurationUtil.getString("cookie_domain");
            c.setDomain(cookieDomain);
            d.setDomain(cookieDomain);
            e.setDomain(cookieDomain);
            int B2B_LOGIN_MEMCACHED_TIME =
                    Integer.parseInt(ConfigurationUtil.getString("b2b_login_memCached_time"));
            c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            response.addCookie(c);
            response.addCookie(d);
            response.addCookie(e);

            String registType = getOutRegistType(host);
            if (null != registType && registType.length() > 0) {
                try {
                    ThirdAccountInfo thirdAccountInfo = new ThirdAccountInfo();
                    thirdAccountInfo.setOpenId(o.get("loginId"));
                    thirdAccountInfo.setThirdAccountType("1_" + registType);
                    thirdAccountInfo.setNickName(loginAccount);
                    thirdAccountInfo.setStatus("Y");
                    thirdAccountInfo.setCreateTime(
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    thirdAccountInfo.setRemark("来自" + host + "的用户");
                    thirdAccountInfoService.saveOutAccountInfo(thirdAccountInfo);
                } catch (Exception e2) {
                    logger.error("新增外接账户信息发生异常", e2);
                }
            }

        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
        return SUCCESS;
    }

    /**
     * 校验用户信息是否存在 update by houlin . 新增传入参数：loginId,校验时代会员用户名是否存在
     */
    public String checkUserExists() throws Exception {
        String result = "-1";
        try {
            String uname = this.getRequest().getParameter("uname");
            String email = this.getRequest().getParameter("email");
            String mobile = this.getRequest().getParameter("mobile");
            String loginId = this.getRequest().getParameter("loginId");
            if (!StringUtil.isEmpty(uname)
                    || (!StringUtil.isEmpty(email) && StringUtil.validEmail(email))
                    || StringUtil.equalLen(mobile, 11)) {
                User params = new User();
                if (!StringUtil.isEmpty(uname)) {
                    params.setLoginAccount(
                            java.net.URLDecoder.decode(uname, "UTF-8").toLowerCase());
                }
                params.setEmail(email);
                params.setMobile(mobile);
                if (!StringUtil.isEmpty(loginId)) { // 放入LoginId
                    params.setLoginId(Long.parseLong(loginId));
                }
                Long vsLoginId = (Long) getSession().getAttribute(Constants.VS_REG_SESSION_USER_ID);
                if (null != vsLoginId) { // 放入LoginId
                    params.setLoginId(vsLoginId);
                }
                result = "" + registservice.checkUserExists(params);
            }
        } catch (Exception e) {
            logger.error("校验用户信息是否存在发送异常:" + e.getMessage());
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", result);
        return SUCCESS;
    }

    /**
     * wap注册发送短信验证码
     */
    public String CMSsendMobile() throws Exception {
        try {
            HttpServletRequest request = this.getRequest();
            HttpSession session = this.getRequest().getSession();
            String info = "";
            String url = getRequest().getHeader("Referer");
            logger.info("来源打印：" + url);
            if (null != url) {
                if (!containsAny(url, ConfigurationUtil.getString("source_Url"))
                        && !containsAny(url, "kmb2b.com") && !containsAny(url, "v.kmb2b.com")
                        && !containsAny(url, "m.kmb2b.com")) {
                    logger.info("非正常来源  " + mobile + " 来源：" + url);
                    getResponse().getWriter().print("0");
                    return null;
                }
            } else {
                logger.info("非正常来源  " + mobile);
                getResponse().getWriter().print("0");
                return null;
            }
            Date d = new Date();
            int hours = d.getHours();// /wk 20150908 紧急处理
            if (hours >= 23 || hours < 8) {
                logger.info("非发送次短信时间  " + mobile + " 当前小时：" + hours);
                getResponse().getWriter().print("0");
                return null;
            }
            /* if ("wapRegist".equals(sendWay) || "phoneRegist".equals(sendWay)) { */
            String veCode = request.getParameter("veCode");
            Map sessionmap = ActionContext.getContext().getSession();
            String sVeCode = (String) sessionmap.get("registVeCode");


            if ((null == veCode || !veCode.equals(sVeCode))) { // 验证码不正确
                logger.info(
                        "非法发送短信   验证码不正确！request 获得验证码为：" + veCode + ", session获得验证码为:" + sVeCode);
                info = "3";
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
                return SUCCESS;
            }
            /* } */
            Long uid = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            int valid = messageRemoteService.validate(mobile, null);// 类型预留
            if (valid == 2) {
                logger.info("号码短信发送过频繁了" + mobile);
                info = "0";
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "发送过频繁", info);
                return SUCCESS;

            } else if (valid == 1) {
                logger.info("号码短信发送次数上限了" + mobile);
                info = "2";
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "已经到上限", info);
                return SUCCESS;

            }
            if (!StringUtil.isEmpty(sendSource) && "CMSphoneRegist".equals(sendWay)) { // 手机注册
                mobile = this.getMobile();
                if (StringUtil.isEmpty(mobileValidDateKey)) {
                    mobileValidDateKey = "MOBILE-VALID-DATE";
                }
                // 避免重复发送
                /*
                 * if (null != date && (new Date().getTime() - date.getTime()) <
                 * (Integer.parseInt(MESSAGE_PAGE_VALID_TIME) * 60 * 1000)) { info = "0";
                 * returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "发送过频繁", info);
                 * return SUCCESS;
                 * 
                 * }
                 */
                // 发送
                boolean result =
                        messageRemoteService.sendMobileVerifyCode(mobile, session, sendSource);
                if (result) {
                    String mCode = (String) session.getAttribute("OrderTrailMobileVerifyCode");// 验证码
                    session.setAttribute(mobileKey, mCode);
                    session.setAttribute(mobileValidDateKey, new Date());
                    // MobileCodeInfRemoteService mcservice =
                    // (MobileCodeInfRemoteService)
                    // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                    // "mobileCodeInfRemoteService");
                    MobileCodeInf mci = new MobileCodeInf();

                    mci.setN_FailureTimeValue(Integer
                            .parseInt(ConfigurationUtil.getString("b2b_common_msg_valid_time")));
                    mci.setIs_Status(0);
                    mci.setMobile(mobile);
                    mci.setTattedCode(mCode);
                    mobileCodeInfRemoteService.addMobileCodeInf(mci, 3);
                }
                session.removeAttribute("wapRegistCopy");
                info = "1";
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
                return SUCCESS;
            }
            if (!StringUtil.isEmpty(sendSource) && null != uid && !"wapRegist".equals(sendSource)) {
                User tempUser = resetPwdService.getUserByLoginId(uid);
                if (null != tempUser && !StringUtil.isEmpty(tempUser.getMobile())
                        && StringUtil.isEmpty(mobile)) {
                    mobile = tempUser.getMobile();
                }
                if (StringUtil.isEmpty(mobileValidDateKey)) {
                    mobileValidDateKey = "MOBILE-VALID-DATE";
                }
                Date date = (Date) session.getAttribute(mobileValidDateKey);
                // 避免重复发送
                if (null != date
                        && (new Date().getTime() - date.getTime()) < (Integer
                                .parseInt(ConfigurationUtil
                                        .getString("b2b_common_page_msg_valid_time"))
                                * 60 * 1000)) {
                    info = "0";
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "发送过频繁", info);
                    return SUCCESS;
                }

                // 发送
                boolean result =
                        messageRemoteService.sendMobileVerifyCode(mobile, session, sendSource);

                if (result) {
                    String mCode = (String) session.getAttribute("OrderTrailMobileVerifyCode");// 验证码
                    session.setAttribute(mobileKey, mCode);
                    session.setAttribute(mobileValidDateKey, new Date());
                    // MobileCodeInfRemoteService mcservice =
                    // (MobileCodeInfRemoteService)
                    // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                    // "mobileCodeInfRemoteService");
                    MobileCodeInf mci = new MobileCodeInf();
                    if (null != tempUser) {
                        mci.setN_LoginId(tempUser.getLoginId().intValue());
                    }
                    mci.setN_FailureTimeValue(Integer
                            .parseInt(ConfigurationUtil.getString("b2b_common_msg_valid_time")));
                    mci.setIs_Status(0);
                    mci.setMobile(mobile);
                    mci.setTattedCode(mCode);
                    mobileCodeInfRemoteService.addMobileCodeInf(mci, 3);
                    info = "1";
                    getResponse().getWriter().print(info);
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
                    this.getReturnResult().setCode("200");
                    this.getReturnResult().setMessage("发送成功");
                    return SUCCESS;
                }
            }
        } catch (Exception e) {
            logger.error("忘记密码发送短信存在异常：" + e.getMessage(), e);
        }

        mobileValidDateKey = null;
        mobile = null;
        return null;

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

    public String getSendSource() {
        return sendSource;
    }

    public void setSendSource(String sendSource) {
        this.sendSource = sendSource;
    }

    public String getSendWay() {
        return sendWay;
    }

    public void setSendWay(String sendWay) {
        this.sendWay = sendWay;
    }

    /* 验证短信验证码 */
    public String getSessionVars() throws ServiceException {
        HttpSession session = this.getSession();
        /* Map<String, String> map = new HashMap<String, String>(); */
        String info = "";
        String sessionMobileVerifyCode =
                (String) session.getAttribute("OrderTrailMobileVerifyCode");
        // 获取Session 中的手机号码
        String sessinonMobilePhone = (String) session.getAttribute("mobilePhone");
        if (sessionMobileVerifyCode != null
                && verificationCode.trim().equals(sessionMobileVerifyCode.trim())
                && sessinonMobilePhone != null && sessinonMobilePhone.equals(mobileNumber.trim())) {
            Date date = (Date) session.getAttribute("mobileVerifyCodeTime");
            if (null != date && (new Date().getTime() - date.getTime()) < (Integer.parseInt(
                    ConfigurationUtil.getString("b2b_common_msg_valid_time")) * 60 * 1000)) {
                // 成功通过校验
                /* map.put(sessionVarName, "1"); */
                info = "1";
            } else {
                // 校验码正确，但超时
                /* map.put(sessionVarName, "2"); */
                info = "2";
            }
        } else {
            // 校验码错误
            /* map.put(sessionVarName, "0"); */
            info = "0";
        }

        /* AjaxUtil.writeJSONToResponse(map); */

        /* return NONE; */
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
        return SUCCESS;
    }

    public String mobileNumberIsTrues() throws Exception {

        try {
            Pattern pattern = Pattern.compile("^[1]([3,4,5,7,8][0-9]{9})$");
            Matcher matcher = pattern.matcher(mobile);
            if (!matcher.find()) {
                logger.info("号码格式不正确:" + mobile);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", 0);
            } else {
                int resultMessage = 1;
                String key = VALID_MOBILE_TIME_KEY.concat(getRequest().getSession().getId());

                String times = jedisCluster.get(key);
                int count = null == times ? 0 : Integer.parseInt(times);
                if (count < 10) {// 校验次数
                    count++;
                    jedisCluster.set(key, count + "");
                    jedisCluster.expire(key, 120);
                    if (securityCentreService.mobileIsUsed(mobile)) {
                        resultMessage = 0;// 手机号码已被绑定
                    }
                    returnResult =
                            new ReturnResult(InterfaceResultCode.SUCCESS, "成功", resultMessage);
                } else {
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", 0);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return SUCCESS;
        /*
         * String resultMessage = ""; boolean isUsed = securityCentreService.mobileIsUsed(mobile);
         * if (isUsed) { resultMessage = "0";// 手机号码已被绑定 } else { resultMessage = "1";// 没有注册 }
         * returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", resultMessage); return
         * SUCCESS;
         */
    }

    /*
     * 浮动框手机注册
     */
    public String CMSPhoneRegist() {
        // 校验手机
        /*
         * boolean flag = false;
         * 
         * try { Pattern p =
         * Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
         * Matcher m = p.matcher(mobile); flag = m.matches(); } catch (Exception e) { flag = false;
         * } if (!flag) { logger.error("注册过程中出错：" + "注册手机号非法"); returnResult = new
         * ReturnResult(InterfaceResultCode.FAILED, "注册信息错误！", ""); return SUCCESS; } if
         * (!StringUtil.withinRange(registPassword, 6, 20)) { logger.error("注册过程中出错：" + "注册密码非法");
         * returnResult = new ReturnResult(InterfaceResultCode.FAILED, "注册信息错误！", ""); return
         * SUCCESS; }
         */

        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();
        String info = "";
        User userRs = new User();
        userRs.setLoginAccount(StringUtil.getLoginAccount(Constants.CUSTOMER_TYPE_SD_MEMBER));
        userRs.setLoginPassword(MD5.getMD5Str(registPassword));
        userRs.setMobile(mobile);
        userRs.setCustomerTypeId(Constants.CUSTOMER_TYPE_COMMON_MEMBER);
        try {

            Map<String, String> o = registservice.registDivPhone(userRs);
            if (o.get("success") == null) {
                logger.error("注册过程中出错：" + o.get("Exception"));
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "注册失败！", "");
                return SUCCESS;
            }

            info = "1";
            userRs.setLoginId(Long.valueOf(o.get("loginId")));
            session.setAttribute(Constants.SESSION_USER_ID, userRs.getLoginId());
            session.setAttribute(Constants.SESSION_USER_NAME, userRs.getLoginAccount());
            session.setAttribute(Constants.SESSION_NICK_NAME, userRs.getLoginAccount());
            String sessionID = session.getId();
            // 写入cookie
            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionID);
            Cookie d = new Cookie(Constants.COOKIE_USER_NAME, java.net.URLEncoder
                    .encode(userRs.getLoginAccount(), "UTF-8")/* loginAccount */);
            Cookie e = new Cookie(Constants.COOKIE_NICK_NAME, java.net.URLEncoder
                    .encode(userRs.getLoginAccount().toLowerCase(), "UTF-8")/* loginAccount */);
            c.setPath("/");
            d.setPath("/");
            e.setPath("/");
            String cookieDomain = ConfigurationUtil.getString("cookie_domain");
            c.setDomain(cookieDomain);
            d.setDomain(cookieDomain);
            e.setDomain(cookieDomain);
            int B2B_LOGIN_MEMCACHED_TIME =
                    Integer.parseInt(ConfigurationUtil.getString("b2b_login_memCached_time"));
            c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            response.addCookie(c);
            response.addCookie(d);
            response.addCookie(e);

            logger.info("注册成功");

        } catch (Exception e) {
            logger.error("注册过程中出错：" + e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "注册失败！", "");
            return SUCCESS;
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", info);
        return SUCCESS;

    }


}
