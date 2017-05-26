package com.kmzyc.b2b.ajax;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.util.CookieUtil;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

import redis.clients.jedis.JedisCluster;

@Controller("userBaseAction")
@Scope("prototype")
public class UserBaseAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    public static final String VALID_COUNT = "valid.count.";

    protected static final int MAX_NAME_ERROR_COUNT = 5;// 用户名最大错误次数
    protected static final int MAX_IP_ERROR_COUNT = 50;// IP最大错误次数

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    // private static final Logger logger = Logger.getLogger(LoginAction.class);
    private static Logger logger = LoggerFactory.getLogger(UserBaseAction.class);

    /**
     * 写入登录cookie
     * 
     * @param user
     * @param persistent
     */
    protected void writeLoginCoodie(User user, boolean persistent, String ip) {
        HttpSession session = getRequest().getSession();
        HttpServletResponse response = getResponse();
        String sessionId = session.getId();
        response.setHeader("P3P",
                "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        EraInfo eraInfo = user.getEarInfo();
        String nickName = null == user.getNickName() ? "" : user.getNickName();
        String userName = user.getLoginAccount();
        if (null != eraInfo && null != eraInfo.getEraNo()) {
            userName = eraInfo.getEraNo();
        } else {
            if (!StringUtil.isEmpty(user.getMobile())) {
                userName = user.getMobile();
            }
        }


        if (null == eraInfo) { // 健康会员
            session.setAttribute(Constants.SESSION_B2B_OR_ERA, Constants.KM_USER_TYPE);
        } else {
            session.setAttribute(Constants.SESSION_B2B_OR_ERA, Constants.LOGINTYPE);
            session.setAttribute(Constants.SESSION_Zx_USER_NAME, eraInfo.getLoginAccount());
        }
        session.setAttribute(Constants.SESSION_USER_ID, user.getLoginId());
        session.setAttribute(Constants.SESSION_USER_NAME, userName);
        session.setAttribute(Constants.SESSION_USER_HEAD_PIC, user.getHeadSculpture());
        session.setAttribute(Constants.SESSION_NICK_NAME, nickName);

        if (persistent) {
            Date date = new Date(
                    Integer.parseInt(ConfigurationUtil.getString("b2b_login_memCached_time"))
                            * 1000L);
            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_login_name_memcached_key_prex") + sessionId,
                    userName, date);
            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex") + sessionId,
                    user.getLoginPassword(), date);
            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex") + sessionId, ip,
                    date);
        }
        try {
            String COOKIE_DOMAIN = ConfigurationUtil.getString("cookie_domain");// cookie

            // CookieUtil.createCookie(response,"JSESSIONID",sessionId,COOKIE_DOMAIN,-1);
            CookieUtil.createCookie(response, Constants.COOKIE_SESSION_ID, sessionId, COOKIE_DOMAIN,
                    -1);
            CookieUtil.createCookie(response, Constants.SESSION_B2B_OR_ERA,
                    null == eraInfo ? Constants.KM_USER_TYPE : Constants.LOGINTYPE, COOKIE_DOMAIN,
                    -1);
            CookieUtil.createCookie(response, Constants.COOKIE_NICK_NAME,
                    java.net.URLEncoder.encode(nickName, "UTF-8"), COOKIE_DOMAIN, -1);
            CookieUtil.createCookie(response, Constants.COOKIE_USER_NAME,
                    java.net.URLEncoder.encode(userName, "UTF-8"), COOKIE_DOMAIN, -1);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 移除session
     *
     */
    protected void removeLoginInfo() {
        HttpSession session = getSession();
        session.removeAttribute(Constants.SESSION_B2B_OR_ERA);
        session.removeAttribute(Constants.SESSION_USER_ID);
        session.removeAttribute(Constants.SESSION_USER_NAME);
        session.removeAttribute(Constants.SESSION_NICK_NAME);
        session.removeAttribute(Constants.SESSION_Zx_USER_NAME);

        session.removeAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER);
        session.removeAttribute("thirdLoginType");
        session.removeAttribute("nikeName");
        session.removeAttribute("userImg");
        session.removeAttribute("openId");
        session.removeAttribute(Constants.SESSION_WX_OPENID);
        session.removeAttribute(ThirdConstant.IS_FANLI_USER);

        // 获取cookie
        HttpServletResponse response = this.getResponse();
        response.setHeader("P3P",
                "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        String loginSessionID = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            boolean scookie = false;
            boolean lcookie = false;
            for (Cookie c : cookies) {
                if (Constants.COOKIE_SESSION_ID.equals(c.getName())) {
                    loginSessionID = c.getValue();
                    scookie = true;
                }
                if (Constants.SESSION_B2B_OR_ERA.equals(c.getName())) {
                    // 清除登录来源cookie
                    Cookie loginFlag = new Cookie(Constants.SESSION_B2B_OR_ERA, null);
                    loginFlag.setMaxAge(0);
                    loginFlag.setPath("/");
                    loginFlag.setDomain(ConfigurationUtil.getString("cookie_domain"));
                    response.addCookie(loginFlag);
                    lcookie = true;
                }
                if (scookie && lcookie) {
                    break;
                }
            }
        }
        if (!StringUtil.isEmpty(loginSessionID)) {
            memCachedClient.delete(ConfigurationUtil.getString("b2b_login_name_memcached_key_prex")
                    + loginSessionID);
            memCachedClient.delete(ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex")
                    + loginSessionID);
            memCachedClient.delete(ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex")
                    + loginSessionID);
        }
    }

    /**
     * 验证失败次数
     * 
     * @param key 一般是loginName
     * @return 0 不合法 其它：合法次数
     */
    protected Integer getErrorCount(String key) {
        String nameCount = jedisCluster.get(VALID_COUNT + key);
        String ip = HttpUtils.getIP(getRequest());
        String ipCount = jedisCluster.get(VALID_COUNT + ip);
        int count = 0;
        if (null != ipCount && (count = Integer.parseInt(ipCount)) >= MAX_IP_ERROR_COUNT) {
            return 0;
        } else if (nameCount != null
                && (count = Integer.parseInt(nameCount)) >= MAX_NAME_ERROR_COUNT) {
            return 0;
        } else if (ipCount == null || nameCount == null) {
            return -1;
        } else {
            return count;
        }
    }

    /***
     * 失败次数自增,失败后调用
     * 
     * @param key 一般是loginName
     */
    protected boolean incrErrorCount(String key) {
        boolean isOut = false;
        String nkey = VALID_COUNT + key;
        String ip = HttpUtils.getIP(getRequest());
        long count = 0;
        if (1 == (count = jedisCluster.incr(nkey))) {
            jedisCluster.expire(nkey, 3600 * 2);
        }
        isOut = count + 1 > MAX_NAME_ERROR_COUNT;

        nkey = VALID_COUNT + ip;
        count = 0;
        if (1 == (count = jedisCluster.incr(nkey))) {
            jedisCluster.expire(nkey, 600);
        }
        return isOut || (count + 1 > MAX_IP_ERROR_COUNT);
    }
}
