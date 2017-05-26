package com.kmzyc.b2b.action;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.CouponLoginInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.CouponService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("serial")
@Controller("couponAction")
@Scope(value = "prototype")
public class CouponAction extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(CouponAction.class);

    @Resource(name = "couponService")
    private CouponService couponService;

    private CouponLoginInfo loginInfo;
    @Resource(name = "jedisCluster")
 private JedisCluster jedisCluster;
    // 返回至页面的对象
    private ReturnResult<Object> returnResult;
    private String mobile;
    private String code;

    /**
     * 发送优惠券
     */
    public void sendCoupons() {

        try {

            couponService.sendCoupons(loginInfo);
        } catch (Exception e) {
            logger.error("优惠券发送异常" + e.getMessage(), e);

        }

    }

    /**
     * 
     * 发放优惠券，用于活动页面
     *
     * @author Administrator
     * @return
     */
    public String grantCouponForActivity() {
        try {
            Long usrId = this.getUserloginId();
            String rst = couponService.grantCouponForActivity(usrId);
            if (StringUtil.isEmpty(rst)) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "领取成功！", null);
                logger.info("活动页面发放优惠券成功{}", usrId);
            } else {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, rst, null);
                logger.info("活动页面发放优惠券失败,{},{}", usrId, rst);
            }

        } catch (Exception e) {
            returnResult =
                    new ReturnResult<Object>(InterfaceResultCode.FAILED, "领取异常！" + e.getMessage(),
                            null);
            logger.error("活动页面发放优惠券失败", e);
        }
        return SUCCESS;
    }

    /**
     * 
     * 获取用户信息
     *
     * @author Administrator
     * @return
     */
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }



}
