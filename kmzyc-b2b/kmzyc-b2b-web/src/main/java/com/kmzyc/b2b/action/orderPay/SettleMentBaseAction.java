package com.kmzyc.b2b.action.orderPay;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.PromotionInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.shopcart.vo.SettlementInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings("unchecked")
public class SettleMentBaseAction extends BaseAction {
    private static final long serialVersionUID = 32398571147875964L;
    private static Logger log = LoggerFactory.getLogger(SettleMentBaseAction.class);

    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    @Resource(name = "addressServiceImpl")
    private AddressService addressService;

    @Resource
    protected PromotionInfoService promotionInfoService;


    public Long getUserloginId() {

        Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
        Long loginId = obj == null ? null : Long.parseLong(obj.toString());
        User user = null;
        if (null == loginId) {
            user = (User) this.getSession().getAttribute(Constants.UNLOGIN_SESSION_USER_KEY);
            if (user == null) {
                return null;
            } else {
                return user.getLoginId();
            }
        }
        return loginId;
    }

    public boolean isLogin() {
        Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
        return obj != null;
    }

    public User getLoginUser() {
        Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
        if (null == obj) {
            return null;
        } else {
            try {
                return this.loginService.queryUserByLoginId(obj.toString());
            } catch (ServiceException e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
    }

    public void showpage(String result) {
        this.getResponse().setCharacterEncoding("GB2312");
        PrintWriter out = null;
        try {
            out = this.getResponse().getWriter();
            out.write(result);
        } catch (IOException e) {
            log.error("response 向页面写入信息错误", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public boolean isNumberOfAddressOk(Long loginId) throws SQLException {
        List<Address> addressList = addressService.findByLoginId(loginId.intValue());
        if (addressList == null || addressList.size() < 10) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否登录为Ajax页面弹出登录页面做准备
     * 
     * @return
     */
    public Boolean compareLoginFlag() {
        String isLoginFlag = this.getRequest().getParameter("islogin");
        boolean paramislogin = Boolean.parseBoolean(isLoginFlag);
        boolean islogin = this.isLogin();
        if (islogin == paramislogin) {
            return true;
        } else {
            return false;
        }
    }

    public ReturnResult getUnloginReturnResult() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("loginFlag", 0);
        ReturnResult result = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", map);
        return result;
    }

    @Autowired
    com.whalin.MemCached.MemCachedClient memCachedClient;

    public static Date settementInfoCahceTime = new Date(1000 * 60 * 60);// 1个小时

    protected boolean saveSettlementInfo(SettlementInfo info) {
        Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
        String key = ConfigurationUtil.getString("b2b_sett_info_prex") + obj.toString();
        return memCachedClient.set(key, info, settementInfoCahceTime);//
    }

    protected SettlementInfo getSettlementInfoCache() {
        // uid
        Object obj = this.getSession().getAttribute(Constants.SESSION_USER_ID);
        String key = ConfigurationUtil.getString("b2b_sett_info_prex") + obj.toString();
        return (SettlementInfo) memCachedClient.get(key);
    }
}
