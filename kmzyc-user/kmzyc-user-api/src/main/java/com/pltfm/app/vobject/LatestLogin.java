package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 最近登录类
 * 
 * @author cjm
 * @since 2013-7-24
 */
public class LatestLogin implements Serializable {
    /**
     * 最近登录编号
     */
    private Integer n_Id;

    /**
     * 登录ID
     */
    private Integer n_LoginId;

    /**
     * 登录时间
     */
    private Date d_Date;

    /**
     * 登录时间start
     */
    private Date d_DateStart;

    /**
     * 登录时间end
     */
    private Date d_DateEnd;

    /**
     * IP地址
     */
    private String loginIp;

    /**
     * 登录模块
     */
    private String loginModule;

    /**
     * 登录账号
     */
    private String loginAccount;

    // ------------ for page
    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }


    public Integer getN_Id() {
        return n_Id;
    }

    public void setN_Id(Integer nId) {
        n_Id = nId;
    }

    public Integer getN_LoginId() {
        return n_LoginId;
    }

    public void setN_LoginId(Integer nLoginId) {
        n_LoginId = nLoginId;
    }

    public Date getD_Date() {
        return d_Date;
    }

    public void setD_Date(Date dDate) {
        d_Date = dDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginModule() {
        return loginModule;
    }

    public void setLoginModule(String loginModule) {
        this.loginModule = loginModule;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public Date getD_DateStart() {
        return d_DateStart;
    }

    public void setD_DateStart(Date d_DateStart) {
        this.d_DateStart = d_DateStart;
    }

    public Date getD_DateEnd() {
        return d_DateEnd;
    }

    public void setD_DateEnd(Date d_DateEnd) {
        this.d_DateEnd = d_DateEnd;
    }

}
