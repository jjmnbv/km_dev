package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class BnesAcctAppealInfo implements Serializable {

    /**
   * 
   */
    private static final long serialVersionUID = -2249712890699264288L;


    private Integer accountAppealId;


    private Integer accountId;
    // 申诉标题
    private String appealTitle;
    //
    private String accountName; // 账户名
    // 申诉内容
    private String appealContent;

    // 处理意见
    private String appealSuggestion;



    // 登录ID
    private Integer nLoginId;
    // 投诉类型：1---产品相关2---价格相关3--物流相关4--服务相关
    private Integer type;
    // 相关订单
    private String order_Desc;
    // 邮件地址
    private String email;
    // 手机号码
    private String mobile;


    // 申诉日期
    private Date appealCreateDate;

    // 创建日期
    private Date createDate;

    // 创建ID
    private Integer createdId;

    // 修改日期
    private Date modifyDate;

    // 修改ID
    private Integer modifieId;
    private Integer skip;
    private Integer max;


    public Integer getAccountAppealId() {
        return accountAppealId;
    }


    public void setAccountAppealId(Integer accountAppealId) {
        this.accountAppealId = accountAppealId;
    }


    public Integer getAccountId() {
        return accountId;
    }


    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }


    public String getAppealTitle() {
        return appealTitle;
    }


    public void setAppealTitle(String appealTitle) {
        this.appealTitle = appealTitle;
    }


    public String getAppealContent() {
        return appealContent;
    }


    public void setAppealContent(String appealContent) {
        this.appealContent = appealContent;
    }


    public Date getAppealCreateDate() {
        return appealCreateDate;
    }


    public void setAppealCreateDate(Date appealCreateDate) {
        this.appealCreateDate = appealCreateDate;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Integer getCreatedId() {
        return createdId;
    }

    public String getAccountName() {
        return accountName;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }


    public Date getModifyDate() {
        return modifyDate;
    }


    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }


    public Integer getModifieId() {
        return modifieId;
    }


    public void setModifieId(Integer modifieId) {
        this.modifieId = modifieId;
    }


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

    public Integer getnLoginId() {
        return nLoginId;
    }


    public void setnLoginId(Integer nLoginId) {
        this.nLoginId = nLoginId;
    }


    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }


    public String getOrder_Desc() {
        return order_Desc;
    }


    public void setOrder_Desc(String order_Desc) {
        this.order_Desc = order_Desc;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getAppealSuggestion() {
        return appealSuggestion;
    }


    public void setAppealSuggestion(String appealSuggestion) {
        this.appealSuggestion = appealSuggestion;
    }

}
