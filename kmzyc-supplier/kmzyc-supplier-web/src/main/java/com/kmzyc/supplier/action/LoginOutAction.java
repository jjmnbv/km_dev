package com.kmzyc.supplier.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@Scope("prototype")
@Controller("loginOutAction")
public class LoginOutAction extends SupplierBaseAction {

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    // 登录名key前缀
    private static final String memNameKey = ConfigurationUtil.getString("supplier_login_name_memcached_key_prex");

    // 登录密码key前缀
    private static final String memPWDKey = ConfigurationUtil.getString("supplier_login_pwd_memcached_key_prex");

    public String loginout() throws Exception {
        HttpSession session = getSession();
        session.removeAttribute(Constants.SESSION_USER_NAME);
        session.removeAttribute(Constants.SESSION_USER_ID);

        getResponse().setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        String loginSessionID = null;
        if (StringUtils.isNotEmpty(loginSessionID)) {
            memCachedClient.delete(memNameKey + loginSessionID);
            memCachedClient.delete(memPWDKey + loginSessionID);
        }
        getSessionMap().remove("loginImg");
        return SUCCESS;
    }

}
