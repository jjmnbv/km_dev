package com.kmzyc.b2b.third.model;

import java.io.Serializable;

import com.kmzyc.b2b.third.util.ThirdConstant;

/**
 * 第三方账号信息表
 * 
 * @author Administrator 2014-03-17 第三方账号的个性化信息于20140327已删除存储
 */
public class ThirdAccountInfo implements Serializable {

    /**
     * 序列版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 第三方账号提供的openId,可能是数字字符串,也有可能是加密后的字符串
     */
    private String openId;
    /**
     * 第三方账号的类型 01:QQ 02:微信 03:新浪微博 04:腾讯微博
     */
    private String thirdAccountType;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户性别, 我们表中存储的值为 1:男 2:女
     */
    private String gender;
    /**
     * 头像链接地址
     */
    private String headingUrl;
    /**
     * 通过调用第三方接口返回用户资料中所填的省份信息
     */
    private String province;
    /**
     * 城市信息
     */
    private String city;
    /**
     * 现居住地
     */
    private String nowAddress;
    /**
     * 所使用的语言
     */
    private String language;
    /**
     * 用户状态,默认为启用 Y:启用 N:不启用
     */
    private String status;
    /**
     * 此用户记录到third_account_info中的创建时间
     */
    private String createTime;
    /**
     * 备注信息
     */
    private String remark;

    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;

    private String unionid;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getThirdAccountType() {
        return thirdAccountType;
    }

    public void setThirdAccountType(String thirdAccountType) {
        this.thirdAccountType = thirdAccountType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    /**
     * 第三方信息返回的gender都不同,统一在表中生成1为男,2为女
     * 
     * @param gender
     */
    public void setGender(String gender) {
        String defaultGendar = ThirdConstant.THIRD_GENDER_MALE;
        if ("f".equals(gender) || "2".equals(gender) || "女".equals(gender)) {
            defaultGendar = ThirdConstant.THIRD_GENDER_FEMALE;
        }

        this.gender = defaultGendar;
    }

    public String getHeadingUrl() {
        return headingUrl;
    }

    public void setHeadingUrl(String headingUrl) {
        this.headingUrl = headingUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }


}
