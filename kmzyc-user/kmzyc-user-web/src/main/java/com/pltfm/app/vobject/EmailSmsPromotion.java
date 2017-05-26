package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 推广短信实体
 * 
 * @author luotao
 *
 */


public class EmailSmsPromotion implements Serializable {
    private static final long serialVersionUID = 137404452597886400L;
    private Integer promotionId;// 主键ID
    private String title; // 推广标题
    private String loginId;
    private Integer status;// 审批状态0---草稿 1---待审核2--审核拒绝3---审核通过 4---发布
    private Date CreateDate;// 创建日期
    private Integer createId;// 创建用户ID
    private Date modifyDate;// 修改日期
    private Integer modifyId;// 修改用户ID
    private String emailContext;// 发送邮件内容
    private String smsText;// 短信内容文本
    private String promoteContent;// 推广内容（邮件或者短信）
    private Integer promotionType;// 推广形式1---短信 2---邮件
    private Integer publicType;// 发布对象1---默认全部 2---指定具体客户
    private Integer isTime;// 是否定时发送0---否 1---是
    private Date timingDate;// 定时发布日期
    private Date timingDateStart;// 定时发布日期Start
    private Date timingDateEnd;// 定时发布日期End
    private Integer templetId;// 邮件模板ID
    private Integer smsType; // 短信接口类型 1---验证2-----通知3----广告

    private Integer channelId; // 1--b2b
    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    public String getEmailContext() {
        return emailContext;
    }

    public void setEmailContext(String emailContext) {
        this.emailContext = emailContext;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getPublicType() {
        return publicType;
    }

    public void setPublicType(Integer publicType) {
        this.publicType = publicType;
    }

    public Integer getIsTime() {
        return isTime;
    }

    public void setIsTime(Integer isTime) {
        this.isTime = isTime;
    }

    public Date getTimingDate() {
        return timingDate;
    }

    public void setTimingDate(Date timingDate) {
        this.timingDate = timingDate;
    }

    public Integer getTempletId() {
        return templetId;
    }

    public void setTempletId(Integer templetId) {
        this.templetId = templetId;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getPromoteContent() {
        return promoteContent;
    }

    public void setPromoteContent(String promoteContent) {
        this.promoteContent = promoteContent;
    }

    public Date getTimingDateStart() {
        return timingDateStart;
    }

    public void setTimingDateStart(Date timingDateStart) {
        this.timingDateStart = timingDateStart;
    }

    public Date getTimingDateEnd() {
        return timingDateEnd;
    }

    public void setTimingDateEnd(Date timingDateEnd) {
        this.timingDateEnd = timingDateEnd;
    }


}
