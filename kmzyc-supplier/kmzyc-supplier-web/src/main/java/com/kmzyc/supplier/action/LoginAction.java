package com.kmzyc.supplier.action;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.enums.SuppliersStatus;
import com.kmzyc.supplier.enums.SuppliersType;
import com.kmzyc.supplier.maps.ShopMainTypeMap;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.service.LoginService;
import com.kmzyc.supplier.service.MerchantInfoService;
import com.kmzyc.supplier.util.RedisCacheUtil;
import com.kmzyc.supplier.util.StringUtil;
import com.kmzyc.supplier.vo.CountInfo;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

import redis.clients.jedis.JedisCluster;

@Scope("prototype")
@Controller("loginAction")
public class LoginAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

    private static final int SUPPER_LOGIN_MEMCACHED_TIME =
            Integer.parseInt(ConfigurationUtil.getString("supplier_login_memCached_time"));

    // 登录名key前缀
    private static final String memNameKey =
            ConfigurationUtil.getString("supplier_login_name_memcached_key_prex");

    // 登录密码key前缀
    private static final String memPWDKey =
            ConfigurationUtil.getString("supplier_login_pwd_memcached_key_prex");

    @Resource
    private LoginService loginService;

    @Resource
    private MerchantInfoService merchantInfoService;

    @Resource
    private MemCachedClient memCachedClient;

    @Resource
    private CustomerRemoteService customerRemoteService;

    private String loginAccount;// 登陆账号

    private String loginPassWord;// 登陆密码

    private String info;

    private String checkboxVa;

    private String jsoncallback;

    private long loginId;

    private String url;// 活动中心前端页面传进来的返回url

    @Resource
    private JedisCluster jedisCluster;

    private final String gysReturnUrl = ConfigurationUtil.getString("gysReturnUrl");

    public String login() throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        HttpServletResponse response = this.getResponse();
        response.setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        String uname = null;
        if (null != loginAccount) {
            uname = java.net.URLDecoder.decode(loginAccount, "UTF-8").toLowerCase();
        }
        if (!StringUtil.withinRangeByte(uname, 6, 50) || !StringUtil.equalLen(loginPassWord, 32)) {
            return ERROR;
        }

        try {
            // 1.账号尝试登录次数过多，为了您的账号安全，请过2h后再登录
            int errorCount = RedisCacheUtil.getErrorCount(jedisCluster, uname, getIP());
            if (errorCount == -1) {
                jsonMap.put("info", "-8");
                writeJson(jsonMap);
                return null;
            }

            String loginVeCode = getRequest().getParameter("loginVeCode");
            Map sessionMap = getSessionMap();
            String loginImg = (String) sessionMap.get("veCode");
            final HttpSession session = super.getSession();
            // 2.验证码失效，请刷新验证码
            if (loginImg == null) {
                jsonMap.put("info", "-5");
                writeJson(jsonMap);
                return null;
            }

            // 3.验证码错误
            if (StringUtils.isBlank(loginVeCode)
                    || !loginImg.toLowerCase().equals(loginVeCode.toLowerCase())) {
                sessionMap.remove("loginImg");
                jsonMap.put("info", "-1");
                writeJson(jsonMap);
                return null;
            }

            // 4.1获取登录加盐器
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setMobile(uname);
            userBaseInfo.setPassword(loginPassWord);
            userBaseInfo = customerRemoteService.queryUserPasswordTwice(userBaseInfo,"login");
            if (userBaseInfo == null) {
                passwordError(jsonMap, uname, errorCount);
                return null;
            }

            // 4.2您输入的手机号和密码不匹配，请重新输入
            User user = new User();
            user.setMobile(uname);
            user.setLoginPassword(userBaseInfo.getPassword());
            User u = loginService.login(user);
            // 用户名或密码错误
            if (u == null) {
                passwordError(jsonMap, uname, errorCount);
                return null;
            }

            // 5.账户名和密码正确
            if (1 == u.getStatus()) {
                MerchantInfo merchant = new MerchantInfo();
                merchant.setLoginId(u.getLoginId());
                merchant = merchantInfoService.selectByLoginIdForLogin(merchant);
                // 5.1该用户未注册供应商账户，或未通过审核
                if (merchant == null) {
                    sessionMap.remove("loginImg");
                    jsonMap.put("info", "-3");
                    writeJson(jsonMap);
                    return null;
                }

                SuppliersInfo supplierInfo = merchantInfoService.selectByMerchantId(merchant.getMerchantId());
                // 5.2商户信息为空，商家说明供应商未注册；
                if (supplierInfo == null) {
                    sessionMap.remove("loginImg");
                    jsonMap.put("info", "-4");
                    writeJson(jsonMap);
                    return null;
                }
                if (SuppliersStatus.NOTCONFIRM.getStatus().equals(supplierInfo.getStatus())) {
                    // 该供应商为待确认状态，禁止登录，请与管理员联系
                    sessionMap.remove("loginImg");
                    jsonMap.put("info", "-7");
                    jsonMap.put("loginId", u.getLoginId().toString());
                    writeJson(jsonMap);
                    return null;
                } else if (!SuppliersStatus.AUDIT.getStatus().equals(supplierInfo.getStatus())) {
                    // 该用户未注册供应商账户，或未通过审核
                    sessionMap.remove("loginImg");
                    jsonMap.put("info", "-4");
                    writeJson(jsonMap);
                    return null;
                }

                // 5.3商户禁止登陆
                if ((supplierInfo.getLoginStatus() != null
                        && supplierInfo.getLoginStatus().shortValue() == 1)
                        || (supplierInfo.getCloseStatus() != null
                        && supplierInfo.getCloseStatus().shortValue() == 1)) {
                    sessionMap.remove("loginImg");
                    jsonMap.put("info", "-6");
                    writeJson(jsonMap);
                    return null;
                }
                // 供应商ID
                Long supplierId = supplierInfo.getSupplierId();
                session.setAttribute(Constants.SESSION_SUPPLIER_ID, supplierId);
                // 康美中药城供应商id

                // 如果供应商类型为4康美中药城，就存入康美中药城供应商id
                if (supplierInfo.getSupplierType().equals(SuppliersType.KMB2B.getStatus())) {
                    session.setAttribute(Constants.SESSION_KMB2BSUPPLIER_ID, supplierId);
                    // 康美中药城类型
                    session.setAttribute(Constants.SESSION_KMB2BSUPPLIER_TYPE, SuppliersType.KMB2B.getStatus());
                } else {
                    session.removeAttribute(Constants.SESSION_KMB2BSUPPLIER_TYPE);
                }
                session.setAttribute(Constants.SESSION_SUPPLIER_COMPANY, merchant.getCorporateName());
                session.setAttribute(Constants.SESSION_SUPPLIER_MOBILE,
                        merchant.getMobile() != null ? merchant.getMobile() : "--");
                session.setAttribute(Constants.SESSION_SUPPLIER_EMIAL,
                        merchant.getContactsEmail() != null ? merchant.getContactsEmail() : "--");

                session.setAttribute(Constants.SESSION_SUPPLIER_HEADIMAGE,
                        merchant.getHeadSculpture() != null ? merchant.getHeadSculpture() : "#");

                boolean haveShop = CollectionUtils.isNotEmpty(supplierInfo.getShopMainList())
                        && supplierInfo.getShopMainList().get(0) != null;
                session.setAttribute(Constants.SESSION_SUPPLIER_SHOPNAME,
                        haveShop ? supplierInfo.getShopMainList().get(0).getShopName() : "--");
                session.setAttribute(Constants.SESSION_SUPPLIER_SHOPTYPE,
                        haveShop ? ShopMainTypeMap.getValue(supplierInfo.getShopMainList().get(0).getShopType())
                                : "--");
                session.setAttribute(Constants.SESSION_SUPPLIER_SHOPURL,
                        haveShop ? supplierInfo.getShopMainList().get(0).getDefaultDomainUrl() : "#");

                session.setAttribute(Constants.SESSION_USER_NAME, u.getLoginAccount());
                session.setAttribute(Constants.SESSION_USER_PWD, u.getLoginPassword().toLowerCase());
                session.setAttribute(Constants.SESSION_USER_ID, u.getLoginId());
                session.setAttribute(Constants.SESSION_MERCHANT_ID, merchant.getMerchantId());

                String sessionId = session.getId();
                Date date = new Date(SUPPER_LOGIN_MEMCACHED_TIME * 1000l);// 有效时间
                memCachedClient.set(memNameKey + sessionId, u.getLoginAccount().toLowerCase(), date);
                memCachedClient.set(memPWDKey + sessionId, u.getLoginPassword(), date);
                session.removeAttribute("loginTime");
                jsonMap.put("loginSessionID", sessionId);
                jsonMap.put("gysLoginUserName", URLEncoder.encode(u.getLoginAccount(), "UTF-8"));
                jsonMap.put("info", "0");
            } else {
                // 该用户已被系统禁用，请联系客服
                jsonMap.put("info", "-2");
                writeJson(jsonMap);
                return null;
            }

            if (StringUtils.isNotBlank(url) && url.startsWith(gysReturnUrl)) {
                jsonMap.put("returnUrl", url);
            } else {
                jsonMap.put("returnUrl", "");
            }
        } catch (Exception e) {
            logger.error("供应商：[{}]登录出错！错误信息{}", new Object[]{uname, e});
            // 登录失败，请刷新页面后登录
            jsonMap.put("info", "-9");
        }
        writeJson(jsonMap);
        return null;
    }

    /**
     * 密码错误
     * @param jsonMap
     * @param uName
     * @param errorCount
     */
    private void passwordError(Map<String, Object> jsonMap, String uName, int errorCount) {
        RedisCacheUtil.incrErrorCount(jedisCluster, uName, getIP());
        jsonMap.put("info", "-10");
        // 剩余登陆次数
        jsonMap.put("errorTimes", (RedisCacheUtil.MAX_NAME_ERROR_COUNT - errorCount - 1));
        writeJson(jsonMap);
    }

    public String loginIndex() throws Exception {
        final HttpSession session = super.getSession();
        HttpServletResponse response = this.getResponse();
        // 1.查询用户信息
        User user = merchantInfoService.queryUserByLoginId(loginId);

        // 2.根据登录id查询商户信息
        MerchantInfo mer = new MerchantInfo();
        mer.setLoginId(loginId);
        mer = merchantInfoService.selectByLoginIdForLogin(mer);

        // 3.根据商户id查询供应商信息
        SuppliersInfo supplier = merchantInfoService.selectByMerchantId(mer.getMerchantId());
        SuppliersInfo newSupp = new SuppliersInfo();
        newSupp.setStatus(SuppliersStatus.AUDIT.getStatus());// 设置供应商的状态为3审核通过
        newSupp.setSupplierId(supplier.getSupplierId());// 设置供应商id

        // 4.修改供应商商的状态
        merchantInfoService.finishUpdate(newSupp);
        // AccountInfo acccount = MerchantInfoService.findByLoginId(loginId);

        // 5.如果供应商类型为4康美中药城，就存入康美中药城供应商id
        if (supplier.getSupplierType().equals(SuppliersType.KMB2B.getStatus())) {
            session.setAttribute(Constants.SESSION_KMB2BSUPPLIER_ID, supplier.getSupplierId());// 康美中药城供应商id
            session.setAttribute(Constants.SESSION_KMB2BSUPPLIER_TYPE, SuppliersType.KMB2B.getStatus());//类型
        }

        // 6.插入session
        session.setAttribute(Constants.SESSION_SUPPLIER_ID, supplier.getSupplierId());// 商户ID
        session.setAttribute(Constants.SESSION_SUPPLIER_COMPANY, mer.getCorporateName());// 公司名称
        session.setAttribute(Constants.SESSION_SUPPLIER_MOBILE,
                mer.getMobile() != null ? mer.getMobile() : "--");// 手机
        session.setAttribute(Constants.SESSION_SUPPLIER_EMIAL,
                mer.getContactsEmail() != null ? mer.getContactsEmail() : "--");// email
        session.setAttribute(Constants.SESSION_SUPPLIER_HEADIMAGE,
                mer.getHeadSculpture() != null ? mer.getHeadSculpture() : "#");
        List<ShopMain> shopMainList = supplier.getShopMainList();
        boolean haveShop = CollectionUtils.isNotEmpty(shopMainList);
        ShopMain shopMain = null;
        if (haveShop) {
            shopMain = shopMainList.get(0);
            haveShop = shopMain != null;
        }
        // 店铺类型
        session.setAttribute(Constants.SESSION_SUPPLIER_SHOPTYPE,
                haveShop && shopMain.getShopType() != null  ? ShopMainTypeMap.getValue(shopMain.getShopType()) : "--");
        // 店铺名称
        session.setAttribute(Constants.SESSION_SUPPLIER_SHOPNAME,
                haveShop && shopMain.getShopName() != null ? shopMain.getShopName() : "--");
        // 店铺地址
        session.setAttribute(Constants.SESSION_SUPPLIER_SHOPURL,
                haveShop && shopMain.getDefaultDomainUrl() != null ? shopMain.getDefaultDomainUrl() : "#");
        // 用户名
        session.setAttribute(Constants.SESSION_USER_NAME, user.getLoginAccount());
        session.setAttribute(Constants.SESSION_USER_PWD, user.getLoginPassword().toLowerCase());
        // 用户登录id
        session.setAttribute(Constants.SESSION_USER_ID, user.getLoginId());

        // 7.插入MemCached缓存
        String sessionId = session.getId();
        Date date = new Date(SUPPER_LOGIN_MEMCACHED_TIME * 1000l);// 有效时间
        memCachedClient.set(memNameKey + sessionId, user.getLoginAccount().toLowerCase(), date);
        memCachedClient.set(memPWDKey + sessionId, user.getLoginPassword(), date);
        session.removeAttribute("loginTime");
        info = "0";

        try {
            // 8.写入cookie
            Cookie c = new Cookie("loginSessionID", sessionId);
            Cookie d = new Cookie("gysUserName", URLEncoder.encode(user.getLoginAccount().toLowerCase(), "UTF-8"));
            c.setPath("/");
            d.setPath("/");
            c.setMaxAge(SUPPER_LOGIN_MEMCACHED_TIME);
            d.setMaxAge(SUPPER_LOGIN_MEMCACHED_TIME);
            response.addCookie(c);
            response.addCookie(d);
        } catch (Exception e) {
            logger.error("goIndex写入cookie失败:", e);
        }
        return loginSuccess();
    }

    public String loginSuccess() {
        if (getSupplyId() == null) {
            return "reLogin";
        }

        try {
            // 1为首页下排按钮查询提示数
            CountInfo countInfo = loginService.queryTotalCountForIndex(getSupplyId());
            getRequest().setAttribute("countInfo", countInfo);
        } catch (Exception e) {
            logger.error("loginSuccess:error:{}", e);
            return ERROR;
        }

        return SUCCESS;
    }

    public String gotoFareMenu() {
        return SUCCESS;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPassWord() {
        return loginPassWord;
    }

    public void setLoginPassWord(String loginPassWord) {
        this.loginPassWord = loginPassWord;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCheckboxVa() {
        return checkboxVa;
    }

    public void setCheckboxVa(String checkboxVa) {
        this.checkboxVa = checkboxVa;
    }

    public String getJsoncallback() {
        return jsoncallback;
    }

    public void setJsoncallback(String jsoncallback) {
        this.jsoncallback = jsoncallback;
    }

    public long getLoginId() {
        return loginId;
    }

    public void setLoginId(long loginId) {
        this.loginId = loginId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
