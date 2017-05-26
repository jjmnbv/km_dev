package com.kmzyc.b2b.action;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.util.MailUtils;
import com.kmzyc.b2b.vo.RegistedInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.util.HttpRequestDeviceUtils;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

@Controller("registAction")
@Scope("prototype")
public class RegistAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(RegistAction.class);
    private ReturnResult<HashMap<String, Object>> returnResult;
    private String registEmail;
    private String loginAccount;
    private String registPassword;
    private String veCode;
    private User user;
    private User reqUser;
    private String info;
    private String validType;
    private RegistedInfo regInfo;
    private String mobile;
    private String headMobile;
    private String bottomMobile;
    // 手机验证码
    private String verificationCode;


    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    @Resource(name = "registServiceImp")
    private RegistService registservice;



    private int B2B_LOGIN_MEMCACHED_TIME =
            Integer.parseInt(ConfigurationUtil.getString("b2b_login_memCached_time"));

    private int REGISTE_IP_REDIS_TIME =
            Integer.parseInt(ConfigurationUtil.getString("REGISTE_IP_REDIS_TIME"));


    /**
     * 单个session可请求手机是否接口次数前缀
     */
    private static final String VALID_MOBILE_TIME_KEY = "B2B_REDIS_VMTIME_";

    /**
     * 验证邮箱是否已经注册
     * 
     * @return
     * @throws Exception
     */
    public String email() {
        Long result = 1L;
        if (null != user && !StringUtil.isEmpty(user.getEmail())) {
            User params = new User();
            params.setEmail(user.getEmail());
            try {
                result = registservice.queryCustomerType(params);
            } catch (SQLException e) {
                logger.error("查询邮箱异常：" + e.getMessage(), e);
                return ERROR;
            }
        }
        try {
            getResponse().getWriter().print(result);
        } catch (IOException e) {
            logger.error("登录出现IO异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 验证用户是否已注册
     * 
     * @return
     * @throws Exception
     */
    public String isRegistedUser() {
        Long result = 1L;
        try {
            User params = new User();
            if (null != user && !StringUtil.isEmpty(user.getEmail())
                    && StringUtil.validEmail(user.getEmail())) {
                params.setEmail(user.getEmail());
            }
            if (null != user && !StringUtil.isEmpty(user.getLoginAccount())) {
                params.setLoginAccount(
                        java.net.URLDecoder.decode(user.getLoginAccount(), "UTF-8").toLowerCase());
            }
            if (null != user && StringUtil.equalLen(user.getMobile(), 11)) {
                params.setMobile(user.getMobile());
            }
            try {
                result = registservice.queryCustomerType(params);
            } catch (SQLException e) {
                logger.error("查询邮箱异常：" + e.getMessage(), e);
                return ERROR;
            }
            getResponse().getWriter().print(result);
        } catch (IOException e) {
            logger.error("验证用户名是否已注册出现IO异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 验证用户名是否已注册
     * 
     * @return
     * @throws Exception
     */
    public String vloginAccount() {
        try {
            if (!StringUtil.isEmpty(loginAccount)) {
                String uname = java.net.URLDecoder.decode(loginAccount, "UTF-8").toLowerCase();
                try {
                    getResponse().getWriter().print(registservice.verilyName(uname));
                } catch (Exception e) {
                    logger.error("验证用户名异常:" + e.getMessage(), e);
                    return ERROR;
                }
            }
        } catch (IOException e) {
            logger.error("验证用户名是否已注册出现IO异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    // 注册
    public String regist() throws Exception {
        info = "";
        // 验证注册信息合法性
        if (StringUtil.isEmpty(registEmail) || StringUtil.isEmpty(loginAccount)
                || StringUtil.isEmpty(registPassword)
                || (!StringUtil.withinRangeByte(java.net.URLDecoder.decode(loginAccount, "UTF-8"),
                        6, 20))
                || (!StringUtil.withinRange(registEmail, 6, 50))
                || (!StringUtil.withinRange(registPassword, 6, 20))) {
            logger.error("注册过程中出错：" + "注册信息非法");
            return ERROR;
        }
        HttpServletRequest request = this.getRequest();
        boolean isMobile = HttpRequestDeviceUtils.isMobileDevice(request); // 检测是否手机端打开。
        logger.info("注册用户" + loginAccount + "IP是" + HttpUtils.getIP(request));
        HttpSession session = request.getSession();
        String veCode = request.getParameter("veCode");
        String sVeCode = (String) session.getAttribute("regImg");

        session.removeAttribute("regImg");
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
            return ERROR;
        }
        String clientIp = getRequest().getParameter("clientIp");
        try {
            // REGISTE_IP_REDIS_TIME 用来记录5分钟只能注册一次
            String object = jedisCluster.get(clientIp);
            if (object == null) { // 存入jedis中，5分钟失效
                jedisCluster.setex(clientIp, REGISTE_IP_REDIS_TIME, clientIp);
            } else {// 用户在5分钟之内不能在登陆
                info = "0";
                getResponse().getWriter().print(info);
                return null;
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        User params = new User();
        params.setEmail(registEmail.toLowerCase());
        params.setLoginAccount(java.net.URLDecoder.decode(loginAccount, "UTF-8").toLowerCase());
        params.setLoginPassword(MD5.getMD5Str(registPassword));
        params.setCustomerTypeId(Constants.CUSTOMER_TYPE_COMMON_MEMBER);
        if (registservice.checkUserExists(params) > 0) {
            getResponse().getWriter().print(info);
            return ERROR;
        }
        // 调用推广返利接口 update by hl 5-14 从cookie中取

        String invitationId = "";
        String organizations = "";
        Cookie[] cooks = request.getCookies();
        String source = "";
        String flSource = "";
        String yunSource = "";
        for (Cookie cook : cooks) {
            if ("invitationId".equals(cook.getName())) {
                invitationId = cook.getValue();
            } else if ("organizations".equals(cook.getName())) {
                organizations = cook.getValue();
            } else if (com.kmzyc.b2b.util.CPSUtils.B2B_CPS_YQF_COOKIE_KEYS[0]
                    .equals(cook.getName())) {
                source = cook.getValue();
            } /*
               * else if (Constants.COOKIE_FANLI_CHANNEL_ID.equals(cook.getName())) { flSource =
               * cook.getValue(); } else if ("sruleId".equals(cook.getName())) { // 是规则的标示 yunSource
               * = cook.getValue(); }
               */
        }
        int Platfrom = Constants.REGISTER_PLATFORM_DEFAULT;
        /*
         * if (null != yunSource && !"".equals(yunSource)) { Platfrom =
         * Constants.REGISTER_PLATFORM_YUNSHANG; } else if (null != flSource &&
         * !"".equals(flSource)) { Platfrom = Constants.REGISTER_PLATFORM_FL; } else
         */if (null != source && !"".equals(source)) {
            Platfrom = Constants.REGISTER_PLATFORM_CPS;
        }
        Map<String, String> o = registservice.registReward(isMobile, params, invitationId,
                organizations, Constants.REGISTER_DEVICE_PC, Platfrom);
        if (o.get("success") == null) {
            logger.error("注册过程中出错：" + o.get("Exception"));
            return ERROR;
        } else {
            try {
                // --start add by hl,敲金蛋的来源记录
                if ("true".equals(ConfigurationUtil.getString("regist_source"))
                        && isInTime(new Date())) {
                    jedisCluster.sadd(ConfigurationUtil.getString("b2b_xhw_resource_uid"),
                            o.get("loginId"));
                    String XHW_USER_SOURCE = ConfigurationUtil.getString("xhw_user_source");
                    if (XHW_USER_SOURCE.equals(source)) {// 表示是新华网来的注册客户
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("userId", o.get("loginId"));
                        map.put("resource", XHW_USER_SOURCE);
                        securityCentreService.updateSource(map);
                    }
                }
                // --end 敲金蛋来源记录
            } catch (Exception e) {
                logger.error("", e);
            }
            info = "1";
            params.setLoginId(Long.valueOf(o.get("loginId")));
            reqUser = params;

            String userName = reqUser.getLoginAccount();

            if (!StringUtil.isEmpty(reqUser.getMobile())) {
                userName = reqUser.getMobile();
            }

            session.setAttribute(Constants.SESSION_USER_ID, params.getLoginId());
            session.setAttribute(Constants.SESSION_USER_NAME, userName);
            session.setAttribute(Constants.SESSION_NICK_NAME, params.getNickName());
            String sessionID = session.getId();
            // 写入cookie
            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");

            Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionID);
            Cookie d = new Cookie(Constants.COOKIE_USER_NAME, userName);
            Cookie e = new Cookie(Constants.COOKIE_NICK_NAME, params.getNickName());
            c.setPath("/");
            d.setPath("/");
            e.setPath("/");
            String cookieDomain = ConfigurationUtil.getString("cookie_domain");
            c.setDomain(cookieDomain);
            d.setDomain(cookieDomain);
            e.setDomain(cookieDomain);
            c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            response.addCookie(c);
            response.addCookie(d);
            response.addCookie(e);
            getResponse().getWriter().print(info);
            logger.info("注册成功");
            return null;
        }
    }

    /**
     * 校验手机号在系统是否存在
     * 
     * @return
     * @throws Exception
     */
    public String mobileNumberIsTrue() throws Exception {

        try {
            // Pattern pattern = Pattern.compile("^[1]([3,4,5,7,8][0-9]{9})$");
            // Matcher matcher = pattern.matcher(mobile);
            if (!StringUtil.isMobile(mobile)) {
                logger.info("手机号码格式不正确:{}", mobile);
                getResponse().getWriter().print(0);
            } else {
                int result = 1;
                String key = VALID_MOBILE_TIME_KEY.concat(getRequest().getSession().getId());
                String times = jedisCluster.get(key);
                int count = null == times ? 0 : Integer.parseInt(times);
                if (count < 10) {// 校验次数
                    count++;
                    jedisCluster.set(key, count + "");
                    jedisCluster.expire(key, 120);
                    if (securityCentreService.mobileIsUsed(mobile)) {
                        result = 0;// 手机号码已被绑定
                    }
                    getResponse().getWriter().print(result);
                } else {
                    getResponse().getWriter().print(0);
                }
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public String wapRegist() {
        // 校验手机
        boolean flag = false;
        try {
            Pattern p =
                    Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobile);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        if (!flag) {
            logger.error("注册过程中出错：" + "注册手机号非法");
            return ERROR;
        }
        if (!StringUtil.withinRange(registPassword, 6, 20)) {
            logger.error("注册过程中出错：" + "注册密码非法");
            return ERROR;
        }
        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();
        String veCode = request.getParameter("veCode");
        String sVeCode = (String) session.getAttribute("regImg");
        session.removeAttribute("regImg");
        if ((null == veCode || !veCode.equals(sVeCode))) {
            logger.error("注册过程中出错：" + "注册信息非法");
            return ERROR;
        }
        boolean isMobile = true; // 检测是否手机端打开。
        User userRe = new User();
        userRe.setLoginAccount(StringUtil.getLoginAccount(Constants.CUSTOMER_TYPE_SD_MEMBER));
        userRe.setLoginPassword(MD5.getMD5Str(registPassword));
        userRe.setMobile(mobile);
        userRe.setCustomerTypeId(Constants.CUSTOMER_TYPE_COMMON_MEMBER);
        try {
            // 调用推广返利接口 update by hl 5-14 从cookie中取
            String invitationId = "";
            String organizations = "";
            Cookie[] cooks = request.getCookies();
            String source = "";
            String flSource = "";
            String yunSource = "";
            for (Cookie cook : cooks) {
                if ("invitationId".equals(cook.getName())) {
                    invitationId = cook.getValue();
                } else if ("organizations".equals(cook.getName())) {
                    organizations = cook.getValue();
                } else if (com.kmzyc.b2b.util.CPSUtils.B2B_CPS_YQF_COOKIE_KEYS[0]
                        .equals(cook.getName())) {
                    source = cook.getValue();
                } /*
                   * else if (Constants.COOKIE_FANLI_CHANNEL_ID.equals(cook.getName())) { flSource =
                   * cook.getValue(); } else if ("sruleId".equals(cook.getName())) { // 是规则的标示
                   * yunSource = cook.getValue(); }
                   */
            }
            int Platfrom = Constants.REGISTER_PLATFORM_DEFAULT;
            /*
             * if (null != yunSource && !"".equals(yunSource)) { Platfrom =
             * Constants.REGISTER_PLATFORM_YUNSHANG; } else if (null != flSource &&
             * !"".equals(flSource)) { Platfrom = Constants.REGISTER_PLATFORM_FL; } else
             */ if (null != source && !"".equals(source)) {
                Platfrom = Constants.REGISTER_PLATFORM_CPS;
            }
            Map<String, String> o = registservice.registReward(isMobile, userRe, invitationId,
                    organizations, Constants.REGISTER_DEVICE_WAP, Platfrom);
            try {
                // --start add by hl,敲金蛋的来源记录
                if ("true".equals(ConfigurationUtil.getString("regist_source"))
                        && isInTime(new Date())) {
                    jedisCluster.sadd(ConfigurationUtil.getString("b2b_xhw_resource_uid"),
                            o.get("loginId"));
                    String XHW_USER_SOURCE = ConfigurationUtil.getString("xhw_user_source");
                    if (XHW_USER_SOURCE.equals(source)) {// 表示是新华网来的注册客户
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("userId", o.get("loginId"));
                        map.put("resource", XHW_USER_SOURCE);
                        securityCentreService.updateSource(map);
                    }
                }
                // --end 敲金蛋来源记录
            } catch (Exception e) {
                logger.error("", e);
            }
            if (o.get("success") == null) {
                logger.error("注册过程中出错：" + o.get("Exception"));
                return ERROR;
            }
            info = "1";

            String userName = userRe.getLoginAccount();

            if (!StringUtil.isEmpty(userRe.getMobile())) {
                userName = userRe.getMobile();
            }

            userRe.setLoginId(Long.valueOf(o.get("loginId")));
            session.setAttribute(Constants.SESSION_USER_ID, userRe.getLoginId());
            session.setAttribute(Constants.SESSION_USER_NAME, userName);
            session.setAttribute(Constants.SESSION_NICK_NAME, userRe.getNickName());
            String sessionID = session.getId();
            // 写入cookie
            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");

            Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionID);
            Cookie d = new Cookie(Constants.COOKIE_USER_NAME, userName);
            Cookie e = new Cookie(Constants.COOKIE_NICK_NAME, userRe.getNickName());
            c.setPath("/");
            d.setPath("/");
            e.setPath("/");
            String cookieDomain = ConfigurationUtil.getString("cookie_domain");
            c.setDomain(cookieDomain);
            d.setDomain(cookieDomain);
            e.setDomain(cookieDomain);
            c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            response.addCookie(c);
            response.addCookie(d);
            response.addCookie(e);
            getResponse().getWriter().print(info);
            logger.info("注册成功");
        } catch (Exception e) {
            logger.error("注册过程中出错：", e);
            return ERROR;
        }
        return null;
    }

    /**
     * 判断时间是否在2015-10-21 和 2015-10-27 在：true 不在：false
     */
    private boolean isInTime(Date nowTime) {
        boolean result = false;
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            now.setTime(nowTime);
            startTime.setTime(sim.parse(ConfigurationUtil.getString("golden_startTime")));
            endTime.setTime(sim.parse(ConfigurationUtil.getString("golden_endTime")));
            if (now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0) {
                result = true;
            }
        } catch (Exception e) {
            logger.info("注册判断时间是否在活动时间之内出错" + e.getMessage(), e);
        }
        return result;
    }



    /**
     * wap 注册校验四位数校验码是否填写正确
     * 
     * @return
     * @throws Exception
     */
    public String wapRegistNumber() throws Exception {
        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();
        String veCode = request.getParameter("veCode");
        String sVeCode = (String) session.getAttribute("regImg");
        String resultMessage = "wrong";
        if ((null != veCode && veCode.equals(sVeCode))) {
            resultMessage = "right";
        }
        getResponse().getWriter().print(resultMessage);
        return null;
    }

    /**
     * PC端页面注册验证码
     * 
     * @return
     * @throws Exception
     */
    public String validCodeImgs() throws Exception {
        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();
        String code = request.getParameter("veCode");
        String sCode = (String) session.getAttribute("regImg");

        String resultMessage = "";
        if ((null == code || !code.equals(sCode))) { // 验证码不正确
            resultMessage = "0";
            getResponse().getWriter().print(resultMessage);
            return null;
        }
        resultMessage = "1";
        getResponse().getWriter().print(resultMessage);
        return null;
    }



    /* PC端注册 */

    public String phoneRegist() {
        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();

        User userR = new User();
        userR.setLoginAccount(StringUtil.getLoginAccount(Constants.CUSTOMER_TYPE_SD_MEMBER));
        userR.setLoginPassword(MD5.getMD5Str(registPassword));
        userR.setMobile(mobile);
        userR.setCustomerTypeId(Constants.CUSTOMER_TYPE_COMMON_MEMBER);
        try {
            /** 手机验证码验证begin */
            String sessionMobileVerifyCode =
                    (String) session.getAttribute("OrderTrailMobileVerifyCode");
            // 获取Session 中的手机号码
            String sessinonMobilePhone = (String) session.getAttribute("mobilePhone");
            if (sessionMobileVerifyCode == null || verificationCode == null || mobile == null
                    || !verificationCode.trim().equals(sessionMobileVerifyCode.trim())
                    || sessinonMobilePhone == null || !sessinonMobilePhone.equals(mobile.trim())) {
                getResponse().sendRedirect("/html/regist.htm");
                return NONE;
            }
            Cookie[] cooks = request.getCookies();
            String source = "";
            for (Cookie cook : cooks) {
                if (com.kmzyc.b2b.util.CPSUtils.B2B_CPS_YQF_COOKIE_KEYS[0].equals(cook.getName())) {
                    source = cook.getValue();
                }
            }
            int Platfrom = Constants.REGISTER_PLATFORM_DEFAULT;

            if (null != source && !"".equals(source)) {
                Platfrom = Constants.REGISTER_PLATFORM_CPS;
            }

            // 调用推广返利接口 update by hl 5-14 从cookie中取
            Map<String, String> o =
                    registservice.registPCPhone(userR, Constants.REGISTER_DEVICE_PC, Platfrom);
            if (o.get("success") == null) {
                logger.error("注册过程中出错：" + o.get("Exception"));
                return ERROR;
            }
            // --start add by hl,敲金蛋的来源记录
            if ("true".equals(ConfigurationUtil.getString("regist_source"))
                    && isInTime(new Date())) {
                jedisCluster.sadd(ConfigurationUtil.getString("b2b_xhw_resource_uid"),
                        o.get("loginId"));
                String XHW_USER_SOURCE = ConfigurationUtil.getString("xhw_user_source");
                if (XHW_USER_SOURCE.equals(source)) {// 表示是新华网来的注册客户
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("userId", o.get("loginId"));
                    map.put("resource", XHW_USER_SOURCE);
                    securityCentreService.updateSource(map);
                }
            }
            // --end 敲金蛋来源记录
            info = "1";

            String userName = userR.getLoginAccount();

            if (!StringUtil.isEmpty(userR.getMobile())) {
                userName = userR.getMobile();
            }

            String nickName = userR.getNickName();
            if (StringUtil.isEmpty(nickName)) {
                nickName = userName;
            }

            userR.setLoginId(Long.valueOf(o.get("loginId")));
            session.setAttribute(Constants.SESSION_USER_ID, userR.getLoginId());
            session.setAttribute(Constants.SESSION_USER_NAME, userName);
            session.setAttribute(Constants.SESSION_NICK_NAME, nickName);
            String sessionID = session.getId();
            // 写入cookie
            HttpServletResponse response = this.getResponse();
            response.setHeader("P3P",
                    "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionID);
            Cookie d = new Cookie(Constants.COOKIE_USER_NAME,
                    java.net.URLEncoder.encode(userName, "UTF-8")/* loginAccount */);
            Cookie e = new Cookie(Constants.COOKIE_NICK_NAME,
                    java.net.URLEncoder.encode(nickName, "UTF-8")/* loginAccount */);
            c.setPath("/");
            d.setPath("/");
            e.setPath("/");
            String cookieDomain = ConfigurationUtil.getString("cookie_domain");
            c.setDomain(cookieDomain);
            d.setDomain(cookieDomain);
            e.setDomain(cookieDomain);
            c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
            response.addCookie(c);
            response.addCookie(d);
            response.addCookie(e);
            getResponse().getWriter().print(info);
            logger.info("注册成功");
        } catch (Exception e) {
            logger.error("注册过程中出错：", e);
            return ERROR;
        }
        return null;
    }

    /**
     * 注册成功
     * 
     * @return
     * @throws Exception
     */
    public String regSuccess() throws Exception {
        regInfo = new RegistedInfo();
        regInfo.setMail(registEmail);
        regInfo.setMailLink(MailUtils.getMailLink(registEmail));
        /*
         * regInfo.setRegPoint(userGrowingService.getScoreNumber("RU0001", new HashMap<String,
         * String>())); regInfo.setMailValidPoint(userGrowingService.getScoreNumber("RU0002", new
         * HashMap<String, String>()));
         */

        info = "1";
        getResponse().getWriter().print(info);
        getRequest().setAttribute("uid",
                getRequest().getSession().getAttribute(Constants.SESSION_USER_ID));
        return SUCCESS;
    }

    public String wapRegSuccess() {
        // 截取手机前三位和后三位
        getRequest().setAttribute("uid",
                getRequest().getSession().getAttribute(Constants.SESSION_USER_ID));
        headMobile = mobile.substring(0, 3);
        bottomMobile = mobile.substring(8);
        return SUCCESS;
    }

    public String phoneRegSuccess() throws Exception {
        // 截取手机前三位和后三位
        headMobile = mobile.substring(0, 3);
        bottomMobile = mobile.substring(8, 11);
        regInfo = new RegistedInfo();
        /*
         * regInfo.setRegPoint(userGrowingService.getScoreNumber("RU0001", new HashMap<String,
         * String>())); regInfo.setMobileValidPoint(userGrowingService.getScoreNumber("RU0003", new
         * HashMap<String, String>()));
         */
        getRequest().setAttribute("uid",
                getRequest().getSession().getAttribute(Constants.SESSION_USER_ID));
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegistedInfo getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(RegistedInfo regInfo) {
        this.regInfo = regInfo;
    }

    public String getRegistEmail() {
        return registEmail;
    }

    public void setRegistEmail(String registEmail) {
        this.registEmail = registEmail;
    }

    public String getRegistPassword() {
        return registPassword;
    }

    public void setRegistPassword(String registPassword) {
        this.registPassword = registPassword;
    }

    public String getVeCode() {
        return veCode;
    }

    public void setVeCode(String veCode) {
        this.veCode = veCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getValidType() {
        return validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public User getReqUser() {
        return reqUser;
    }

    public void setReqUser(User reqUser) {
        this.reqUser = reqUser;
    }

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }


    public String getHeadMobile() {
        return headMobile;
    }

    public void setHeadMobile(String headMobile) {
        this.headMobile = headMobile;
    }

    public String getBottomMobile() {
        return bottomMobile;
    }

    public void setBottomMobile(String bottomMobile) {
        this.bottomMobile = bottomMobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
