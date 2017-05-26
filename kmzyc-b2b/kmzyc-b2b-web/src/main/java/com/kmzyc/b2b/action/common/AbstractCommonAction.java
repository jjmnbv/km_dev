package com.kmzyc.b2b.action.common;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

public abstract class AbstractCommonAction extends BaseAction {

    private static final long serialVersionUID = 238894246839232778L;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回失败，类似于SUCCESS
     */
    protected static final String FAIL = "FAIL";


    @Resource(name = "messageRemoteService")
    protected MessageRemoteService messageRemoteService;

    /**
     * 合法请求域
     */
    protected String[] LEGAL_DOMAIN = {"v.kmb2b.com", "m.kmb2b.com"};

    /**
     * 请求domain
     */
    protected final String REQUEST_DOMAIN = getRequest().getScheme() + "://"
            + getRequest().getServerName()
            + (80 == getRequest().getServerPort() ? "" : (":" + getRequest().getServerPort()));

    /**
     * 从请求头中获取请求来源url
     */
    protected String getReferUrl() {
        return getRequest().getHeader("Referer");
    }

    /**
     * url 是否被包含在 domain数组中
     */
    public static boolean containsAny(String url, String... domain) {
        for (String d : domain) {
            if (url.contains(d)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否为非法请求来源
     */
    protected boolean illegalReferUrl(String url, String mobile) throws IOException {
        logger.info("来源打印：" + url);
        if (StringUtil.isEmpty(url) || !containsAny(url, ConfigurationUtil.getString("source_Url"))
                && !containsAny(url, LEGAL_DOMAIN)) {
            logger.info("非正常来源  " + mobile + " 来源：" + url);
            printJson("0");
            return true;
        }

        return false;
    }

    /**
     * 是否为非法短信发送时间
     */
    protected boolean illegalSendMessageTimeOnMobile(String mobile) throws IOException {
        // Calendar cal = Calendar.getInstance();
        // int hours = cal.get(Calendar.HOUR_OF_DAY);// wk 20150908 紧急处理 2016-05-18
        /*
         * if (hours >= 23 || hours < 8) { logger.info("非发送次短信时间  " + mobile + " 当前小时：" + hours);
         * printJson("0"); return true; }
         */

        return false;
    }


    /**
     * 号码短信发送异常：过于频繁、超过发送上限
     */
    protected boolean abnormalSendMessageByMobile(String mobile) throws IOException {
        int valid = messageRemoteService.validate(mobile, null);// 类型预留
        if (valid == 2) {
            logger.info("{} 号码短信发送过频繁了", mobile);
            printJson("0");
            return true;
        } else if (valid == 1) {
            logger.info("{} 号码短信发送次数上限了", mobile);
            printJson("2");
            return true;
        }

        return false;
    }

    /**
     * 非法图形验证码
     */
    protected boolean illegalGraphValidCode(String veCode) throws IOException {
        String sVeCode = getAttribute("wapRegistCopy");
        if (veCode == null || !veCode.equals(sVeCode)) {
            logger.info("非法发送短信   验证码不正确！request 获得验证码为：" + veCode + ", session获得验证码为:" + sVeCode);
            printJson("3");
            return true;
        }

        return false;
    }


    /***
     * 获取短信内容类型
     *
     * @return
     */
    protected MessageTypeEnum getMessageType(String sendSource) {
        MessageTypeEnum messageType = null;
        if ("1".equals(sendSource)) {// 重置密码
            messageType = MessageTypeEnum.RESETPWD;
        } else if ("2".equals(sendSource)) {// 免注册转会员
            messageType = MessageTypeEnum.NOREGISTERTOMEMBER;
        }

        return messageType;
    }

    /**
     * struts2返回json
     *
     * @param result 需要返回的数据
     */
    protected void printJson(String result) throws IOException {
        getResponse().getWriter().print(result);
    }

    protected void printJson(int result) throws IOException {
        getResponse().getWriter().print(result);
    }

    /**
     * 获取request中的属性
     */
    protected String getParameter(String key) {
        return getRequest().getParameter(key);
    }


    /**
     * 获取session中的属性
     */
    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(String key) {
        Object obj = getSession().getAttribute(key);
        return (T) obj;
    }

    /**
     * 设置session中的属性
     */
    protected void setAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 移除session中的属性
     */
    protected void removeAttribute(String key) {
        getSession().removeAttribute(key);
    }

    protected void setCookies(String account, String sessionId) {
        getResponse().setHeader("P3P",
                "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");

        Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionId);
        Cookie d = new Cookie(Constants.COOKIE_USER_NAME, account);
        Cookie e = new Cookie(Constants.COOKIE_NICK_NAME, account);

        c.setPath("/");
        c.setDomain(ConfigurationUtil.getString("cookie_domain"));
        c.setMaxAge(ConfigurationUtil.getIntValue("b2b_login_memCached_time"));

        d.setPath("/");
        d.setDomain(ConfigurationUtil.getString("cookie_domain"));
        d.setMaxAge(ConfigurationUtil.getIntValue("b2b_login_memCached_time"));

        e.setPath("/");
        e.setDomain(ConfigurationUtil.getString("cookie_domain"));
        e.setMaxAge(ConfigurationUtil.getIntValue("b2b_login_memCached_time"));

        getResponse().addCookie(c);
        getResponse().addCookie(d);
        getResponse().addCookie(e);
    }

    /**
     * 获取cookies
     */
    protected Cookie[] getCookies() {
        return getRequest().getCookies();
    }


    /* *//***
          * 建议使用 getParameter()
          */
    /*
     * @Override
     * 
     * @Deprecated public HttpServletRequest getRequest() { return super.getRequest(); }
     *//***
       * 建议使用 printJson()
       */
    /*
     * @Override
     * 
     * @Deprecated public HttpServletResponse getResponse() { return super.getResponse(); }
     *//***
       * 建议使用 getSessionAttr() setSessionAttr() removeSessionAttr()
       */
    /*
     * @Override
     * 
     * @Deprecated public HttpSession getSession() { return super.getSession(); }
     */

}
