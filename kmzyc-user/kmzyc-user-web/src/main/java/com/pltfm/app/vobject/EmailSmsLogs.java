package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * 推广短信邮件日志表
 * 
 * @author luotao
 *
 */
public class EmailSmsLogs implements Serializable {
    private static final long serialVersionUID = 137404452597886400L;
    private Integer selogId; // 主键ID
    private Integer promotionId;// 推广短信邮件信息ID
    private Date sendDate;// 发送日期
    private Integer status;// 发送状态0---失败 1----发送成功 2--发送异常
    private Date createdDate;// 创建日期
    private String title;// 推广标题
    private Integer promotionType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    private String loginName; // 登录帐号
    private Integer createdId;// 创建人ID
    private Date modifyDate;// 修改日期
    private Integer modifyId;// 修改人ID
    private Integer loginId;// 登录账号ID
    private String mobile;// 发送手机号码
    private String email;// 发送邮件地址
    private String mark; // 备注

    private Integer channelId;// 1--b2b

    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;

    public Integer getSelogId() {
        return selogId;
    }

    public void setSelogId(Integer selogId) {
        this.selogId = selogId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }



}
