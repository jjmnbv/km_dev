package com.kmzyc.supplier.model;

import com.kmzyc.supplier.model.AccountInfo;

import java.math.BigDecimal;
import java.util.Date;

public class EraInfo {

    private static final long serialVersionUID = -7460720891118955965L;

    private Integer eraInfoId;

    private AccountInfo accountInfo;//保存用户信息

    private String mobile;

    private String email;

    private Integer nLoginId;

    private Integer eraId;

    private String eraNo;

    private String loginAccount;

    private String contactInformation;

    private Integer expIntegralValue;

    private Integer eraGradeId;

    private String eraGradeName;

    private BigDecimal eraGradeRate;

    private String recommendedNo;

    private String sex;

    private Date birthday;

    private String name;

    private String nickname;

    private String certificateNumber;

    private Date createDate;

    private Date modifyDate;

    private Integer personalId;

    private String papertype;

    public Integer getEraInfoId() {
        return eraInfoId;
    }

    public void setEraInfoId(Integer eraInfoId) {
        this.eraInfoId = eraInfoId;
    }

    public Integer getnLoginId() {
        return nLoginId;
    }

    public void setnLoginId(Integer nLoginId) {
        this.nLoginId = nLoginId;
    }

    public Integer getEraId() {
        return eraId;
    }

    public void setEraId(Integer eraId) {
        this.eraId = eraId;
    }

    public String getEraNo() {
        return eraNo;
    }

    public void setEraNo(String eraNo) {
        this.eraNo = eraNo;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Integer getExpIntegralValue() {
        return expIntegralValue;
    }

    public void setExpIntegralValue(Integer expIntegralValue) {
        this.expIntegralValue = expIntegralValue;
    }

    public Integer getEraGradeId() {
        return eraGradeId;
    }

    public void setEraGradeId(Integer eraGradeId) {
        this.eraGradeId = eraGradeId;
    }

    public String getEraGradeName() {
        return eraGradeName;
    }

    public void setEraGradeName(String eraGradeName) {
        this.eraGradeName = eraGradeName;
    }

    public BigDecimal getEraGradeRate() {
        return eraGradeRate;
    }

    public void setEraGradeRate(BigDecimal eraGradeRate) {
        this.eraGradeRate = eraGradeRate;
    }

    public String getRecommendedNo() {
        return recommendedNo;
    }

    public void setRecommendedNo(String recommendedNo) {
        this.recommendedNo = recommendedNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        Date temp = birthday;
        return temp;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public String getPapertype() {
        return papertype;
    }

    public void setPapertype(String papertype) {
        this.papertype = papertype;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
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

    public Date getModifyDate() {
        Date temp = modifyDate;
        return temp;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Date getCreateDate() {
        Date temp = createDate;
        return temp;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}