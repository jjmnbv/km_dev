package com.kmzyc.b2b.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.vo.ChangePayPasswordInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.MobileCodeInfRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.MobileCodeInf;
import com.whalin.MemCached.MemCachedClient;

// import com.km.framework.common.util.RemoteTool;

@SuppressWarnings("serial")
@Scope("prototype")
@Controller("appSecurityCentreAction")
public class AppSecurityCentreAction extends AppBaseAction {
    // 发送短信key前缀
    // private String SEND_MESSAGE_KEY = ConfigurationUtil
    // .getString("b2b_send_message_memcached_key_prex");
    // 短信校验有效时间
    // private String MESSAGE_VALID_TIME = ConfigurationUtil.getString("b2b_common_msg_valid_time");
    // 发送短信时间key前缀
    // private String SEND_MESSAGE_DATE_KEY = ConfigurationUtil
    // .getString("b2b_send_message_date_memcached_key_prex");
    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    // private static final Logger logger = Logger.getLogger(AppSecurityCentreAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppSecurityCentreAction.class);

    @Resource(name = "messageRemoteService")
    private MessageRemoteService messageRemoteService;
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "accountInfoServiceImp")
    private AccountInfoService accountInfoService;
    private ChangePayPasswordInfo changePayPasswordInfo;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource
    private MobileCodeInfRemoteService mobileCodeInfRemoteService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    private ReturnResult<Map<String, Object>> returnResult;


    /**
     * 手机验证-登录密码验证APP
     * 
     * @return
     */
    public void verifyUserPassword() {
        String uId = getUserid();
        if (StringUtils.isNotBlank(uId)) {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            if (null != jsonParams && !jsonParams.isEmpty()) {
                String loginPassWord = jsonParams.getString("loginPassWord");
                try {
                    boolean rs = securityCentreService.appVerifyUserPassword(loginPassWord,
                            Long.parseLong(uId));
                    if (rs) {
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.SUCCESS, "密码验证通过", null));
                    } else {
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, "密码不正确", null));
                    }
                } catch (Exception e) {
                    logger.error("账号:" + uId + ",验证密码异常" + e.getMessage(), e);
                    setReturnResult(new ReturnResult<Map<String, Object>>(
                            InterfaceResultCode.FAILED, "密码验证失败", null));
                }
            } else {
                setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                        "传入参数异常!", null));
            }
        } else {
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "未登录!", null));
        }
        printJsonString(getReturnResult());
    }



    /**
     * 获取支付范围
     */
    public void queryPayRange() {
        String uId = getUserid();
        if (StringUtils.isNotBlank(uId)) {
            Map<String, Object> map = new HashMap<String, Object>();
            try {
                AccountInfo accountInfo = accountInfoService.findByLoginId(Long.parseLong(uId));
                map.put("payRange", accountInfo.getPayRange());
                map.put("remark", "1--使用余额2---使用优惠券3--使用礼品卡4--使用积分；5--使用预备金");
                if (StringUtils.isBlank(accountInfo.getPaymentpwd())) {
                    map.put("PaymentpwdStatus", 0);
                } else {
                    map.put("PaymentpwdStatus", 1);
                }
                setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                        "获取支付范围成功", map));
            } catch (Exception e) {
                logger.error("账号:" + uId + ",获取支付范围异常" + e.getMessage(), e);
                setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                        "获取支付范围异常", null));
            }
        } else {
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "未登录!", null));
        }
        printJsonString(returnResult);
    }

    /**
     * 支付管理APP
     * 
     * @return
     */
    public void postPaySetting() {
        String uId = getUserid();
        if (StringUtils.isNotBlank(uId)) {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            if (null != jsonParams && !jsonParams.isEmpty()) {
                String payRange = jsonParams.getString("payRange");
                try {
                    User user = securityCentreService.getUserByLoginId(Long.parseLong(uId));
                    if (StringUtil.isEmpty(payRange)) {
                        payRange = "1";
                    } else {
                        payRange = "1," + payRange.trim();
                    }
                    boolean rs = securityCentreService.saveAccountInfoPayRange(payRange, user);
                    if (rs) {
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.SUCCESS, "支付范围设置成功", null));
                    } else {
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.SUCCESS, "支付范围设置失败", null));
                    }
                } catch (Exception e) {
                    logger.error("appSecurityCentreAction的postPaySetting()方法出现异常：" + e.getMessage(),
                            e);
                    setReturnResult(new ReturnResult<Map<String, Object>>(
                            InterfaceResultCode.SUCCESS, "支付范围设置失败", null));
                }
            } else {
                setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                        "传入参数异常!", null));
            }
        } else {
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "未登录!", null));
        }
        printJsonString(returnResult);
    }

    /**
     * 设置修改支付密码APP
     * 
     * @return
     */
    public void postModifyPayPassword() {
        try {
            String uId = getUserid();
            if (StringUtils.isNotBlank(uId)) {
                JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());

                UserBaseInfo userBaseInfo = new UserBaseInfo();
                userBaseInfo.setLoginId(Long.valueOf(uId));
                userBaseInfo.setPassword(jsonParams.getString("newPayPassWord"));
                userBaseInfo =
                        this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "pay");
                // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);

                if (null != jsonParams && !jsonParams.isEmpty() && userBaseInfo != null) {

                    // String newPayPassWord =
                    // StringUtil.passwordTwiceEncryption(jsonParams.getString("newPayPassWord"),saltInfo.getPaySalt());
                    String mobile = jsonParams.getString("mobile");
                    changePayPasswordInfo = new ChangePayPasswordInfo();
                    changePayPasswordInfo.setPayPassword(userBaseInfo.getPassword());
                    Map<String, String> secMap =
                            securityCentreService.queryUserSecurityInfo(Long.parseLong(uId));
                    String paymentpwd = secMap.get("PAYMENTPWD");
                    String loginPassword = secMap.get("LOGINPASSWORD");
                    String loginAccount = secMap.get("LOGINACCOUNT");
                    if (1 != myOrderService.enablePayPWD(Long.parseLong(uId))
                            && (null == paymentpwd || paymentpwd.length() == 0)) {
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, "设置支付密码需验证手机，且有5元订单。", null));
                    } else if (userBaseInfo.getPassword().equalsIgnoreCase(paymentpwd)) {// 新旧支付密码一样
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, "不能和老密码一致", null));
                    } else if (userBaseInfo.getPassword().equalsIgnoreCase(loginPassword)) {// 和登陆密码一致
                        setReturnResult(new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, "不能和登录密码一致", null));
                    } else {
                        // 提交修改支付密码
                        User user = new User();
                        user.setLoginId(Long.parseLong(uId));
                        boolean rs = securityCentreService
                                .appModifyPayPassword(changePayPasswordInfo, user);
                        if (rs) {
                            /*
                             * // 如果是首次验证，送50积分 if (!isChange) { try {
                             * userGrowingService.updateUserScoreInfo("RU0004", 1,
                             * user.getLoginAccount(), new HashMap<String, String>()); } catch
                             * (Exception e) { logger.error("", e); } }
                             */
                            setReturnResult(new ReturnResult<Map<String, Object>>(
                                    InterfaceResultCode.SUCCESS, "支付密码设置成功", null));
                            // 修改支付密码业务完后 清除验证码缓存
                            String status =
                                    MessageTypeEnum
                                            .getMessageTypeEnumByIndex(
                                                    EmailSendType.MSG_VERIFY_CODE.getStatus())
                                            .getStatus();
                            String valKey = ConfigurationUtil.getString(
                                    "b2b_send_message_memcached_key_prex") + mobile + '_' + status;
                            String dateKey = ConfigurationUtil
                                    .getString("b2b_send_message_date_memcached_key_prex") + mobile
                                    + '_' + status;
                            memCachedClient.delete(valKey);
                            memCachedClient.delete(dateKey);
                        } else {
                            logger.error("账号:" + loginAccount + "支付密码设置失败");
                            setReturnResult(new ReturnResult<Map<String, Object>>(
                                    InterfaceResultCode.FAILED, "支付密码设置失败", null));
                        }
                    }
                } else {
                    setReturnResult(new ReturnResult<Map<String, Object>>(
                            InterfaceResultCode.FAILED, "传入参数异常!", null));
                }
            } else {
                setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                        "未登录!", null));
            }
        } catch (Exception e) {
            logger.error("AppSecurityCentreAction的postModifyPayPassword()方法出现异常：" + e.getMessage(),
                    e);
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "支付密码设置异常", null));
        }
        printJsonString(returnResult);
    }

    /**
     * 验证手机校验码
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private boolean validMobileCode(int mobileType) {
        boolean result = false;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        String mobile = jsonParams.getString("mobile");
        String msgCode = jsonParams.getString("msgCode");
        if (StringUtil.equalLen(mobile, 11)) {
            String status = MessageTypeEnum.getMessageTypeEnumByIndex(mobileType).getStatus();
            String valKey = ConfigurationUtil.getString("b2b_send_message_memcached_key_prex")
                    + mobile + '_' + status;
            String dateKey = ConfigurationUtil.getString("b2b_send_message_date_memcached_key_prex")
                    + mobile + '_' + status;
            Date date = (Date) memCachedClient.get(dateKey);
            String scode = (String) memCachedClient.get(valKey);// 验证码
            if (null != msgCode && msgCode.equals(scode)
                    && (new Date().getTime() - date.getTime()) < (Integer
                            .parseInt(ConfigurationUtil.getString("b2b_common_msg_valid_time")) * 60
                            * 1000)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 验证手机校验码
     * 
     * @return
     * @throws Exception
     */
    // public String validMobileCode() {
    // JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    // if (null != jsonParams && !jsonParams.isEmpty()) {
    // String mobile = jsonParams.getString("mobile");
    // String msgCode = jsonParams.getString("msgCode");
    // int mobileType = jsonParams.getIntValue("mobileType");
    // if (StringUtil.equalLen(mobile, 11)) {
    // String status = MessageTypeEnum.getMessageTypeEnumByIndex(mobileType).getStatus();
    // String valKey = SEND_MESSAGE_KEY + mobile + '_' + status;
    // String dateKey = SEND_MESSAGE_DATE_KEY + mobile + '_' + status;
    // Date date = (Date) memCachedClient.get(dateKey);
    // String scode = (String) memCachedClient.get(valKey);// 验证码
    //
    // if (null != msgCode
    // && msgCode.equals(scode)
    // && (new Date().getTime() - date.getTime()) < (Integer.parseInt(MESSAGE_VALID_TIME) * 60 *
    // 1000)) {
    // logger.info("手机验证码验证通过，memCachedClient中验证码为：" + scode + ",接收验证码为：" + msgCode);
    // memCachedClient.delete(valKey);
    // memCachedClient.delete(dateKey);
    // returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "短信验证成功", null);
    // return SUCCESS;
    // }
    // }
    // }
    // returnResult = new ReturnResult(InterfaceResultCode.FAILED, "短信验证失败", null);
    // return SUCCESS;
    // }

    /**
     * 通用发送短信验证码
     * 
     * @return
     * @throws Exception
     */
    public String sendMobile() {
        String message = "手机号码错误！";
        String code = InterfaceResultCode.FAILED;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String mobile = jsonParams.getString("mobile");
            int mobileType = jsonParams.getIntValue("mobileType");
            try {
                if (StringUtil.equalLen(mobile, 11)) {
                    HttpSession session = this.getSession();
                    String status =
                            MessageTypeEnum.getMessageTypeEnumByIndex(mobileType).getStatus();
                    String valKey =
                            ConfigurationUtil.getString("b2b_send_message_memcached_key_prex")
                                    + mobile + '_' + status;
                    boolean result = false;
                    result = messageRemoteService.sendMobileVerifyCode(mobile, session, status);
                    if (result) {
                        String mCode = (String) memCachedClient.get(valKey);
                        // MobileCodeInfRemoteService mcservice =
                        // (MobileCodeInfRemoteService) RemoteTool.getRemote(
                        // Constants.REMOTE_SERVICE_CUSTOMER, "mobileCodeInfRemoteService");
                        MobileCodeInf mci = new MobileCodeInf();
                        mci.setN_FailureTimeValue(Integer.parseInt(
                                ConfigurationUtil.getString("b2b_common_msg_valid_time")));
                        mci.setIs_Status(0);
                        mci.setMobile(mobile);
                        mci.setTattedCode(mCode);
                        // 设置类型
                        mobileCodeInfRemoteService.addMobileCodeInf(mci, 3);
                        code = InterfaceResultCode.SUCCESS;
                        message = "短信发送成功！";
                    } else {
                        message = "短信发送失败！";
                    }
                }
            } catch (Exception e) {
                logger.error("发送短信存在异常：" + e.getMessage(), e);
                message = "短信发送失败！";
            }
        } else {
            message = "传入参数异常!";
            setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "传入参数异常!", null));
        }
        returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        return SUCCESS;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }


}
