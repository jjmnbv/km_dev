package com.pltfm.cms.vobject;

import java.util.Date;
import java.util.List;

public class LotteryLuckDraw {
    /**
     * 开始索引值
     */
    private Integer startIndex;
    /**
     * 结束索引值
     */
    private Integer endIndex;
    private Integer windowId;// 窗口ID
    private Integer flag;// 标志
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.LUCK_DRAW_ID
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Integer luckDrawId;
    private List luckDrawIds;

    public List getLuckDrawIds() {
        return luckDrawIds;
    }

    public void setLuckDrawIds(List luckDrawIds) {
        this.luckDrawIds = luckDrawIds;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.ACTIVE_TYPE
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Short activeType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.CHANNEL_ID
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Integer channelId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.TITEL
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private String titel;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.STARTTIME
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Date starttime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.ENDTIME
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Date endtime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.INTRODUCTION
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private String introduction;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.DETAILED_RULES
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private String detailedRules;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.STATUS
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Short status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.CREATE_DATE
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.CREATED_ID
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Integer createdId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.MODIFY_DATE
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column LOTTERY_LUCK_DRAW.MODIFIED_ID
     *
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    private Integer modifiedId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.LUCK_DRAW_ID
     *
     * @return the value of LOTTERY_LUCK_DRAW.LUCK_DRAW_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Integer getLuckDrawId() {
        return luckDrawId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.LUCK_DRAW_ID
     *
     * @param luckDrawId the value for LOTTERY_LUCK_DRAW.LUCK_DRAW_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setLuckDrawId(Integer luckDrawId) {
        this.luckDrawId = luckDrawId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.ACTIVE_TYPE
     *
     * @return the value of LOTTERY_LUCK_DRAW.ACTIVE_TYPE
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Short getActiveType() {
        return activeType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.ACTIVE_TYPE
     *
     * @param activeType the value for LOTTERY_LUCK_DRAW.ACTIVE_TYPE
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setActiveType(Short activeType) {
        this.activeType = activeType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.CHANNEL_ID
     *
     * @return the value of LOTTERY_LUCK_DRAW.CHANNEL_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Integer getChannelId() {
        return channelId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.CHANNEL_ID
     *
     * @param channelId the value for LOTTERY_LUCK_DRAW.CHANNEL_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.TITEL
     *
     * @return the value of LOTTERY_LUCK_DRAW.TITEL
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public String getTitel() {
        return titel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.TITEL
     *
     * @param titel the value for LOTTERY_LUCK_DRAW.TITEL
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.STARTTIME
     *
     * @return the value of LOTTERY_LUCK_DRAW.STARTTIME
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.STARTTIME
     *
     * @param starttime the value for LOTTERY_LUCK_DRAW.STARTTIME
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.ENDTIME
     *
     * @return the value of LOTTERY_LUCK_DRAW.ENDTIME
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.ENDTIME
     *
     * @param endtime the value for LOTTERY_LUCK_DRAW.ENDTIME
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.INTRODUCTION
     *
     * @return the value of LOTTERY_LUCK_DRAW.INTRODUCTION
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public String getIntroduction() {
        return introduction;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.INTRODUCTION
     *
     * @param introduction the value for LOTTERY_LUCK_DRAW.INTRODUCTION
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.DETAILED_RULES
     *
     * @return the value of LOTTERY_LUCK_DRAW.DETAILED_RULES
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public String getDetailedRules() {
        return detailedRules;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.DETAILED_RULES
     *
     * @param detailedRules the value for LOTTERY_LUCK_DRAW.DETAILED_RULES
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setDetailedRules(String detailedRules) {
        this.detailedRules = detailedRules;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.STATUS
     *
     * @return the value of LOTTERY_LUCK_DRAW.STATUS
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.STATUS
     *
     * @param status the value for LOTTERY_LUCK_DRAW.STATUS
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.CREATE_DATE
     *
     * @return the value of LOTTERY_LUCK_DRAW.CREATE_DATE
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.CREATE_DATE
     *
     * @param createDate the value for LOTTERY_LUCK_DRAW.CREATE_DATE
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.CREATED_ID
     *
     * @return the value of LOTTERY_LUCK_DRAW.CREATED_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Integer getCreatedId() {
        return createdId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.CREATED_ID
     *
     * @param createdId the value for LOTTERY_LUCK_DRAW.CREATED_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.MODIFY_DATE
     *
     * @return the value of LOTTERY_LUCK_DRAW.MODIFY_DATE
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.MODIFY_DATE
     *
     * @param modifyDate the value for LOTTERY_LUCK_DRAW.MODIFY_DATE
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column LOTTERY_LUCK_DRAW.MODIFIED_ID
     *
     * @return the value of LOTTERY_LUCK_DRAW.MODIFIED_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public Integer getModifiedId() {
        return modifiedId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column LOTTERY_LUCK_DRAW.MODIFIED_ID
     *
     * @param modifiedId the value for LOTTERY_LUCK_DRAW.MODIFIED_ID
     * @ibatorgenerated Tue Sep 09 11:27:05 CST 2014
     */
    public void setModifiedId(Integer modifiedId) {
        this.modifiedId = modifiedId;
    }
}