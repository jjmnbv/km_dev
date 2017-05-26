package com.kmzyc.framework.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.util.Base64Util;
import com.kmzyc.promotion.util.UserChannelContextHolder;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings({"serial"})
@Component("loginCheckFilter")
public class LoginCheckFilter extends HttpServlet implements Filter {

    // private static Logger logger = Logger.getLogger(LoginCheckFilter.class);
    private static Logger logger = LoggerFactory.getLogger(LoginCheckFilter.class);

    // 登录名key前缀
    private String memNameKey = ConfigurationUtil.getString("b2b_login_name_memcached_key_prex");
    // 登录客户端ip
    private String memIpKey = ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex");
    // 登录密码key前缀
    private String memPWDKey = ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex");
    // wapPath
    private String WAP_PATH = ConfigurationUtil.getString("staticPath_WAP");
    // APP用户类型
    private String APP_LOGIN_USER_TYPE = ConfigurationUtil.getString("b2b_km_app_user_type");
    // APP用户用户ID
    private String APP_LOGIN_FLAG = ConfigurationUtil.getString("b2b_km_app_user_id");
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    // APP密钥
    private String APP_SECRETKEY = ConfigurationUtil.getString("app_secretkey");
    // APP token前缀
    private String APP_TOKEN_PREX = ConfigurationUtil.getString("b2b_app_token_prex");
    protected String B2B_KM_APP_INFO_KEY = ConfigurationUtil.getString("b2b_km_app_info_key");// 设备信息
    protected String KM_APP_ID_KEY = ConfigurationUtil.getString("b2b_km_app_id_key");// 设备ID

    // APP Token 有效时间
    private static final long APP_TOKENT_ALIVE_PERIOD = 300000l;
    // APP Token 预警时间
    private static final long APP_TOKENT_ALIVE_ALERT = 1200000l;

    // APP校验返回码：APP校验0:非法请求;-1:token失效;1:正常请求;2:参数错误
    private static final int APP_CHECK_ILLEGAL_REQUEST = 0;
    private static final int APP_CHECK_OUT_SESSION = -1;
    private static final int APP_CHECK_LEGAL_REQUEST = 1;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String url = req.getRequestURI();
        String host = req.getHeader("host");
        if (url.startsWith("/app/") && url.endsWith(".action")
                && !"/app/illegalUrl.action".equals(url)) {
            // 设置用户渠道号
            UserChannelContextHolder.setUserChannel(Constants.CHANNEL_APP);
            int flag = appCheck(req);
            if (APP_CHECK_LEGAL_REQUEST == flag) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (APP_CHECK_OUT_SESSION == flag) {
                req.getRequestDispatcher(
                        "/app/illegalUrl.action?flag=" + flag + "&token=" + genToken(req)).forward(
                        servletRequest, servletResponse);
            } else {
                req.getRequestDispatcher("/app/illegalUrl.action?flag=" + flag).forward(
                        servletRequest, servletResponse);
            }
            return;
        } else {
            HttpSession session = req.getSession();
            String comPareUrl = url.substring(0, url.indexOf(".action"));
            comPareUrl = comPareUrl.substring(comPareUrl.lastIndexOf("/") + 1);
            if (!LoginFilterMap.getMap().containsKey(comPareUrl) && session.getAttribute
                    (Constants.SESSION_USER_ID)==null) {
                HttpServletResponse response = ((HttpServletResponse) servletResponse);
                HttpServletRequest request = ((HttpServletRequest) servletRequest);
                response.setHeader("P3P",
                        "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
                Cookie[] cs = request.getCookies();
                String sessionId = null;
                String uname = null;
                if (cs != null) {
                    for (Cookie c : cs) {
                        if (c.getName().equals(Constants.COOKIE_SESSION_ID)) {
                            sessionId = c.getValue();
                        } else if (c.getName().equals(Constants.COOKIE_USER_NAME)) {
                            uname = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                        }
                    }
                }
                if (null != sessionId && sessionId.length() > 1) {
                    String UserName = (String) memCachedClient.get(memNameKey + sessionId);
                    String password = (String) memCachedClient.get(memPWDKey + sessionId);
                    String clientIp = (String) memCachedClient.get(memIpKey + sessionId);
                    if (!StringUtil.isEmpty(UserName) && UserName.equalsIgnoreCase(uname)
                            && !StringUtil.isEmpty(password)) {
                        User params = new User();
                        params.setLoginAccount(uname);
                        params.setLoginPassword(password);
                        try {
                            User dataUser = loginService.login(params);
                            if (null != dataUser && 1 == dataUser.getStatus()) {
                                logger.info("登录名：" + dataUser.getLoginAccount()
                                        + ",“自动”登录系统b2b；登录IP：" + clientIp);
                                String nickName =
                                        null == dataUser.getNickName() ? "" : dataUser
                                                .getNickName();
                                session.setAttribute(Constants.SESSION_USER_ID,
                                        dataUser.getLoginId());
                                session.setAttribute(Constants.SESSION_USER_NAME, dataUser
                                        .getLoginAccount().toLowerCase());
                                session.setAttribute(Constants.SESSION_NICK_NAME, nickName);
                                loginService.loginRecord(dataUser, clientIp);
                                memCachedClient.set(memNameKey + sessionId, UserName);
                                memCachedClient.set(memPWDKey + sessionId, password);
                                memCachedClient.set(memIpKey + sessionId, clientIp);
                                filterChain.doFilter(servletRequest, servletResponse);
                                return;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                // 自动登录
                String urlPara =
                        req.getRequestURL()
                                + (StringUtil.isEmpty(req.getQueryString()) ? "" : "?"
                                        + req.getQueryString());
                if (WAP_PATH.indexOf(host) > 0 || req.getRequestURL().indexOf("wap") > 0
                        || req.getRequestURL().indexOf("Wap") > 0) {
                    response.sendRedirect(req.getContextPath() + "/html/wapLogin.jsp?ReturnUrl="
                            + urlPara);
                } else {
                    response.sendRedirect(req.getContextPath() + "/html/loginPage.htm?ReturnUrl="
                            + urlPara);
                }
                return;
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
    }

    /**
     * 保持APP的token有效 有效返回timestamp,1个参数表示无需替换，2个参数1为老的，2为新的
     * 
     * @return
     */
    private boolean keepAppAlive(String token) {
        if (null == token || token.length() != 32) {
            return false;
        }
        String key = APP_TOKEN_PREX.concat(token);
        Long timeInMillis = (Long) memCachedClient.get(key);
        if (null != timeInMillis) {
            Calendar cal = Calendar.getInstance();
            if (cal.getTimeInMillis() - timeInMillis > APP_TOKENT_ALIVE_PERIOD) {
                // 有效期小于25分钟
                memCachedClient.set(key, cal.getTimeInMillis(), new Date(APP_TOKENT_ALIVE_ALERT));
                String userTypeKey = APP_TOKEN_PREX.concat(token).concat(APP_LOGIN_USER_TYPE);
                memCachedClient.set(userTypeKey, memCachedClient.get(userTypeKey), new Date(
                        APP_TOKENT_ALIVE_ALERT));
                String userIdKey = APP_TOKEN_PREX.concat(token).concat(APP_LOGIN_FLAG);
                memCachedClient.set(userIdKey, memCachedClient.get(userIdKey), new Date(
                        APP_TOKENT_ALIVE_ALERT));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * APP校验0:非法请求;-1:未登录;1:合法请求;2:参数错误
     * 
     * @param req
     * @return
     */
    /**
     * @param req
     * @return
     */
    private int appCheck(HttpServletRequest req) {
        int flag = APP_CHECK_ILLEGAL_REQUEST;
        try {
            String signData = null;// signData=md5(secretKey+token+parameters+timestam)/md5(SecretKey+设备ID/登录ID+timestamp)
            String timestamp = null, kmAppId = null, params = req.getParameter("parameters");
            boolean isLoginAction = false;
            if (null != (timestamp = req.getHeader("timeStamp"))
                    && null != (signData = req.getHeader("signData"))) {
                String url = req.getRequestURI();
                String action = url.substring(url.lastIndexOf("/") + 1, url.indexOf(".action"));
                String token = null;
                String deviceInfo = req.getHeader(B2B_KM_APP_INFO_KEY);
                if (null != deviceInfo) {// 从访问设备信息中分离出app_id_key
                    JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
                    kmAppId =
                            json.containsKey(KM_APP_ID_KEY) ? json.getString(KM_APP_ID_KEY) : null;
                    if (!StringUtil.isEmpty(kmAppId)) {
                        req.setAttribute("kmAppId", kmAppId);
                    }
                }
                if ("genToken".equals(action)
                        && null != kmAppId
                        && MD5.getMD5Str(APP_SECRETKEY.concat(kmAppId).concat(timestamp))
                                .equalsIgnoreCase(signData)) {// 如果所请求的链接为生成token的action，且传入的app_id_key不为空，签名按照约定可以校验通过
                    flag = APP_CHECK_LEGAL_REQUEST;// 生成token
                } else if (MD5.getMD5Str(
                        APP_SECRETKEY.concat(token = req.getHeader("token"))
                                .concat(null == params ? "" : params).concat(timestamp))
                        .equalsIgnoreCase(signData)) {
                    String uid =
                            (String) memCachedClient.get(APP_TOKEN_PREX.concat(token).concat(
                                    APP_LOGIN_FLAG));
                    boolean isAlive = false;
                    if (((isLoginAction = !LoginFilterMap.getAppUnLoginCheckList().contains(action)) && null != uid)
                            & (isAlive = keepAppAlive(token))) {// 后面只能用一个&，用来维持token有效
                        flag = APP_CHECK_LEGAL_REQUEST;
                    } else {
                        flag =
                                isLoginAction || !isAlive ? APP_CHECK_OUT_SESSION
                                        : APP_CHECK_LEGAL_REQUEST;
                    }
                    if (flag == APP_CHECK_LEGAL_REQUEST) {
                        req.setAttribute(APP_LOGIN_FLAG, uid);
                        req.setAttribute(
                                APP_LOGIN_USER_TYPE,
                                memCachedClient.get(APP_TOKEN_PREX.concat(token).concat(
                                        APP_LOGIN_USER_TYPE)));
                    }
                }
            }
        } catch (Exception e) {
            flag = APP_CHECK_ILLEGAL_REQUEST;

            logger.error("", e);
        }
        return flag;
    }

    private String genToken(HttpServletRequest request) {
        String token = null;
        try {
            String deviceInfo = request.getHeader(B2B_KM_APP_INFO_KEY);
            String kmAppId = null;
            if (null != deviceInfo) {
                JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
                kmAppId = json.containsKey(KM_APP_ID_KEY) ? json.getString(KM_APP_ID_KEY) : null;
            }
            if (null != kmAppId) {
                Calendar cal = Calendar.getInstance();
                token = MD5.getMD5Str(APP_SECRETKEY + kmAppId + cal.getTimeInMillis());
                memCachedClient.set(APP_TOKEN_PREX.concat(token), cal.getTimeInMillis(), new Date(
                        1200000));
            }
        } catch (Exception e) {
            logger.error("生成token失败", e);
        }
        logger.info("APP请求({})生成token({})",request.getRequestURI(),token);
        return token;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
