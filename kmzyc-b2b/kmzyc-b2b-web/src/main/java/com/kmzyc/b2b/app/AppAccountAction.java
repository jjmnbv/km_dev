package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Favorite;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.BrowsingHisService;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.MemberSiteNoticeService;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.service.member.MyCouponService;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.service.ThirdBindInfoService;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.SecurityCentreInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@Scope("prototype")
@Controller("appAccountAction")
public class AppAccountAction extends AppBaseAction {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 2113192271803442540L;

    // private static Logger logger = Logger.getLogger(AppAccountAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppAccountAction.class);

    // public final static String CHANNEL = ConfigurationUtil.getString("CHANNEL");
    @Resource(name = "accountServiceImpl")
    private AccountService accountService;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource(name = "myCouponServiceImpl")
    private MyCouponService myCouponService;

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Resource(name = "memberSiteNoticeService")
    private MemberSiteNoticeService memberSiteNoticeService;

    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;

    @Resource(name = "resetPwdServiceImpl")
    private ResetPwdService resetPwdService;

    @Resource(name = "browsingHisServiceImpl")
    private BrowsingHisService browsingHisService;

    @Resource(name = "myFavoriteServiceImpl")
    private MyFavoriteService myFavoriteService;

    @Resource(name = "thirdBindInfoService")
    private ThirdBindInfoService thirdBindInfoService;

    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;

    private AccountInfo accountInfo;
    private Long uid;
    private String token;
    private String message = "请求参数错误";
    private String code = InterfaceResultCode.FAILED;
    private ReturnResult<Map<String, Object>> returnResult;

    // 登录用户IDkey前缀
    // private String UID_PREX_KEY =
    // ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex");

    private User user;

    /**
     * 查询会员的账户信息
     * 
     * @return
     */
    public void queryAccountInfo() {
        uid = Long.parseLong(this.getUserid());
        String loginType = getRequest().getAttribute(APP_LOGIN_USER_TYPE).toString();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 获取用户登陆信息
            User loginInfo = resetPwdService.getUserByLoginId(uid);

            PersonalityInfo personality = userInfoService.queryPersonalityByUserId(uid);
            // 获取账户状态
            if (StringUtils.isNotBlank(loginType) && Constants.KM_USER_TYPE.equals(loginType)) {// 康美会员
                List<ThirdBindInfo> thirdBindInfoList =
                        thirdBindInfoService.queryBindInfo(uid.toString());// 是否是第三方账户
                if (thirdBindInfoList != null && thirdBindInfoList.size() > 0) {
                    ThirdBindInfo thirdBindInfo = thirdBindInfoList.get(0);// 获取第三方信息
                    String bindType = thirdBindInfo.getBindType();
                    if ("01".equals(bindType)) {// 信息已完善
                        map.put("infoPerfect", "Y");
                    } else if ("02".equals(bindType)) {// 信息未完善
                        map.put("infoPerfect", "N");
                    } else {// 数据字典插入值异常
                        logger.error("该账户完善信息异常，登陆ID:" + uid);
                    }
                } else {// 正式会员
                    map.put("infoPerfect", "Y");// 默认已完善
                }
                // 积分
                Long availableIntegral = personality.getAvailableIntegral();
                map.put("availableIntegral", availableIntegral);

                map.put("userLevelName", "普通会员");

                // if (StringUtil.isEmpty(loginInfo.getLevelId())) {
                // map.put("userLevelName", "普通会员");
                // } else {
                // UserLevel userLevel =
                // userLevelService.findByLevelId(Long.valueOf(loginInfo.getLevelId()));
                // map.put("userLevelName", userLevel.getLevelName());
                // }
            } else if (StringUtils.isNotBlank(loginType) && Constants.LOGINTYPE.equals(loginType)) {// 时代会员
                EraInfo eraInfo = eraInfoService.queryEraInfoByLoginId(uid);
                if (null == eraInfo || StringUtils.isBlank(eraInfo.getLoginAccount())) {
                    map.put("infoPerfect", "N");
                } else {
                    map.put("infoPerfect", "Y");
                }
                // 时代会员积分显示pv值
                map.put("availableIntegral", myOrderService.sumPvByCustomerId(uid));
                map.put("userLevelName", eraInfo.getEraGradeName());
            } else {
                logger.error("会员登陆类型异常---登陆类型" + loginType + "---登陆ID：" + uid);
            }

            // 获取账户信息
            accountInfo = accountService.findAccountByUserId(uid);
            // 获取用户名
            map.put("accountName", accountInfo.getAccountLogin());
            // 获取账户支付密码
            // 20160922修改，若支付密码不为空，则传“Y”，否则为空
            if (null != accountInfo.getPaymentpwd() && !"".equals(accountInfo.getPaymentpwd())) {
                map.put("amountPaymentpwd", "Y");
            } else {
                map.put("amountPaymentpwd", "");
            }

            // 获取账户支付范围
            map.put("amountPayRange", accountInfo.getPayRange());
            map.put("amountAvailable", accountInfo.getAmountAvlibal());
            // 获取优惠劵个数
            long couponAmount =
                    myCouponService.countCouponByUserId(uid, Constants.NOTUSE_COUPONSTATUS);
            map.put("couponAmount", couponAmount);
            // 获取可用积分与头像url

            map.put("nickName", personality.getNickname());
            if (personality.getHeadSculpture() != null
                    && StringUtils.isNotBlank(personality.getHeadSculpture())
                    && !"no_img_mid.jpg".equals(personality.getHeadSculpture())) {
                map.put("userImgUrl", ConfigurationUtil.getString("USER_IMG_PATH")
                        + personality.getHeadSculpture() + "?i=" + new Date().getTime());
            } else { // 默认头像
                map.put("userImgUrl", ConfigurationUtil.getString("CSS_JS_PATH")
                        + "images/default__man_err100_100.jpg?i=" + new Date().getTime());
            }
            // 获取用户手机号
            map.put("userMobile", loginInfo.getMobile());
            // 获取站内未读通知数
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("userId", uid);
            para.put("status", Constants.MESSAGE_NOT_READ);
            para.put("platform", Constants.message_platform_b2b);
            Long newNoticeAmount = memberSiteNoticeService.countNewNotice(para);
            map.put("newNoticeAmount", newNoticeAmount);
            // 获取安全认证信息
            user = securityCentreService.getUserByLoginId(uid);
            SecurityCentreInfo securityCentreInfo =
                    securityCentreService.querySecurityCentreInfo(user);
            map.put("isMobileValidate", securityCentreInfo.isMobileValidate());
            // add by hl 9.18 收藏商品数量
            List<Favorite> myFavoriteListAll = myFavoriteService.findAllFavoriteGoods(uid);
            map.put("favoriteNum", null == myFavoriteListAll ? 0 : myFavoriteListAll.size());
            // 收藏店铺数量
            Pagination page = this.getPagination(5, 10);
            Map<String, Object> seleceC = new HashMap<String, Object>();
            seleceC.put("userId", uid.intValue());
            page.setObjCondition(seleceC);
            page = myFavoriteService.findFavoriteShopByPage(page);
            map.put("favoriteShopNum", page.getTotalRecords());
            // 浏览记录数量
            Map<String, Object> newConditon = new HashMap<String, Object>();
            newConditon.put("loginId", uid);
            newConditon.put("channel", ConfigurationUtil.getString("CHANNEL"));
            Pagination page1 = this.getPagination(5, 10);
            page1.setStartindex(1);
            page1.setEndindex(10);
            page1.setObjCondition(newConditon);
            Pagination pgination = browsingHisService.queryBrowsingHis(page1);
            map.put("viewNum", pgination.getTotalRecords());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderChannel", ConfigurationUtil.getString("CHANNEL"));
            params.put("loginId", user.getLoginId());

            int enablePayPWD = myOrderService.enablePayPWD(uid);
            if (enablePayPWD == -2 || 0 == enablePayPWD) {
                map.put("enablePayPWDTip", "必须完成过一笔在线支付支付金额5元及以上交易订单才可以启用支付密码");
            }
            Map<String, BigDecimal> result = myOrderService.queryMyOrderStatusCount(params);
            // 获取待支付订单总数
            map.put("notPayOrderAmount", (null == result || null == result.get("NOTPAYORDERAMOUNT"))
                    ? 0 : result.get("NOTPAYORDERAMOUNT"));
            // 待收货数
            map.put("waitToAccpetNum", (null == result || null == result.get("DONEORDERAMOUNT")) ? 0
                    : result.get("DONEORDERAMOUNT"));
            // 已完成(待评价)
            map.put("notAssessOrderAmount",
                    (null == result || null == result.get("NOTASSESSORDERAMOUNT")) ? 0
                            : result.get("NOTASSESSORDERAMOUNT"));
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功", map);
        } catch (Exception e) {
            logger.error("查询常见问题出错：" + e.getMessage(), e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
        }
        printJsonString(returnResult);
    }

    /**
     * 校验令牌 成功:true，失败：false
     * 
     * @param loginId
     * @param pToken
     * @return
     */
    public boolean validToken(long loginId, String pToken) {
        boolean result = false;
        if (!StringUtil.isEmpty(pToken) && 0L != loginId) {
            String mToken = (String) memCachedClient
                    .get(ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex") + loginId);
            result = pToken.equalsIgnoreCase(mToken);
        }
        return result;
    }


    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
