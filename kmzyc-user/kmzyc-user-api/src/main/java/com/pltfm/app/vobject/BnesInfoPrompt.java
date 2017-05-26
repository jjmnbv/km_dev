package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息作息提示
 * 
 */
public class BnesInfoPrompt implements Serializable {
    /**
     * 安全信息提示ID
     * 
     */
    private Integer infoPromptId;
    /**
     * 登录ID
     * 
     */
    private String loginId;

    /**
     * 内容
     * 
     */
    private String content;

    /**
     * 创建日期
     * 
     */
    private Date createDate;

    /**
     * 消息类型
     * 
     */
    private Integer type;
    /**
     * 发布状态
     * 
     */
    private Integer status;

    /**
     * 修改人
     * 
     */
    private Integer modified;

    /**
     * 修改日期
     * 
     */
    private Date modifiedDate;

    /**
     * 标题
     * 
     */
    private String title;

    /**
     * 是否定时发布
     * 
     */
    private Integer isTime;
    /**
     * 
     * 发布日期
     */
    private Date releaseDate;

    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;
    // 创建人

    private Integer createId;

    // 客户类别
    private Integer customerType;

    // 发布类型
    private Integer releaseObject;

    // 消息发送平台(1：b2b；2：供应商；2：微商)
    private Integer messagePlatform;



    public Integer getMessagePlatform() {
        return messagePlatform;
    }

    public void setMessagePlatform(Integer messagePlatform) {
        this.messagePlatform = messagePlatform;
    }

    public Integer getInfoPromptId() {
        return infoPromptId;
    }

    public void setInfoPromptId(Integer infoPromptId) {
        this.infoPromptId = infoPromptId;
    }



    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
    }



    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsTime() {
        return isTime;
    }

    public void setIsTime(Integer isTime) {
        this.isTime = isTime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReleaseObject() {
        return releaseObject;
    }

    public void setReleaseObject(Integer releaseObject) {
        this.releaseObject = releaseObject;
    }



}
