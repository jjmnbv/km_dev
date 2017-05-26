package com.kmzyc.b2b.third.model;

import java.io.Serializable;

/**
 * 第三方账号和康美中药城账号绑定信息表
 * 
 * @author Administrator 2014-03-17
 * 
 */
public class ThirdBindInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 第三方账号的openid
     */
    private String openId;
    /**
     * 第三方账号的类型
     */
    private String thirdAccountType;
    /**
     * 康美中药城会员的登录id
     */
    private String nLoginId;
    /**
     * 绑定类型 (01.和正式会员绑定 02.和临时会员绑定)
     */
    private String bindType;
    /**
     * 是否绑定 Y:绑定 N:不绑定
     */
    private String isBind;
    /**
     * 最后一次更新时间
     */
    private String lastUpdateTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 备用字段,可用来临时存储一些有用的列
     */
    private String extends1;
    private String extends2;

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

    public String getnLoginId() {
        return nLoginId;
    }

    public void setnLoginId(String nLoginId) {
        this.nLoginId = nLoginId;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getIsBind() {
        return isBind;
    }

    public void setIsBind(String isBind) {
        this.isBind = isBind;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "[open_id=" + this.openId + ",  accountType=" + this.thirdAccountType
                + ",  n_login_id=" + this.nLoginId + ",  bind_type=" + this.bindType + ",  isBind="
                + this.isBind + ",  last_update_time=" + this.lastUpdateTime + "]";

    }

    public String getExtends1() {
        return extends1;
    }

    public void setExtends1(String extends1) {
        this.extends1 = extends1;
    }

    public String getExtends2() {
        return extends2;
    }

    public void setExtends2(String extends2) {
        this.extends2 = extends2;
    }

}
