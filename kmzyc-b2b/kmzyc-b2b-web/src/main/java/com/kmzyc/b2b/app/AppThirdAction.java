package com.kmzyc.b2b.app;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.AlipayUserDetail;
import com.google.common.base.Strings;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.third.service.ThirdBindInfoService;
import com.kmzyc.b2b.util.ActionUtils;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.weibo.model.WeiboException;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.util.Base64Util;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.qq.connect.QQConnectException;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings("unchecked")
@Controller("appNewThirdAction")
@Scope("prototype")
public class AppThirdAction extends AppBaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    // private static Logger log = LoggerFactory.getLogger(AppThirdAction.class);
    private static Logger log = LoggerFactory.getLogger(AppThirdAction.class);

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAcctInfoService;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource(name = "thirdBindInfoService")
    private ThirdBindInfoService thirdBindInfoService;

    @Resource
    private ShopCartInfoService shopCartInfoService;

    // 返回结果
    private ReturnResult<HashMap<String, Object>> returnResult;

    // APP密钥
    // private static final String APP_SECRETKEY = ConfigurationUtil.getString("app_secretkey");

    // protected static final String APP_LOGIN_USER_TYPE = ConfigurationUtil
    // .getString("b2b_km_app_user_type");// 登录类型
    // APP token前缀
    // private static final String APP_TOKEN_PREX =
    // ConfigurationUtil.getString("b2b_app_token_prex");

    /**
     * wap端登录以及绑定请求
     * 
     * @return
     * @throws IOException
     */
    public void thirdRegistAndBind() {
        log.info("第三方绑定thirdLogin4App()方法begin");
        HttpServletResponse response = ServletActionContext.getResponse();
        Map resultObject = null;
        response.setCharacterEncoding("utf-8");
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        // openid为必传参数
        if (null == jsonParams || jsonParams.isEmpty()
                || StringUtils.isEmpty(jsonParams.getString("openid"))) {
            returnResult =
                    new ReturnResult(InterfaceResultCode.FAILED, "app第三方绑定参数缺失！", resultObject);
            this.printJsonString(returnResult);
            return;
        }
        String acctType = jsonParams.getString("acctType");
        // 参数非空判断
        if (StringUtils.isEmpty(acctType)) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "app第三方绑定参数：第三方类型为空！",
                    resultObject);
            this.printJsonString(returnResult);
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        if ("QQ".equalsIgnoreCase(acctType)) {
            thirdRegist4QQ(jsonParams);
            return;
        } else if ("wx".equalsIgnoreCase(acctType)) {
            thirdRegist4Wx(jsonParams);
            return;
        } else if ("sina".equalsIgnoreCase(acctType)) {
            thirdRegist4Sina(jsonParams);
            return;
        } else if ("alipay".equalsIgnoreCase(acctType)) {
            thirdRegist4Alipay(jsonParams);
            return;
        } else if ("taobao".equalsIgnoreCase(acctType)) {
            thirdRegist4Taobao(jsonParams);
            return;
        } else {
            resultObject = new HashMap();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                    "app第三方绑定异常：不支持此第三方登录：" + acctType, resultObject);
            this.printJsonString(returnResult);
            return;
        }
    }

    private void thirdRegist4Alipay(JSONObject jsonParams) {
        AlipayUserDetail userDetail = new AlipayUserDetail();
        userDetail.setAlipayUserId(jsonParams.getString("openid"));
        if (jsonParams.containsKey("real_name")) {
            userDetail.setRealName(jsonParams.getString("real_name"));
        }
        if (jsonParams.containsKey("sex")) {
            userDetail.setSex(jsonParams.getString("sex"));
        }
        // 执行和康美会员绑定关系的返回结果map
        Map<String, Object> resultMap = null;
        // 第三方普通登录业务逻辑,具体注释请看service层
        try {
            // 是否绑定已有账号
            if (jsonParams.containsKey("uuid"))
                resultMap = thirdAcctInfoService.commonBindWithNormalMember4App(userDetail,
                        "alipay", jsonParams.getString("uuid"), null);
            else
                resultMap = thirdAcctInfoService.commonBizAbountThirdLogin4App(userDetail, "alipay",
                        null);
            Assert.notNull(resultMap);
            ActionUtils.putValue2UserInfo4App(jsonParams, resultMap);
            if (resultMap.containsKey("alreadyBind")) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "第三方接口：账号已绑定，无需重复绑定！", null);
                this.printJsonString(returnResult);
                return;
            }
            // 注册后生成token
            String token = "";
            if (!jsonParams.containsKey("uuid")) {
                token = this.generationToken(resultMap.get("uid").toString(), true);
                // 缓存登录类型
                memCachedClient.set(ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                        .concat(APP_LOGIN_USER_TYPE), Constants.KM_USER_TYPE, new Date(1200000));
                resultMap.put("token", token);
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                    "app第三方绑定异常：支付宝接口" + e.getMessage(), null);
            this.printJsonString(returnResult);
            return;
        }

        if (!Strings.isNullOrEmpty((String) resultMap.get("errorMsg"))) {
            log.info("errorMsg:" + resultMap.get("errorMsg"));
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "app第三方绑定异常：支付宝接口", null);
            this.printJsonString(returnResult);
            return;
        }

        // 合并购物车
        String uid = "";
        if (jsonParams.containsKey("uuid")) {
            uid = jsonParams.getString("uuid");
        } else {
            uid = resultMap.get("uid").toString();
        }
        this.mergeShopCar(uid);
        // end

        // log.info("loginUser...." + loginUser.toString());
        // 将第三方账户所对应的康美会员的信息写入到session
        // 成功时打印结果
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口：微信绑定成功！", resultMap);
        this.printJsonString(returnResult);
        return;
    }

    private void thirdRegist4Taobao(JSONObject jsonParams) {
        try {

            com.taobao.api.domain.User user = new com.taobao.api.domain.User();
            user.setUid(jsonParams.getString("openid"));
            if (jsonParams.containsKey("nickname")) {
                user.setNick(jsonParams.getString("nickname"));
            }
            if (jsonParams.containsKey("sex")) {
                user.setSex(jsonParams.getString("sex"));
            }
            if (jsonParams.containsKey("avatar")) {
                user.setAvatar(jsonParams.getString("avatar"));
            }
            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;
            // 第三方普通登录业务逻辑,具体注释请看service层
            if (jsonParams.containsKey("uuid"))// 是否绑定已有账号
                resultMap = thirdAcctInfoService.commonBindWithNormalMember4App(user, "taobao",
                        jsonParams.getString("uuid"), null);
            else
                resultMap =
                        thirdAcctInfoService.commonBizAbountThirdLogin4App(user, "taobao", null);
            ActionUtils.putValue2UserInfo4App(jsonParams, resultMap);
            Assert.notNull(resultMap);
            if (resultMap.containsKey("alreadyBind")) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "第三方接口：账号已绑定，无需重复绑定！", null);
                this.printJsonString(returnResult);
                return;
            }
            if (!Strings.isNullOrEmpty((String) resultMap.get("errorMsg"))) {
                log.error("淘宝请求授权后处理对应账户信息发生异常errorMsg:" + resultMap.get("errorMsg"));
                returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                        resultMap.get("errorMsg").toString(), null);
                this.printJsonString(returnResult);
                return;
            }
            // 注册后生成token
            String token = "";
            if (!jsonParams.containsKey("uuid")) {
                token = this.generationToken(resultMap.get("uid").toString(), true);
                // 缓存登录类型
                memCachedClient.set(ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                        .concat(APP_LOGIN_USER_TYPE), Constants.KM_USER_TYPE, new Date(1200000));
                resultMap.put("token", token);
            }

            // 合并购物车
            String uid = "";
            if (jsonParams.containsKey("uuid")) {
                uid = jsonParams.getString("uuid");
            } else {
                uid = resultMap.get("uid").toString();
            }
            this.mergeShopCar(uid);
            // end

            // 成功时打印结果
            returnResult =
                    new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口：微信绑定成功！", resultMap);
            this.printJsonString(returnResult);
            return;
        } catch (IOException e) {
            log.error("WeiboException:" + "获取淘宝信息错误" + e.getMessage(), e);
            // 和淘宝支付链接发生错误
            String message = "淘宝支付链接发生错误:" + e;
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
            return;
        } catch (Exception e) {
            log.info("TaobaoAfterLoginRedirectAction==>" + e.getMessage(), e);
            log.error("Exception has occurred:" + e.getMessage(), e);
            String message = "Exception has occurred:" + e;
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
        }
        return;
    }

    private void thirdRegist4Sina(JSONObject jsonParams) {
        try {
            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;
            // 第三方普通登录业务逻辑,具体注释请看service层
            if (jsonParams.containsKey("uuid"))// 是否绑定已有账号
                resultMap = thirdAcctInfoService.commonBindWithNormalMember4App(jsonParams, "sina",
                        jsonParams.getString("uuid"), jsonParams.getString("openid"));
            else
                resultMap = thirdAcctInfoService.commonBizAbountThirdLogin4App(jsonParams, "sina",
                        jsonParams.getString("openid"));
            ActionUtils.putValue2UserInfo4App(jsonParams, resultMap);
            Assert.notNull(resultMap);
            if (resultMap.containsKey("alreadyBind")) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "第三方接口：账号已绑定，无需重复绑定！", null);
                this.printJsonString(returnResult);
                return;
            }
            if (null != resultMap.get("errorMsg") && !"".equals(resultMap.get("errorMsg"))) {
                log.error("wx请求授权后处理对应账户信息发生异常,openId=,errorMsg:" + resultMap.get("errorMsg"));
                returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                        resultMap.get("errorMsg").toString(), null);
                this.printJsonString(returnResult);
                return;
            }
            // 注册后生成token
            String token = "";
            if (!jsonParams.containsKey("uuid")) {
                token = this.generationToken(resultMap.get("uid").toString(), true);
                // 缓存登录类型
                memCachedClient.set(ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                        .concat(APP_LOGIN_USER_TYPE), Constants.KM_USER_TYPE, new Date(1200000));
                resultMap.put("token", token);
            }

            // 合并购物车
            String uid = "";
            if (jsonParams.containsKey("uuid")) {
                uid = jsonParams.getString("uuid");
            } else {
                uid = resultMap.get("uid").toString();
            }
            this.mergeShopCar(uid);
            // end

            // 成功时打印结果
            returnResult =
                    new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口：微博绑定成功！", resultMap);
            this.printJsonString(returnResult);
            return;
        } catch (WeiboException e) {
            log.error("WeiboException:" + "获取微博信息错误" + e.getMessage(), e);
            // 和微博链接发生错误
            String message = "获取微博信息错误!" + e;
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
            return;
        } catch (Exception e) {
            log.error("Exception has occurred:" + e.getMessage(), e);
            String message = "Exception has occurred:" + e;
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
        }
        return;
    }

    private void thirdRegist4Wx(JSONObject jsonParams) {
        // 执行和康美会员绑定关系的返回结果map
        Map<String, Object> resultMap = null;
        // 是否是从绑定管理中心过来的主动绑定
        try {
            com.kmzyc.b2b.third.model.wechat.UserInfo userInfo =
                    new com.kmzyc.b2b.third.model.wechat.UserInfo();
            userInfo.setOpenid(jsonParams.getString("openid"));
            if (jsonParams.containsKey("nickname")) {
                userInfo.setNickname(jsonParams.getString("nickname"));
            }
            if (jsonParams.containsKey("sex")) {
                userInfo.setSex(jsonParams.getString("sex"));
            }
            if (jsonParams.containsKey("avatar")) {
                userInfo.setHeadimgurl(jsonParams.getString("avatar"));
            }
            if (jsonParams.containsKey("province")) {
                userInfo.setProvince(jsonParams.getString("province"));
            }
            if (jsonParams.containsKey("city")) {
                userInfo.setCity(jsonParams.getString("city"));
            }
            if (jsonParams.containsKey("language")) {
                userInfo.setLanguage(jsonParams.getString("language"));
            }
            if (jsonParams.containsKey("unionid")) {
                userInfo.setUnionid(jsonParams.getString("unionid"));
            }
            // 普通登录业务逻辑,具体注释请看service层
            if (jsonParams.containsKey("uuid"))// 是否绑定已有账号
                resultMap = thirdAcctInfoService.commonBindWithNormalMember4App(userInfo, "wx",
                        jsonParams.getString("uuid"), null);
            else
                resultMap =
                        thirdAcctInfoService.commonBizAbountThirdLogin4App(userInfo, "wx", null);

            ActionUtils.putValue2UserInfo4App(jsonParams, resultMap);
            if (resultMap.containsKey("alreadyBind")) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "第三方接口：账号已绑定，无需重复绑定！", null);
                this.printJsonString(returnResult);
                return;
            }
            // 返回错误码
            if (!Strings.isNullOrEmpty((String) resultMap.get("errorMsg"))) {
                log.error("wx请求授权后处理对应账户信息发生异常,openId=" + jsonParams.getString("openid")
                        + ",errorMsg:" + resultMap.get("errorMsg"));
                returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                        resultMap.get("errorMsg").toString(), null);
                this.printJsonString(returnResult);
                return;
            }
            // 注册后生成token
            String token = "";
            if (!jsonParams.containsKey("uuid")) {
                token = this.generationToken(resultMap.get("uid").toString(), true);
                if (null == token) {
                    returnResult =
                            new ReturnResult(InterfaceResultCode.FAILED, "第三方注册生成token失败！", null);
                    this.printJsonString(returnResult);
                    return;
                } else {
                    // 缓存登录类型
                    memCachedClient.set(
                            ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                                    .concat(APP_LOGIN_USER_TYPE),
                            Constants.KM_USER_TYPE, new Date(1200000));
                }
                resultMap.put("token", token);
            }

            // 合并购物车
            String uid = "";
            if (jsonParams.containsKey("uuid")) {
                uid = jsonParams.getString("uuid");
            } else {
                if (null != resultMap) {
                    uid = resultMap.get("uid").toString();
                }
            }
            this.mergeShopCar(uid);
            // end

            // 将第三方账户所对应的康美会员的信息写入到session
            Long loginId = (Long) resultMap.get("loginId");
            System.out.println("loginId...." + loginId);
            log.info("第三方用户所对应的loginId=" + loginId);
            log.info("==============微信登录成功~");
        } catch (Exception e) {
            log.info("Exception:" + "微信登录错误,具体错误信息为=" + e.getMessage());
            String message = "微信登录错误,具体错误信息为=" + e.getMessage();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
        }
        // 成功时打印结果
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口：微信绑定成功！", resultMap);
        this.printJsonString(returnResult);
        return;
    }

    private void thirdRegist4QQ(JSONObject jsonParams) {
        try {
            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;
            if (jsonParams.containsKey("uuid"))// 是否绑定已有账号
                resultMap = thirdAcctInfoService.commonBindWithNormalMember4App(jsonParams, "QQ",
                        jsonParams.getString("uuid"), jsonParams.getString("openid"));
            else
                resultMap = thirdAcctInfoService.commonBizAbountThirdLogin4App(jsonParams, "QQ",
                        jsonParams.getString("openid"));
            Assert.notNull(resultMap);
            ActionUtils.putValue2UserInfo4App(jsonParams, resultMap);
            // 第三方普通登录业务逻辑,具体注释请看service层
            if (resultMap.containsKey("alreadyBind")) {
                returnResult =
                        new ReturnResult(InterfaceResultCode.FAILED, "第三方接口：账号已绑定，无需重复绑定！", null);
                this.printJsonString(returnResult);
                return;
            }
            // 注册后生成token
            String token = "";
            if (!jsonParams.containsKey("uuid")) {
                token = this.generationToken(resultMap.get("uid").toString(), true);
                if (null == token) {
                    returnResult =
                            new ReturnResult(InterfaceResultCode.FAILED, "第三方注册生成token失败！", null);
                    this.printJsonString(returnResult);
                    return;
                } else {
                    // 缓存登录类型
                    memCachedClient.set(
                            ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                                    .concat(APP_LOGIN_USER_TYPE),
                            Constants.KM_USER_TYPE, new Date(1200000));
                }
                resultMap.put("token", token);
            }
            if (!Strings.isNullOrEmpty((String) resultMap.get("errorMsg"))) {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                        resultMap.get("errorMsg").toString(), null);
                this.printJsonString(returnResult);
                return;
            }

            // 合并购物车
            String uid = "";
            if (jsonParams.containsKey("uuid")) {
                uid = jsonParams.getString("uuid");
            } else {
                uid = resultMap.get("uid").toString();
            }
            this.mergeShopCar(uid);
            // end

            // 成功时打印结果
            returnResult =
                    new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口：QQ绑定成功！", resultMap);
            this.printJsonString(returnResult);
            return;

        } catch (QQConnectException e) {
            log.error("QQException:" + "与QQ链接错误" + e);
            // 和QQ链接发生错误
            String message = "与QQ链接错误," + e.getMessage();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
            return;

        } catch (Exception e) {
            log.error("Exception:" + e);
            String message = "与QQ链接错误,具体错误信息为=" + e.getMessage();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, message, null);
            this.printJsonString(returnResult);
            return;
        }
    }

    /**
     * 生成token
     * 
     * @param kmAppId
     * @return
     */
    private String generationToken(String kmAppId, boolean login) {
        Calendar cal = Calendar.getInstance();
        String token = MD5.getMD5Str(
                ConfigurationUtil.getString("app_secretkey") + kmAppId + cal.getTimeInMillis());
        memCachedClient.set(ConfigurationUtil.getString("b2b_app_token_prex").concat(token),
                cal.getTimeInMillis(), new Date(1200000));
        if (login) {
            memCachedClient.set(ConfigurationUtil.getString("b2b_app_token_prex").concat(token)
                    .concat(APP_LOGIN_FLAG), kmAppId, new Date(1200000));
        }
        return token;
    }

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public void listThirdAppsBindStatus() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        // openid为必传参数
        if (null == jsonParams || jsonParams.isEmpty()
                || StringUtils.isEmpty(jsonParams.getString("loginId"))) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "app第三方绑定参数缺失！", null);
            this.printJsonString(returnResult);
            return;
        }
        String loginId = jsonParams.getString("loginId");

        try {
            List<ThirdBindInfo> bindInfos = thirdBindInfoService.queryBindInfo(loginId);
            returnResult =
                    new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口绑定状态查询成功！", bindInfos);
            this.printJsonString(returnResult);
        } catch (Exception e) {
            returnResult =
                    new ReturnResult(InterfaceResultCode.SUCCESS, "第三方接口绑定状态查询异常！", e.getMessage());
            this.printJsonString(returnResult);
        }

    }

    public void mergeShopCar(String uid) {
        String deviceInfo = getRequest().getHeader(B2B_KM_APP_INFO_KEY);

        try {
            JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
            String appId = json.containsKey(KM_APP_ID_KEY) ? json.getString(KM_APP_ID_KEY) : null;
            if (!StringUtil.isEmpty(appId) && !StringUtil.isEmpty(uid)) {
                shopCartInfoService.mergeShopCar(uid, appId);
            }
        } catch (Exception e) {
            log.info("第三方绑定,合并购物车失败", e);
        }
    }

}
