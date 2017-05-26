package com.pltfm.shiro.vobject;


import java.util.Date;

public class User implements java.io.Serializable {
    private static final long serialVersionUID = 105212819174135389L;
    private Long loginId;
    private Integer levelId;
    private Integer customerTypeId;
    private String loginAccount;
    private String loginPassword;
    private String mobile;
    private String email;
    private Integer status;
    private Date createDate;
    private Long created;
    private String modifyDate;
    private Integer modified;
    private String nickName;


    public Long getLoginId() {
        return loginId;
    }


    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }


    public Integer getLevelId() {
        return levelId;
    }


    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }


    public Integer getCustomerTypeId() {
        return customerTypeId;
    }


    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }


    public String getLoginAccount() {
        return loginAccount;
    }


    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }


    public String getLoginPassword() {
        return loginPassword;
    }


    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
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


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Long getCreated() {
        return created;
    }


    public void setCreated(Long created) {
        this.created = created;
    }


    public String getModifyDate() {
        return modifyDate;
    }


    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }


    public Integer getModified() {
        return modified;
    }


    public void setModified(Integer modified) {
        this.modified = modified;
    }


    @Override
    public String toString() {
        return "User [loginId=" + loginId + ", levelId=" + levelId
                + ", customerTypeId=" + customerTypeId + ", loginAccount="
                + loginAccount + ", loginPassword=" + loginPassword
                + ", mobile=" + mobile + ", email=" + email + ", status="
                + status + ", createDate=" + createDate + ", created="
                + created + ", modifyDate=" + modifyDate + ", modified="
                + modified + "]";
    }


    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


}
