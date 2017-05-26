package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.app.AppBaseAction;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.shopcart.util.ShopCartUtil;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

public abstract class ThirdLoginAction extends AppBaseAction {
    /**
     * 对于所有 第三方登陆账号登录 kmb2b首页的 Action 需继承此类 具体调用 super.doLogin(User user,String loginName,String
     * passwordcode,String cmsServerPath) 即可成功跳到首页 完成kmb2b的登录
     */
    private static final long serialVersionUID = -1661494042397006800L;
    private int B2B_LOGIN_MEMCACHED_TIME =
            Integer.parseInt(ConfigurationUtil.getString("b2b_login_memCached_time"));
    // 登录名key前缀
    // private String memNameKey = ConfigurationUtil.getString("b2b_login_name_memcached_key_prex");
    // 登录密码key前缀
    // private String memPWDKey = ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex");
    // private String cookieDomain = ConfigurationUtil.getString("cookie_domain");
    // static Logger logger = Logger.getLogger(ThirdLoginAction.class);
    private static Logger logger = LoggerFactory.getLogger(ThirdLoginAction.class);
    // 登录客户端ip
    // private String memIpKey = ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex");

    /**
     * 当正式用户操作绑定关系(主动绑定,立即解绑)时,绑定结果的相关提示
     */
    public String bindTip;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource
    private ShopCartInfoService shopCartInfoService;

    /**
     * 登录 由于这个方法是第三方登录账号绑定康美会员后的必经方法, 在这个方法里面只需要将第三方账号绑定康美会员的一些信息存入到session和cookie即可
     * 
     * @return
     * @throws IOException
     * @throws Exception
     */
    public void putLoginInfoToSession(User loginUser, String clientIp) throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P",
                "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        // 将第三方所绑定的会员的信息写入到session
        final HttpSession session = ServletActionContext.getRequest().getSession();
        if (clientIp == null) {
            clientIp = "";
        }
        Long loginId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        if (loginUser != null && 1 == loginUser.getStatus()) {
            // 若是第三方登录所绑定的康美会员如果没有nickName,则直接存第三方登录用户的nickName
            // String thirdNickName=(String)session.getAttribute("nikeName");
            String nickName = null == loginUser.getNickName() ? "" : loginUser.getNickName();
            session.setAttribute(Constants.SESSION_USER_NAME,
                    loginUser.getLoginAccount().toLowerCase());
            session.setAttribute(Constants.SESSION_USER_ID, loginUser.getLoginId());
            session.setAttribute(Constants.SESSION_NICK_NAME, nickName);

            logger.info("ThirdLoginAction 放入session值: SESSION_USER_NAME="
                    + loginUser.getLoginAccount().toLowerCase() + ",SESSION_USER_ID="
                    + loginUser.getLoginId());

            String sessionId = session.getId();

            Date date = new Date(B2B_LOGIN_MEMCACHED_TIME * 1000);// 有效时间
            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_login_name_memcached_key_prex") + sessionId,
                    loginUser.getLoginAccount().toLowerCase(), date);


            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_login_pwd_memcached_key_prex") + sessionId,
                    StringUtils.isBlank(loginUser.getLoginPassword()) == true ? "defaultPwdThird"
                            : loginUser.getLoginPassword(),
                    date);
            memCachedClient.set(
                    ConfigurationUtil.getString("b2b_client_ip_memcached_key_prex") + sessionId,
                    clientIp + "defaultClientIp");

            session.removeAttribute("loginTime");
            try {

                // 20160127 update 将是否是第三方需要完善信息用户标识抽奖页面获取

                String isTempMember =
                        (String) session.getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER);

                // 写入cookie
                Cookie c = new Cookie(Constants.COOKIE_SESSION_ID, sessionId);
                Cookie d = new Cookie(Constants.COOKIE_USER_NAME,
                        java.net.URLEncoder.encode(loginUser.getLoginAccount(), "UTF-8"));
                Cookie e = new Cookie(Constants.COOKIE_NICK_NAME,
                        java.net.URLEncoder.encode(nickName, "UTF-8"));

                Cookie f = new Cookie(ThirdConstant.THIRD_ISTEMP_MEMBER, isTempMember);

                c.setPath("/");
                d.setPath("/");
                e.setPath("/");
                f.setPath("/");

                c.setDomain(ConfigurationUtil.getString("cookie_domain"));
                d.setDomain(ConfigurationUtil.getString("cookie_domain"));
                e.setDomain(ConfigurationUtil.getString("cookie_domain"));
                f.setDomain(ConfigurationUtil.getString("cookie_domain"));

                e.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
                c.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
                d.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);
                f.setMaxAge(B2B_LOGIN_MEMCACHED_TIME);

                response.addCookie(c);
                response.addCookie(d);
                response.addCookie(e);
                response.addCookie(f);


                // 若已到登录页登录过,则将不会再次记录登录记录
                if (null == loginId || loginUser.getLoginId().longValue() != loginId.longValue()) {
                    loginService.loginRecord(loginUser, clientIp);
                }
                // shopCarService.mergeShopCar(loginId.toString(),
                // ShopCartUtil.getTempUserId(getRequest(), response));
                shopCartInfoService.mergeShopCar(loginUser.getLoginId().toString(),
                        ShopCartUtil.getTempUserId(getRequest(), getResponse()));
                // shopCarService.mergeShopCar(loginUser.getLoginId().toString(),
                // ShopCartUtil.getTempUserId(getRequest(), response));
                // shopCarService.loginAfterChangeShopCar(session);
            } catch (Exception e) {
                logger.error("ThirdLoginAction ---->将信息存入session发生异常" + e.getMessage(), e);
            }
        }
    }

    public int cookieUserSource() {
        HttpServletRequest request = this.getRequest();
        Cookie[] cooks = request.getCookies();
        String source = "";
        String flSource = "";
        for (Cookie cook : cooks) {
            if (com.kmzyc.b2b.util.CPSUtils.B2B_CPS_YQF_COOKIE_KEYS[0].equals(cook.getName())) {
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

        return Platfrom;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public String getBindTip() {
        return bindTip;
    }

    public void setBindTip(String bindTip) {
        this.bindTip = bindTip;
    }
}
