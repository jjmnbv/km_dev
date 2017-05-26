package com.kmzyc.b2b.third.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.AlipayUserDetail;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.wechat.UserInfo;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.weibo.model.User;
import com.qq.connect.javabeans.qzone.UserInfoBean;

/**
 * 实体转换类 将第三方账号转换成我们自己的实体类(ThirdAccountInfo)
 * 
 * @author Administrator
 * 
 */
public class ThirdEntityUtil {

    /**
     * 将QQ提供的第三方用户信息统一转换成我们系统的ThirdAccountInfo
     * 
     * @param userInfoBean QQ封装用户信息的bean
     * @param openId
     * @return
     */
    public static ThirdAccountInfo QQUserConvertToThirdAcct(UserInfoBean userInfoBean, String openId) {
        ThirdAccountInfo qqAcct = new ThirdAccountInfo();
        qqAcct.setOpenId(openId);
        qqAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
        qqAcct.setGender(userInfoBean.getGender());
        qqAcct.setHeadingUrl(userInfoBean.getAvatar().getAvatarURL50());
        qqAcct.setNickName(userInfoBean.getNickname());
        qqAcct.setStatus("Y");
        qqAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        qqAcct.setRemark("来自QQ的用户");

        // qq提供的获取用户接口并未返回省份城市信息
        qqAcct.setLanguage("zh_CN");

        // 存取个性化信息(已删2014-03-27)
        return qqAcct;
    }

    /**
     * 将QQ提供的第三方用户信息统一转换成我们系统的ThirdAccountInfo
     * 
     * @param userInfoBean QQ封装用户信息的bean
     * @param openId
     * @return
     */
    public static ThirdAccountInfo QQUserConvertToThirdAcct(JSONObject jsonParams, String openId) {
        ThirdAccountInfo qqAcct = new ThirdAccountInfo();
        qqAcct.setOpenId(openId);
        qqAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
        qqAcct.setGender(jsonParams.getString("sex"));
        qqAcct.setHeadingUrl(jsonParams.getString("avatar"));
        qqAcct.setNickName(jsonParams.getString("nickname"));
        qqAcct.setStatus("Y");
        qqAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        qqAcct.setRemark("来自QQ的用户");

        // qq提供的获取用户接口并未返回省份城市信息
        qqAcct.setLanguage("zh_CN");

        // 存取个性化信息(已删2014-03-27)
        return qqAcct;
    }

    /**
     * 将微信提供的第三方用户信息统一转换成我们系统的ThirdAccountInfo
     * 
     * @param userInfo
     * @return
     */
    public static ThirdAccountInfo wxUserConvertToThirdAcct(UserInfo userInfo) {
        ThirdAccountInfo wxAcct = new ThirdAccountInfo();
        wxAcct.setOpenId(userInfo.getOpenid());
        wxAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        wxAcct.setNickName(userInfo.getNickname());
        wxAcct.setGender(userInfo.getSex());
        wxAcct.setHeadingUrl(userInfo.getHeadimgurl());
        wxAcct.setProvince(userInfo.getProvince());
        wxAcct.setCity(userInfo.getCity());
        wxAcct.setStatus("Y");
        wxAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        wxAcct.setRemark("来自微信的用户");
        wxAcct.setLanguage(userInfo.getLanguage());
        wxAcct.setUnionid(userInfo.getUnionid());

        // 存储个性化信息(已删2014-03-27)
        return wxAcct;
    }

    /**
     * 将新浪微博提供的第三方用户信息统一转换成我们系统的ThirdAccountInfo
     * 
     * @param user
     * @return
     */
    public static ThirdAccountInfo sinaUserConvertToThirdAcct(User user) {
        ThirdAccountInfo sinaAcct = new ThirdAccountInfo();
        sinaAcct.setOpenId(user.getId());
        sinaAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
        sinaAcct.setGender(user.getGender());
        sinaAcct.setHeadingUrl(user.getProfileImageUrl());
        sinaAcct.setNickName(user.getScreenName());
        sinaAcct.setStatus("Y");
        sinaAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sinaAcct.setLanguage(user.getLang());
        sinaAcct.setRemark("来自新浪微博的用户");
        sinaAcct.setProvince(String.valueOf(user.getProvince()));
        sinaAcct.setCity(String.valueOf(user.getCity()));
        sinaAcct.setNowAddress(user.getLocation());
        // 存储个性化信息(已删2014-03-27)

        return sinaAcct;
    }

    /**
     * 将新浪微博提供的第三方用户信息统一转换成我们系统的ThirdAccountInfo
     * 
     * @param user
     * @return
     */
    public static ThirdAccountInfo sinaUserConvertToThirdAcct(JSONObject jsonParams) {
        ThirdAccountInfo sinaAcct = new ThirdAccountInfo();
        sinaAcct.setOpenId(jsonParams.getString("openid"));
        sinaAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
        sinaAcct.setGender(jsonParams.getString("sex"));
        sinaAcct.setHeadingUrl(jsonParams.getString("avatar"));
        sinaAcct.setNickName(jsonParams.getString("nickname"));
        sinaAcct.setStatus("Y");
        sinaAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sinaAcct.setLanguage(jsonParams.getString("language"));
        sinaAcct.setRemark("来自新浪微博的用户");
        sinaAcct.setProvince(String.valueOf(jsonParams.getString("province")));
        sinaAcct.setCity(jsonParams.getString("city"));
        sinaAcct.setNowAddress(jsonParams.getString("location"));
        // 存储个性化信息(已删2014-03-27)

        return sinaAcct;
    }

    /**
     * 将支付宝返回的用户信息转换成我们系统的ThirdAccountInfo
     * 
     * @param user
     * @return
     */
    public static ThirdAccountInfo alipayUserConvertToThirdAcct(AlipayUserDetail user) {
        ThirdAccountInfo alipayAcct = new ThirdAccountInfo();
        alipayAcct.setOpenId(user.getAlipayUserId());
        alipayAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
        alipayAcct.setNickName(user.getRealName());
        alipayAcct.setGender(user.getSex());
        alipayAcct.setStatus("Y");
        alipayAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        alipayAcct.setRemark("来自支付宝的用户");
        return alipayAcct;
    }

    /**
     * 将淘宝用户信息转化成我们系统的ThirdAccountInfo
     * 
     * @param user
     * @return
     */
    public static ThirdAccountInfo taobaoUserConvertToThirdAcct(com.taobao.api.domain.User user) {
        ThirdAccountInfo taobaoAcct = new ThirdAccountInfo();
        taobaoAcct.setOpenId(user.getUid());
        taobaoAcct.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
        taobaoAcct.setNickName(user.getNick());
        taobaoAcct.setGender(user.getSex());
        taobaoAcct.setHeadingUrl(user.getAvatar());
        taobaoAcct.setProvince("");
        taobaoAcct.setCity("");
        taobaoAcct.setStatus("Y");
        taobaoAcct.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        taobaoAcct.setRemark("来自淘宝的用户");
        taobaoAcct.setLanguage("");
        return taobaoAcct;
    }

}
