package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.model.UserInfo;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.service.ThirdBindInfoService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.framework.sensitive.SensitiveWordFilter;
import com.kmzyc.user.remote.service.AccountInfoRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.EraInfoRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.AccountInfo;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings("unchecked")
@Scope
@Controller
public class AppUserInfoAction extends AppBaseAction {
    private static final long serialVersionUID = -4693712570252841563L;
    // Logger logger = Logger.getLogger(this.getClass());
    private static Logger logger = LoggerFactory.getLogger(AppUserInfoAction.class);

    private Long uid;
    private String message = "请求参数错误";
    private String code = InterfaceResultCode.FAILED;
    private ReturnResult<Map<String, Object>> returnResult;
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;
    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;
    @Resource(name = "registServiceImp")
    private RegistService registservice;
    @Resource(name = "thirdBindInfoService")
    private ThirdBindInfoService thirdBindInfoService;
    @Resource(name = "sensitiveWordFilter")
    private SensitiveWordFilter sensitiveWordFilter;
    @Resource
    private EraInfoRemoteService eraInfoRemoteService;
    @Resource
    private CustomerRemoteService customerRemoteService;

    @Resource
    private AccountInfoRemoteService accountInfoRemoteService;

    private UserInfo userInfo;
    private EraInfo era;

    // 短信校验有效时间
    // private String MESSAGE_VALID_TIME = ConfigurationUtil.getString("b2b_common_msg_valid_time");
    // 发送短信key前缀
    // private String SEND_MESSAGE_KEY = ConfigurationUtil
    // .getString("b2b_send_message_memcached_key_prex");
    // 发送短信时间key前缀
    // private String SEND_MESSAGE_DATE_KEY = ConfigurationUtil
    // .getString("b2b_send_message_date_memcached_key_prex");

    /**
     * 验证手机校验码
     */
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
     * 第三方转会员 修改会员登陆信息 creatBy lijianjun
     */
    public void updateLoginInfo() {
        String message = "请求参数错误！";
        String code = InterfaceResultCode.FAILED;
        uid = Long.parseLong(getUserid());
        // 登陆类型 01：康美会员，02：时代会员
        com.pltfm.app.vobject.LoginInfo loginInfo = new com.pltfm.app.vobject.LoginInfo();
        loginInfo.setN_LoginId(uid.intValue());
        User params = new User();
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String mobile = jsonParams.getString("mobile");
            String uname = jsonParams.getString("uname");
            String password = jsonParams.getString("password");
            uname = StringUtils.isEmpty(uname) ? "m_" + mobile : uname;
            try {
                if (null != uname)
                    uname = java.net.URLDecoder.decode(uname, "UTF-8").toLowerCase();
                if (StringUtil.equalLen(mobile, 11) && StringUtil.equalLen(password, 32)
                        && StringUtil.withinRangeByte(uname, 6, 20)) {
                    loginInfo.setMobile(mobile);
                    loginInfo.setLoginPassword(password);
                    params.setMobile(mobile);
                    loginInfo.setLoginAccount(uname);
                    loginInfo.setLoginPassword(password);
                    params.setLoginAccount(uname);

                    int exists = registservice.checkUserExists(params);
                    if (0 == exists) {
                        try {
                            // 第三方 同时更新第三方绑定表
                            List<ThirdBindInfo> thirdBindInfoList = thirdBindInfoService
                                    .queryBindInfo(loginInfo.getN_LoginId().toString());
                            if (thirdBindInfoList != null && thirdBindInfoList.size() > 0) {
                                ThirdBindInfo thirdBindInfo = thirdBindInfoList.get(0);
                                thirdBindInfo.setBindType(ThirdConstant.THIRD_BIND_TYPE_FORMAL);
                                thirdBindInfo.setLastUpdateTime(
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                                .format(new Date()));
                                thirdBindInfo.setIsBind("Y");
                                thirdBindInfoService.updateBindInfo(thirdBindInfo);
                            }
                            // 时代信息 同时更新时代表login_account
                            EraInfo eraInfo = eraInfoService
                                    .queryEraInfoByLoginId(loginInfo.getN_LoginId().longValue());
                            if (eraInfo != null) {
                                // 时代会员同意设置为m_+mobile
                                uname = "m_" + mobile;
                                com.kmzyc.b2b.vo.EraInfo era = new com.kmzyc.b2b.vo.EraInfo();
                                era.setLoginAccount(uname);
                                era.setEraNo(eraInfo.getEraNo().toString());
                                // EraInfoRemoteService eraInfoRemoteService =
                                // (EraInfoRemoteService)
                                // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                                // "eraInfoRemoteService");
                                eraInfoRemoteService.updateUseEraInfo(era);
                            }
                            // 资料正常 完善会员资料
                            // CustomerRemoteService customerRemoteService =
                            // (CustomerRemoteService)
                            // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                            // "customerRemoteService");
                            customerRemoteService.updateByPrimaryKeySelective(loginInfo);
                            // 同步完善账户信息
                            // AccountInfoRemoteService accountInfoRemoteService =
                            // (AccountInfoRemoteService) RemoteTool.getRemote(
                            // Constants.REMOTE_SERVICE_CUSTOMER, "accountInfoRemoteService");
                            AccountInfo accountInfo = new AccountInfo();
                            accountInfo.setN_LoginId(uid.intValue());
                            accountInfo.setAccountLogin(uname);
                            accountInfo.setMobile(mobile);
                            accountInfoRemoteService.updateByLoginId(accountInfo);
                            message = "成功";
                            returnResult = new ReturnResult<Map<String, Object>>(
                                    InterfaceResultCode.SUCCESS, message, null);
                        } catch (Exception e) {
                            logger.error("完善会员信息异常" + e.getMessage(), e);
                            message = "失败";
                            returnResult =
                                    new ReturnResult<Map<String, Object>>(code, message, null);
                        }
                        // 转会员业务走完 清除短信验证码缓存
                        String status =
                                MessageTypeEnum
                                        .getMessageTypeEnumByIndex(
                                                EmailSendType.MSG_VERIFY_CODE.getStatus())
                                        .getStatus();
                        String valKey =
                                ConfigurationUtil.getString("b2b_send_message_memcached_key_prex")
                                        + mobile + '_' + status;
                        String dateKey = ConfigurationUtil.getString(
                                "b2b_send_message_date_memcached_key_prex") + mobile + '_' + status;
                        memCachedClient.delete(valKey);
                        memCachedClient.delete(dateKey);
                    } else {
                        switch (exists) {
                            case 2:
                                message = "您的用户名已经被注册，请使用其他用户名！";
                                returnResult = new ReturnResult(code, message, null);
                                break;
                            case 3:
                                message = "您的手机号码已注册，请使用其它手机号码！";
                                returnResult = new ReturnResult(code, message, null);
                                break;
                            case 4:
                                message = "您的用户名包含敏感字符，请使用其他用户名！";
                                returnResult = new ReturnResult(code, message, null);
                                break;
                            default:
                                break;
                        }
                    }
                } else if (!StringUtil.equalLen(mobile, 11)) {
                    message = "手机号码输入错误！";
                    returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
                } else if (!StringUtil.equalLen(password, 32)) {
                    message = "密码长度在6-20个字符之间！";
                    returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
                } else if (!StringUtil.withinRangeByte(uname, 6, 20)) {
                    message = "用户名长度在6-20个字符之间！";
                    returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
                } else {
                    message = "参数异常！";
                    returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
                }

            } catch (Exception e) {
                logger.error("完善会员信息异常" + e.getMessage(), e);
                message = "失败";
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }

        } else

        {
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }

        printJsonString(returnResult);
    }

    /**
     * 更新时代信息 creatBy lijianjun
     */
    public void updateEraInfo() {
        String message = "请求参数异常！";
        String code = InterfaceResultCode.FAILED;
        uid = Long.parseLong(getUserid());
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());

        if (null != jsonParams && !jsonParams.isEmpty()) {
            Long eraInfoId = jsonParams.getLong("eraInfoId");
            String eraNo = jsonParams.getString("eraNo");
            // 同步更新时代信息
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                // EraInfoRemoteService eraInfoRemoteService =
                // (EraInfoRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                // "eraInfoRemoteService");
                com.kmzyc.b2b.vo.EraInfo eraInfo = new com.kmzyc.b2b.vo.EraInfo();
                eraInfo.setEraInfoId(new BigDecimal(eraInfoId));
                eraInfo.setEraNo(eraNo);
                int result = eraInfoRemoteService.updateEraInfo(eraInfo);
                if (result == -1) {
                    returnResult = new ReturnResult(code, message, null);
                } else if (result == 1) {
                    message = "成功";
                    // 获取会员基本信息
                    userInfo = userInfoService.queryUserInfoById(uid.intValue());
                    map.put("userInfo", userInfo);
                    // 获取时代信息
                    era = eraInfoService.queryEraInfoByLoginId(uid);
                    map.put("era", era);
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, message, map);
                } else {
                    message = "失败";
                    returnResult = new ReturnResult(code, message, null);
                }
            } catch (Exception e) {
                logger.error("更新时代信息异常" + e.getMessage(), e);
                message = "失败";
                returnResult = new ReturnResult(code, message, null);
            }
        } else {
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }
        printJsonString(returnResult);
    }

    /**
     * 获取个人信息 creatBy lijianjun
     */
    public void queryUserInfo() {
        uid = Long.parseLong(getUserid());
        // 登陆类型 01：康美会员，02：时代会员
        String loginType = getRequest().getAttribute(APP_LOGIN_USER_TYPE).toString();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(loginType) && Constants.KM_USER_TYPE.equals(loginType)) {// 康美会员
                // 获取会员基本信息
                userInfo = userInfoService.queryUserInfoById(uid.intValue());
                // 获取账号状态
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
                        logger.error("该账户:" + userInfo.getAccountLogin() + "信息异常，登陆ID:" + uid);
                    }
                } else {// 正式会员
                    map.put("infoPerfect", "Y");// 默认已完善
                }
                message = "成功";
                map.put("userInfo", userInfo);
                map.put("loginType", loginType);
                if (StringUtils.isNotBlank(userInfo.getHeadSculpture())
                        && !"no_img_mid.jpg".equals(userInfo.getHeadSculpture())) {// 用户头像
                    map.put("userImgUrl", ConfigurationUtil.getString("USER_IMG_PATH")
                            + userInfo.getHeadSculpture() + "?i=" + new Date().getTime());
                } else { // 默认头像
                    map.put("userImgUrl", ConfigurationUtil.getString("CSS_JS_PATH")
                            + "images/default__man_err100_100.jpg?i=" + new Date().getTime());
                }
                returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                        message, map);
            } else if (StringUtils.isNotBlank(loginType) && Constants.LOGINTYPE.equals(loginType)) {// 时代会员
                era = eraInfoService.queryEraPersonalInfo(uid);

                // 转换时代数据
                // 性别
                if (StringUtil.isEmpty(era.getSex()) || "保密".equals(era.getSex())) {
                    era.setSex("");
                } else if ("男".equals(era.getSex())) {
                    era.setSex("0");
                } else if ("女".equals(era.getSex())) {
                    era.setSex("1");
                } else {
                    era.setSex("");
                }
                //

                // 获取账户状态
                if (StringUtils.isNotBlank(era.getLoginAccount())) {// 已完善
                    map.put("infoPerfect", "Y");
                } else {// 未完善
                    map.put("infoPerfect", "N");
                }
                message = "成功";
                map.put("era", era);
                map.put("loginType", loginType);
                if (StringUtils.isNotBlank(era.getHeadSculpture())
                        && !"no_img_mid.jpg".equals(era.getHeadSculpture())) {// 用户头像
                    map.put("userImgUrl", ConfigurationUtil.getString("USER_IMG_PATH")
                            + era.getHeadSculpture() + "?i=" + new Date().getTime());
                } else { // 默认头像
                    map.put("userImgUrl", ConfigurationUtil.getString("CSS_JS_PATH")
                            + "images/default__man_err100_100.jpg?i=" + new Date().getTime());
                }
                returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                        message, map);
            } else {
                logger.error("会员登陆类型异常---登陆类型" + loginType + "---登陆ID：" + uid);
                message = "会员登陆类型异常!";
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
        } catch (Exception e) {
            logger.error("获取会员信息异常---登陆ID：" + uid + "---" + e.getMessage(), e);
            message = "获取会员信息异常!";
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }
        printJsonString(returnResult);
    }

    // 修改个人信息
    public void updateUserInfo() {
        code = InterfaceResultCode.FAILED;
        String uId = getUserid();
        if (StringUtils.isBlank(uId)) {
            message = "未登录！";
        } else {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            if (null != jsonParams && !jsonParams.isEmpty()) {
                try {
                    String nickName = jsonParams.getString("nickName");
                    // 过滤敏感词
                    if (!StringUtils.isEmpty(nickName)) {
                        nickName = sensitiveWordFilter.replaceSensitiveWord(nickName);
                    }
                    String sex = jsonParams.getString("sex");
                    String birthday = jsonParams.getString("birthday");
                    // 获取会员基本信息
                    userInfo = userInfoService.queryUserInfoById(Integer.parseInt(uId));
                    userInfo.setNickName(nickName);
                    userInfo.setSex(sex);
                    if (StringUtils.isNotBlank(birthday)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
                        userInfo.setBirthday(sdf.parse(birthday));
                    }
                    // 修改个人信息
                    userInfoService.updateUserInfo(userInfo);
                    code = InterfaceResultCode.SUCCESS;
                    message = "修改信息成功";
                } catch (Exception e) {
                    logger.error("修改个人信息失败" + e.getMessage(), e);
                    message = "修改信息失败";
                }
            } else {
                message = "请求参数异常!";
            }
        }
        returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        printJsonString(returnResult);
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public EraInfo getEra() {
        return era;
    }

    public void setEra(EraInfo era) {
        this.era = era;
    }

}
