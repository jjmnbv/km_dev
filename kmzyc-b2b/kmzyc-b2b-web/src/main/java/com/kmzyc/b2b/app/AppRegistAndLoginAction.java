package com.kmzyc.b2b.app;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.framework.util.Base64Util;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.MobileCodeInfRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.MobileCodeInf;
import com.whalin.MemCached.MemCachedClient;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings({"rawtypes", "unchecked"})
@Scope("prototype")
@Controller("registAndLoginAction")
public class AppRegistAndLoginAction extends AppBaseAction {
    private static final int MAX_NAME_ERROR_COUNT = 5;// 用户名最大错误次数
    private static final int MAX_IP_ERROR_COUNT = 5;// IP最大错误次数
    private static final String VALID_COUNT = "valid.count.";
    // static Logger logger = Logger.getLogger(AppRegistAndLoginAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppRegistAndLoginAction.class);
    private static final long serialVersionUID = 1L;
    @Resource(name = "registServiceImp")
    private RegistService registservice;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource(name = "messageRemoteService")
    private MessageRemoteService messageRemoteService;
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "resetPwdServiceImpl")
    private ResetPwdService resetPwdService;

    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;

    private static final String APP_LOGIN_NAME_QUERY_COUNT_KEY = "app_login_name_query_count_";//
    private static final int APP_MAX_LOGIN_NAME_QUERY = 10;// 每个设备号允许校验用户名存在次数
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Resource
    private MobileCodeInfRemoteService mobileCodeInfRemoteService;

    private ReturnResult<HashMap<String, Object>> returnResult;

    @Resource
    private CustomerRemoteService customerRemoteService;

    /**
     * 生成token接口
     */
    public void genToken() {
        String rs = InterfaceResultCode.FAILED;
        String token = null;
        try {
            HttpServletRequest request = getRequest();
            String deviceInfo = request.getHeader(B2B_KM_APP_INFO_KEY);
            String kmAppId = null;
            if (null != deviceInfo) {
                JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
                kmAppId = json.containsKey(KM_APP_ID_KEY) ? json.getString(KM_APP_ID_KEY) : null;
            }
            if (null != kmAppId) {
                token = generationToken(kmAppId, false);
                rs = InterfaceResultCode.SUCCESS;
            }
        } catch (Exception e) {
            logger.error("生成token失败", e);
        }
        returnResult =
                new ReturnResult(rs, null, JSONObject.parse("{\"token\":\"" + token + "\"}"));
        printJsonString(returnResult);
    }

    /**
     * 生成token
     */
    private String generationToken(String kmAppId, boolean login) {
        Calendar cal = Calendar.getInstance();
        String token = MD5.getMD5Str(
                ConfigurationUtil.getString("app_secretkey") + kmAppId + cal.getTimeInMillis());
        String APP_TOKEN_PREX = ConfigurationUtil.getString("b2b_app_token_prex");
        memCachedClient.set(APP_TOKEN_PREX.concat(token), cal.getTimeInMillis(), new Date(1200000));
        if (login) {
            memCachedClient.set(APP_TOKEN_PREX.concat(token).concat(APP_LOGIN_FLAG), kmAppId,
                    new Date(1200000));
        }
        return token;
    }

    /**
     * 退出
     */
    public void mobileExit() throws Exception {
        try {
            String token = getRequest().getHeader("token");
            String APP_TOKEN_PREX = ConfigurationUtil.getString("b2b_app_token_prex");
            memCachedClient.delete(APP_TOKEN_PREX.concat(token).concat(APP_LOGIN_USER_TYPE));
            memCachedClient.delete(APP_TOKEN_PREX.concat(token));
            memCachedClient.delete(APP_TOKEN_PREX.concat(token).concat(APP_LOGIN_FLAG));
        } catch (Exception e) {
            logger.error("", e);
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "退出成功", null);
        printJsonString(returnResult);
    }

    /**
     * 登录
     */
    public void mobileLogin() {
        String message = "用户名和密码不匹配！";
        String code = InterfaceResultCode.FAILED;
        HashMap<String, Object> resultObject = new HashMap<>();
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        try {
            if (null != jsonParams && !jsonParams.isEmpty()) {
                String appId;

                HttpServletRequest request = getRequest();
                String deviceInfo = request.getHeader(B2B_KM_APP_INFO_KEY);
                JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
                appId = (null != json && json.containsKey(KM_APP_ID_KEY))
                        ? json.getString(KM_APP_ID_KEY) : "";

                String loginName = java.net.URLDecoder
                        .decode(jsonParams.getString("uname").toLowerCase(), "UTF-8").toLowerCase();
                String passwordcode = jsonParams.getString("password").toLowerCase();

                // 校验 s
                String imgCode = jsonParams.getString("imgCode"); // 图形验证码可能有
                if (!StringUtil.isEmpty(imgCode)) {
                    String type = jsonParams.getString("type");
                    String key = ConfigurationUtil.getString("b2b_km_valid_image_prex")
                            .concat(appId).concat(null == type ? "" : type);

                    Object imgCodeObj = memCachedClient.get(key);
                    if (imgCodeObj == null) {
                        imgCodeObj = "";
                    }

                    if (!imgCode.equalsIgnoreCase(String.valueOf(imgCodeObj))) {
                        message = "验证码错误！";
                        return;
                    } else if (imgCode.equalsIgnoreCase(String.valueOf(imgCodeObj))) {
                        memCachedClient.delete(key);
                    }
                }

                if (!StringUtil.withinRangeByte(loginName, 1, 50)
                        || !StringUtil.equalLen(passwordcode, 32)) {
                    message = "用户名或密码格式错误！";
                    return;
                } else if (0 == getErrorCount(loginName, appId)) {
                    message = "登录频繁，请稍候再试！";
                    return;
                }
                // 校验 e


                // 查询用户信息 s
                String userType = Constants.KM_USER_TYPE;
                User user;
                if (StringUtil.isTimesUName(loginName)) {
                    user = loginService.timeLogin(loginName, passwordcode,
                            Constants.REGISTER_DEVICE_APP);
                    userType = Constants.LOGINTYPE;
                } else {
                    user = new User();
                    if (StringUtil.isMobile(loginName)) {
                        user.setMobile(loginName);
                    } else if (loginName.indexOf("@") > 0) {
                        user.setEmail(loginName);
                    } else {
                        user.setLoginAccount(loginName);
                    }
                    user.setLoginPassword(passwordcode);
                    user = loginService.userLogin(user);
                    // 判断是否是已经绑定康美账号的时代会员
                    if (user != null && user.getEarInfo() != null
                            && user.getEarInfo().getEraNo() != null) {
                        userType = Constants.LOGINTYPE;
                    }
                }
                // 查询用户信息 e

                if (null != user && 1 == user.getStatus()) {
                    String token = generationToken(user.getLoginId().toString(), true);
                    if (null != token) {
                        resultObject.put("token", token);
                        resultObject.put("uid", user.getLoginId());
                        resultObject.put("uname",
                                null != user.getLoginAccount() ? user.getLoginAccount()
                                        : ((null != user.getEarInfo()
                                                && null != user.getEarInfo().getEraNo())
                                                        ? user.getEarInfo().getEraNo() : ""));
                        resultObject.put(Constants.SESSION_B2B_OR_ERA, userType);
                    }
                    message = null;
                    memCachedClient.set(ConfigurationUtil.getString("b2b_app_token_prex")
                            .concat(token).concat(APP_LOGIN_USER_TYPE), userType,
                            new Date(1200000));
                    message = "登录成功！";
                    code = InterfaceResultCode.SUCCESS;
                    loginService.doAfterLogin(user, appId, null, appId);
                } else if (null != user) {
                    message = "用户已被系统禁用，请联系客服！";
                } else {
                    if (incrErrorCount(loginName, appId)) {
                        message = "登录频繁，请稍候再试！";
                    }
                }
            }

        } catch (Exception e) {
            logger.error("登录发生异常" + e.getMessage(), e);
        } finally {
            returnResult = new ReturnResult<>(code, message, resultObject);
            printJsonString(returnResult);
        }
    }

    /**
     * 通用发送短信验证码
     */
    public void sendMobile() {
        String message = "手机号码输入错误！";
        String code = InterfaceResultCode.FAILED;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String mobile = jsonParams.getString("mobile");
            int mobileType = jsonParams.getIntValue("mobileType");
            try {
                if (StringUtil.equalLen(mobile, 11)) {
                    boolean unCheck = true;
                    if (mobileType == MessageTypeEnum.VALIDMOBILE.getIndex()) {
                        List<User> userList = loginService.queryListByMobile(mobile);
                        mobileType = MessageTypeEnum.ORDERTRAIL.getIndex();// 无验证手机短信模版，故需要覆盖
                        if (null != userList && userList.size() > 0) {
                            message = "该手机号已被使用,请更换其他手机号！";
                            unCheck = false;
                        }
                    } else if (mobileType == MessageTypeEnum.MOBILEREGIST.getIndex()) {
                        User params = new User();
                        params.setMobile(mobile);
                        long userType = registservice.queryCustomerType(params);
                        mobileType = MessageTypeEnum.ORDERTRAIL.getIndex();
                        if (6 == userType) {
                            message = "此用户曾被用于购物，请进入免注册转会员！";
                            unCheck = false;
                        } else if (-1 != userType) {
                            message = "您的手机号码已注册，请使用其它手机号码！";
                            unCheck = false;
                        }
                    } else if (mobileType == EmailSendType.MSG_VERIFY_CODE.getStatus()) {
                        mobileType = MessageTypeEnum.ORDERTRAIL.getIndex();
                    }
                    if (unCheck) {
                        HttpSession session = this.getSession();
                        String status =
                                MessageTypeEnum.getMessageTypeEnumByIndex(mobileType).getStatus();
                        String valKey =
                                ConfigurationUtil.getString("b2b_send_message_memcached_key_prex")
                                        + mobile + '_' + status;
                        String dateKey = ConfigurationUtil.getString(
                                "b2b_send_message_date_memcached_key_prex") + mobile + '_' + status;
                        Date date = (Date) memCachedClient.get(dateKey);
                        if (null == date || (new Date().getTime() - date.getTime()) > (Integer
                                .parseInt(ConfigurationUtil
                                        .getString("b2b_common_page_msg_valid_time"))
                                * 60 * 1000)) {
                            boolean result;
                            result = messageRemoteService.sendMobileVerifyCode(mobile, session,
                                    status);
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
                                message = null;
                            } else {
                                message = "短信发送失败！";
                            }
                        } else {// 已发送
                            code = InterfaceResultCode.FREQUENTLY;// 频繁操作
                            message = "短信发送频繁";
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("发送短信存在异常：" + e.getMessage(), e);
                message = "短信发送失败！";
            }
        }
        returnResult = new ReturnResult(code, message, null);
        printJsonString(returnResult);
    }

    /**
     * 手机端注册
     */
    public void mobileRegist() {
        logger.info("APP手机端请求--------------------注册接口");
        String message = "请求参数异常！";
        String code = InterfaceResultCode.FAILED;
        User params = null;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String mobile = jsonParams.getString("mobile");
            String password = jsonParams.getString("password");
            if (StringUtils.isBlank(mobile) || !StringUtil.equalLen(mobile, 11)) {
                message = "手机号码输入错误！";
                returnResult = new ReturnResult<>(code, message, null);
            } else if (!StringUtil.equalLen(password, 32)) {
                message = "密码长度在6-20个字符之间！";
                returnResult = new ReturnResult<>(code, message, null);
            } else {
                params = new User();
                params.setLoginAccount(
                        StringUtil.getLoginAccount(Constants.CUSTOMER_TYPE_SD_MEMBER));
                params.setMobile(mobile);
                params.setLoginPassword(password);
            }
            if (null != params) {
                try {
                    int exists = registservice.checkUserExists(params);
                    if (0 == exists) {
                        params.setCustomerTypeId(1);
                        Map<String, String> o;
                        params.setRegisterDevice(Constants.REGISTER_DEVICE_APP);
                        params.setRegisterPlatfrom(Constants.REGISTER_PLATFORM_DEFAULT);
                        o = registservice.regist(params);
                        if (o.get("success") == null) {
                            logger.error("注册过程中出错：" + o.get("Exception"));
                            message = "注册失败，请刷新页面重新注册！";
                            returnResult = new ReturnResult<>(code, message, null);
                        } else {
                            Long loginId = Long.valueOf(o.get("loginId"));
                            params.setLoginId(loginId);
                            String token = generationToken(String.valueOf(loginId), true);
                            HashMap<String, Object> resultObject = new HashMap<>();
                            resultObject.put("uid", loginId);
                            resultObject.put("uname", params.getLoginAccount());
                            resultObject.put("token", token);
                            message = "注册成功！";
                            returnResult = new ReturnResult<>(InterfaceResultCode.SUCCESS, message,
                                    resultObject);
                            // 手机端注册业务走完后 清除验证码缓存
                            String status =
                                    MessageTypeEnum
                                            .getMessageTypeEnumByIndex(
                                                    MessageTypeEnum.ORDERTRAIL.getIndex())
                                            .getStatus();
                            String valKey = ConfigurationUtil.getString(
                                    "b2b_send_message_memcached_key_prex") + mobile + '_' + status;
                            String dateKey = ConfigurationUtil
                                    .getString("b2b_send_message_date_memcached_key_prex") + mobile
                                    + '_' + status;
                            memCachedClient.delete(valKey);
                            memCachedClient.delete(dateKey);
                            // 缓存会员类型(用于注册后自动登陆)
                            String userType = Constants.KM_USER_TYPE;
                            memCachedClient.set(
                                    ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                                            .concat(APP_LOGIN_USER_TYPE),
                                    userType, new Date(1200000));
                        }

                    } else {
                        switch (exists) {
                            case 2:
                                message = "您的用户名已经被注册，请使用其他用户名！";
                                break;
                            case 3:
                                message = "您的手机号码已注册，请使用其它手机号码！";
                                break;
                            case 4:
                                message = "您的用户名包含敏感字符，请使用其他用户名！";
                                break;
                            case 6:
                                message = "此用户曾被用于购物，请进入免注册转会员！";
                                break;
                            default:
                                message = "传入参数不符合注册规则";
                                break;
                        }
                        returnResult = new ReturnResult<>(code, message, null);
                    }
                } catch (Exception e1) {
                    logger.error("手机端注册异常" + e1.getMessage(), e1);
                    message = "注册失败，请稍后再试！";
                    returnResult = new ReturnResult<>(code, message, null);
                }
            }
        } else {
            returnResult = new ReturnResult<>(code, message, null);
        }
        printJsonString(returnResult);
    }

    /**
     * 修改密码
     */
    public void modifyPassword() {
        String message = "用户未登录！";
        String code = InterfaceResultCode.FAILED;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            try {
                long uid = jsonParams.getLongValue("uid");
                String oldPassword = jsonParams.getString("oldPassword");
                String password = jsonParams.getString("password");

                UserBaseInfo userBaseInfo = new UserBaseInfo();
                userBaseInfo.setLoginId(uid);
                userBaseInfo.setPassword(oldPassword);
                // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
                userBaseInfo =
                        this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");

                if (userBaseInfo != null && 0L != uid && StringUtil.equalLen(oldPassword, 32)
                        && StringUtil.equalLen(password, 32)) {

                    // password =
                    // StringUtil.passwordTwiceEncryption(password,saltInfo.getPaySalt());

                    Map<String, String> secMap = securityCentreService.queryUserSecurityInfo(uid);
                    String paymentpwd, loginPassword;
                    if (null == secMap) {
                        message = "用户不存在！";
                    } else if (null != (paymentpwd = secMap.get("PAYMENTPWD")) && StringUtil
                            .passwordTwiceEncryption(password, userBaseInfo.getLoginSalt())
                            .equalsIgnoreCase(paymentpwd)) {
                        message = "登录密码不能和支付密码一致！";
                    } else if (null != (loginPassword = secMap.get("LOGINPASSWORD"))
                            && userBaseInfo.getPassword().equalsIgnoreCase(loginPassword)) {
                        UserBaseInfo userInfo = new UserBaseInfo();
                        userInfo.setLoginId(uid);
                        userInfo.setLoginPassword(password);
                        if (resetPwdService.resetPassword(userInfo)) {
                            code = InterfaceResultCode.SUCCESS;
                            memCachedClient.delete(
                                    ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex")
                                            + uid);// 移除令牌
                        }
                        message = null;
                    } else {
                        message = "原始密码错误！";
                    }
                } else if (!StringUtil.equalLen(oldPassword, 32)) {
                    message = "原密码长度在6-20个字符之间！";
                } else if (!StringUtil.equalLen(password, 32)) {
                    message = "新密码长度在6-20个字符之间！";
                }
            } catch (Exception e) {
                logger.error("app修改密码异常" + e.getMessage(), e);
                message = "修改密码异常";
            }
        }
        returnResult = new ReturnResult<>(code, message, null);
        printJsonString(returnResult);
    }

    /**
     * 绑定手机
     */
    public void bindMobile() {
        String message = "用户未登录！";
        String code = InterfaceResultCode.FAILED;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            long uid = jsonParams.getLongValue("uid");
            String mobile = jsonParams.getString("mobile");
            String msgCode = jsonParams.getString("msgCode");
            if (StringUtil.equalLen(mobile, 11) && StringUtil.equalLen(msgCode, 6)
                    && StringUtil.isMobile(mobile)) {
                try {
                    if (validMobileCode(MessageTypeEnum.ORDERTRAIL.getIndex(), mobile, msgCode)) {
                        // 获取当前账户信息
                        User dataUser = loginService.queryUserByLoginId(String.valueOf(uid));
                        if (null != dataUser) {
                            // 绑定账户手机号
                            User data = new User();
                            data.setLoginId(uid);
                            data.setLoginAccount(dataUser.getLoginAccount().toLowerCase());
                            data.setMobile(mobile);
                            registservice.updateTempUserInfo(data);
                            /*
                             * // 首次验证手机号送积分 if (StringUtils.isBlank(dataUser.getMobile())) { try {
                             * userGrowingService.updateUserScoreInfo("RU0003", 1, dataUser
                             * .getLoginAccount().toLowerCase(), new HashMap<String, String>()); }
                             * catch (Exception e1) { logger.error("app首次验证手机送积分异常,账号:" +
                             * dataUser.getLoginAccount() + e1.getMessage(), e1); } }
                             */
                            code = InterfaceResultCode.SUCCESS;
                            message = "验证手机号成功";
                            // 验证手机业务走完后 清除验证码缓存
                            String status =
                                    MessageTypeEnum
                                            .getMessageTypeEnumByIndex(
                                                    MessageTypeEnum.ORDERTRAIL.getIndex())
                                            .getStatus();
                            String valKey = ConfigurationUtil.getString(
                                    "b2b_send_message_memcached_key_prex") + mobile + '_' + status;
                            String dateKey = ConfigurationUtil
                                    .getString("b2b_send_message_date_memcached_key_prex") + mobile
                                    + '_' + status;
                            memCachedClient.delete(valKey);
                            memCachedClient.delete(dateKey);
                        } else {
                            message = "用户不存在！";
                        }
                    } else {
                        message = "校验码错误！";
                    }
                } catch (Exception e) {
                    logger.error("app验证手机异常" + e.getMessage(), e);
                    message = "验证手机异常";
                }
            } else if (!StringUtil.equalLen(mobile, 11)) {
                message = "手机号码输入错误！";
            } else if (!StringUtil.equalLen(msgCode, 6)) {
                message = "手机校验码输入错误！";
            } else if (!StringUtil.isMobile(mobile)) {
                message = "手机号格式输入错误！";
            }
        }
        returnResult = new ReturnResult<>(code, message, null);
        printJsonString(returnResult);
    }

    /**
     * 获取用户联系信息
     */
    public String getUserContact() throws Exception {
        String message = "用户名长度在6-20个字符之间！";
        String code = InterfaceResultCode.FAILED;
        Map resultObject = null;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String uname = jsonParams.getString("uname");
            if (null != uname)
                uname = java.net.URLDecoder.decode(uname, "UTF-8").toLowerCase();
            if (StringUtil.withinRangeByte(uname, 6, 20)) {
                User params = new User();
                params.setLoginAccount(java.net.URLDecoder.decode(uname, "UTF-8"));
                User dataUser = registservice.queryTempUser(params);
                if (null == dataUser) {
                    message = "用户名不存在！";
                } else {
                    message = null;
                    code = InterfaceResultCode.SUCCESS;
                    resultObject = new HashMap();
                    resultObject.put("uid", dataUser.getLoginId());
                    resultObject.put("mobile", dataUser.getMobile());
                    resultObject.put("email", dataUser.getEmail());
                    resultObject.put("uname", uname);
                }
            }
        }
        returnResult = new ReturnResult(code, message, resultObject);
        return SUCCESS;
    }

    /**
     * 通用校验短信码
     */
    public void resetPwdInfoValid() {
        String message;
        String code = InterfaceResultCode.FAILED;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String mobile = jsonParams.getString("mobile");
            String msgCode = jsonParams.getString("msgCode");
            int mobileType = jsonParams.getIntValue("mobileType");// 验证码类型
            if (StringUtil.equalLen(mobile, 11) && StringUtil.equalLen(msgCode, 6)) {
                try {
                    if (validMobileCode(mobileType, mobile, msgCode)) {
                        code = InterfaceResultCode.SUCCESS;
                        message = "验证码正确";
                        // 校验手机验证码完成后 清除验证码缓存
                        String status =
                                MessageTypeEnum.getMessageTypeEnumByIndex(mobileType).getStatus();
                        String valKey =
                                ConfigurationUtil.getString("b2b_send_message_memcached_key_prex")
                                        + mobile + '_' + status;
                        String dateKey = ConfigurationUtil.getString(
                                "b2b_send_message_date_memcached_key_prex") + mobile + '_' + status;
                        memCachedClient.delete(valKey);
                        memCachedClient.delete(dateKey);
                    } else {
                        message = "验证码错误!";
                    }
                } catch (Exception e) {
                    logger.error("app验证验证码异常" + e.getMessage(), e);
                    message = "验证验证码异常";
                }
            } else {
                message = "参数格式错误！";
            }
        } else {
            message = "请求参数异常!";
        }
        returnResult = new ReturnResult<>(code, message, null);
        printJsonString(returnResult);
    }

    /**
     * 密码重置(未登录,但是账户验证通过)
     */
    public void resetPwd() {
        String message = "传入参数异常!";
        String code = InterfaceResultCode.FAILED;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        String password = jsonParams.getString("password");
        long uid = jsonParams.getLongValue("uid");
        try {
            if (!jsonParams.isEmpty()) {
                if (StringUtil.equalLen(password, 32) && uid != 0L) {
                    UserBaseInfo userInfo = new UserBaseInfo();
                    userInfo.setLoginId(uid);
                    userInfo.setLoginPassword(password);
                    boolean result = resetPwdService.resetPassword(userInfo);// 修改密码
                    if (result) {
                        message = "修改密码成功";
                        code = InterfaceResultCode.SUCCESS;
                    } else {
                        message = "修改密码失败！";
                    }
                } else if (!StringUtil.equalLen(password, 32)) {
                    message = "密码长度在6-20个字符之间！";
                }
            }
        } catch (Exception e) {
            logger.error("账号:" + uid + "app端密码重置异常" + e.getMessage(), e);
            message = "修改密码失败！";
        }
        returnResult = new ReturnResult<>(code, message, null);
        printJsonString(returnResult);
    }

    /**
     * 验证手机校验码
     */
    private boolean validMobileCode(int mobileType, String mobile, String msgCode) {
        boolean result = false;
        if (mobileType == 14 || mobileType == 16 || mobileType == 27) {
            mobileType = 2;
        }
        String status = MessageTypeEnum.getMessageTypeEnumByIndex(mobileType).getStatus();
        String valKey = ConfigurationUtil.getString("b2b_send_message_memcached_key_prex") + mobile
                + '_' + status;
        String dateKey = ConfigurationUtil.getString("b2b_send_message_date_memcached_key_prex")
                + mobile + '_' + status;
        Date date = (Date) memCachedClient.get(dateKey);
        String scode = (String) memCachedClient.get(valKey);// 验证码
        if (null != msgCode && msgCode.equals(scode)
                && (new Date().getTime() - date.getTime()) < (Integer.parseInt(
                        ConfigurationUtil.getString("b2b_common_msg_valid_time")) * 60 * 1000)) {
            result = true;
        }
        return result;
    }

    /**
     * 检查登录名是否存在 creatBy lijianjun
     */
    public void checkLoginName() {
        String message = "没有此用户信息！";
        String code = InterfaceResultCode.FAILED;
        Map<String, Object> map = null;
        try {
            HttpServletRequest request = getRequest();
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            String deviceInfo = request.getHeader(B2B_KM_APP_INFO_KEY);
            String key = null;
            String rs = "0";
            if (null != jsonParams && !jsonParams.isEmpty()) {
                String loginName = jsonParams.getString("loginName");
                String type = jsonParams.getString("type");
                String imgCode = jsonParams.getString("imgCode");
                String isCheckMobile = jsonParams.getString("isCheckMobile");
                if (StringUtils.isNotBlank(loginName) || StringUtils.isNotBlank(imgCode)) {
                    User user;
                    // 验证图形验证码
                    if (null != deviceInfo) {
                        JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
                        key = json.containsKey(KM_APP_ID_KEY) ? json.getString(KM_APP_ID_KEY) : "";
                    }
                    String countKey = APP_LOGIN_NAME_QUERY_COUNT_KEY.concat(key);
                    key = ConfigurationUtil.getString("b2b_km_valid_image_prex").concat(key)
                            .concat(null == type ? "" : type);
                    Object imgCodeMem = memCachedClient.get(key);
                    if (null != imgCode && null != imgCodeMem
                            && imgCode.equalsIgnoreCase(imgCodeMem.toString())) {
                        rs = "1";
                    }
                    memCachedClient.delete(ConfigurationUtil.getString("b2b_km_valid_image_prex")
                            .concat(key).concat(null == type ? "" : type));
                    String countStr;
                    map = new HashMap<>();
                    map.put("imgResult", rs);
                    if ("1".equals(isCheckMobile) && !StringUtil.isMobile(loginName)) {
                        message = "手机号不合法";
                        code = InterfaceResultCode.MOBILE_ILLEGAL;
                    } else if (null != (countStr = jedisCluster.get(countKey))
                            && Integer.parseInt(countStr) >= APP_MAX_LOGIN_NAME_QUERY) {
                        message = "图形验证码错误验证次数已达上限";
                        code = InterfaceResultCode.FAILED;
                    } else if (null == (user = loginService.queryAllUserInfoByName(loginName))
                            && "1".equals(rs)) {
                        message = "没有此登陆名-图形验证码正确";
                        code = InterfaceResultCode.NOT_EXIST;
                    } else if (user != null && user.getStatus() == 1 && "1".equals(rs)) {
                        map.put("user", user);
                        message = "账户已存在-图形验证码正确";
                        code = InterfaceResultCode.SUCCESS;
                    } else if (user != null && user.getStatus() == 0 && "1".equals(rs)) {
                        map.put("user", user);
                        message = "账户已禁用-图形验证码正确";
                        code = InterfaceResultCode.DISABLE;
                    } else {
                        // map.put("user", user);
                        message = "图形验证码错误";
                        code = InterfaceResultCode.FAILED;
                    }
                    if (jedisCluster.incr(countKey) <= 1) {
                        jedisCluster.expire(countKey, 600);
                    }
                }
            } else {
                message = "请求参数错误";
            }
        } catch (Exception e) {
            logger.error("app checkLoginName 异常", e);
            message = "请求参数错误";
        }
        returnResult = new ReturnResult(code, message, map);
        printJsonString(returnResult);
    }

    /**
     * 验证失败次数
     *
     * @param key 一般是loginName
     * @return 0 不合法 其它：合法次数
     */
    private Integer getErrorCount(String key, String appId) {
        String nameCount = jedisCluster.get(VALID_COUNT + key);
        String ipCount;
        int count = 0;
        if (null != (ipCount = jedisCluster.get(VALID_COUNT + appId))
                && (count = Integer.parseInt(ipCount)) >= MAX_IP_ERROR_COUNT) {
            return 0;
        } else if (nameCount != null && (count < Integer.parseInt(nameCount))
                && (count = Integer.parseInt(nameCount)) >= MAX_NAME_ERROR_COUNT) {
            return 0;
        } else if (ipCount == null) {
            return -1;
        } else {
            return count;
        }
    }

    /***
     * 失败次数自增,失败后调用
     *
     * @param key 一般是loginName
     * @param appId 冻结多长时间，单位s
     */
    private boolean incrErrorCount(String key, String appId) {
        boolean isOut;
        String nkey = VALID_COUNT + key;
        long count;
        if (1 == (count = jedisCluster.incr(nkey))) {
            jedisCluster.expire(nkey, 3600 * 2);
        }
        isOut = count + 1 > MAX_NAME_ERROR_COUNT;

        nkey = VALID_COUNT + appId;
        if (1 == (count = jedisCluster.incr(nkey))) {
            jedisCluster.expire(nkey, 600);
        }
        return isOut || (count + 1 > MAX_IP_ERROR_COUNT);
    }

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }
}
