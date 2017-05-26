package com.pltfm.app.vobject;


import java.util.Date;

public class BnesAcctAppealInfoQry {

    private Integer accountAppealId;


    private Integer accountId;

    private String accountName;

    private String appealTitle;


    private String appealContent;


    private Date appealCreateDate;


    private Date createDate;


    private Integer createdId;


    private Date modifyDate;


    private Integer modifieId;
    private Integer skip;
    private Integer max;
    private Integer type;

    // 处理状态
    private Integer dealStatus;

    // 相关订单
    private String order_Desc;
    private String appealSuggestion;
    private Date disposeDate;
    private Integer disposePersonId;
    private String disposePersonName;
    // 创建日期开始
    private Date createDateBefore;
    // 创建日期结束
    private Date createDateEnd;



    public Date getCreateDateBefore() {
        return createDateBefore;
    }


    public void setCreateDateBefore(Date createDateBefore) {
        this.createDateBefore = createDateBefore;
    }


    public Date getCreateDateEnd() {
        return createDateEnd;
    }


    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }


    public String getOrder_Desc() {
        return order_Desc;
    }


    public void setOrder_Desc(String order_Desc) {
        this.order_Desc = order_Desc;
    }


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


    public String getAppealSuggestion() {
        return appealSuggestion;
    }


    public void setAppealSuggestion(String appealSuggestion) {
        this.appealSuggestion = appealSuggestion;
    }


    public Date getDisposeDate() {
        return disposeDate;
    }


    public void setDisposeDate(Date disposeDate) {
        this.disposeDate = disposeDate;
    }


    public Integer getDisposePersonId() {
        return disposePersonId;
    }


    public void setDisposePersonId(Integer disposePersonId) {
        this.disposePersonId = disposePersonId;
    }


    public String getAccountName() {
        return accountName;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public String getDisposePersonName() {
        return disposePersonName;
    }


    public void setDisposePersonName(String disposePersonName) {
        this.disposePersonName = disposePersonName;
    }

    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getDealStatus() {
        return dealStatus;
    }


    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

}
