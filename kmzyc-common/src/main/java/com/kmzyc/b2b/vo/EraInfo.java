package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 时代会员信息
 * 
 * @author Administrator
 * 
 */
public class EraInfo implements Serializable {

  private static final long serialVersionUID = -1175770299811870327L;

  private BigDecimal eraInfoId;

  private Long nLoginId;

  private Long eraId;

  private String eraNo;

  private String loginAccount;

  private String contactInformation;

  private BigDecimal expIntegralValue; // 当前体验积分值

  private Long eraGradeId;

  private String eraGradeName;

  private BigDecimal eraGradeRate; // 时代等级折扣率百分比

  private String recommendedNo;

  private String sex;

  private Date birthday;

  private String name;

  private String nickname;

  private String certificateNumber;

  private Date dCreateDate;

  private Date dModifyDate;

  private String email;

  private String mobile;
  // 验证邮箱
  private String aiEmail;
  // 个人信息头像
  private String headSculpture;
  // km账户名
  private String kmLoginAccount;
  
  private BigDecimal personalId;
  
  // 注册平台
  private Integer register_Platfrom;
  // 注册设备
  private Integer register_Device;

  //登录密码加密串
  private String loginSalt;
  //支付密码加密串
  private String paySalt;

  private String loginPassword; //登录密码

  private Integer endIndex;

  private Integer startIndex;
  private BigDecimal loginId;


  private Date createDate;

  private Date modifyDate;

  private String papertype;



  /* 新增查询条件：创建时间起始 */
  private Date createStartDate;
  /* 新增查询条件：创建时间结束 */
  private Date createEndDate;
  
  /*
   * 时代用户信息增量推送原因
   * Operation = 10, 代表该用户属于新增的用户 
   * Operation = 20, 代表该用户已冻结或已注销，接收到此类信息可以直接删除该用户
   * Operation = 30, 代表该用户的信息发生修改
 */
  private Integer operation;
 

public Integer getOperation() {
    return operation;
}

public void setOperation(Integer operation) {
    this.operation = operation;
}

public Integer getRegister_Platfrom() {
    return register_Platfrom;
}

public void setRegister_Platfrom(Integer register_Platfrom) {
    this.register_Platfrom = register_Platfrom;
}

public Integer getRegister_Device() {
    return register_Device;
}

public void setRegister_Device(Integer register_Device) {
    this.register_Device = register_Device;
}


  
  public BigDecimal getPersonalId() {
      return personalId;
  }

  public void setPersonalId(BigDecimal personalId) {
      this.personalId = personalId;
  }

  public Integer getEndIndex() {
      return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
      this.endIndex = endIndex;
  }

  public Integer getStartIndex() {
      return startIndex;
  }

  public void setStartIndex(Integer startIndex) {
      this.startIndex = startIndex;
  }

  public BigDecimal getLoginId() {
      return loginId;
  }

  public void setLoginId(BigDecimal loginId) {
      this.loginId = loginId;
  }

  public Date getCreateDate() {
      return createDate;
  }

  public void setCreateDate(Date createDate) {
      this.createDate = createDate;
  }

  public Date getModifyDate() {
      return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
      this.modifyDate = modifyDate;
  }

  public String getPapertype() {
      return papertype;
  }

  public void setPapertype(String papertype) {
      this.papertype = papertype;
  }

  public Date getCreateStartDate() {
      return createStartDate;
  }

  public void setCreateStartDate(Date createStartDate) {
      this.createStartDate = createStartDate;
  }

  public Date getCreateEndDate() {
      return createEndDate;
  }

  public void setCreateEndDate(Date createEndDate) {
      this.createEndDate = createEndDate;
  }

  public BigDecimal getEraInfoId() {
    return eraInfoId;
  }

  public void setEraInfoId(BigDecimal eraInfoId) {
    this.eraInfoId = eraInfoId;
  }

  public Long getnLoginId() {
    return nLoginId;
  }

  public void setnLoginId(Long nLoginId) {
    this.nLoginId = nLoginId;
  }

  public Long getEraId() {
    return eraId;
  }

  public void setEraId(Long eraId) {
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

  public BigDecimal getExpIntegralValue() {
    return expIntegralValue;
  }

  public void setExpIntegralValue(BigDecimal expIntegralValue) {
    this.expIntegralValue = expIntegralValue;
  }

  public Long getEraGradeId() {
    return eraGradeId;
  }

  public void setEraGradeId(Long eraGradeId) {
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
    return birthday;
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

  public Date getdCreateDate() {
    return dCreateDate;
  }

  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  public Date getdModifyDate() {
    return dModifyDate;
  }

  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
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

  public String getAiEmail() {
    return aiEmail;
  }

  public void setAiEmail(String aiEmail) {
    this.aiEmail = aiEmail;
  }

  public String getHeadSculpture() {
    return headSculpture;
  }

  public void setHeadSculpture(String headSculpture) {
    this.headSculpture = headSculpture;
  }

  public String getKmLoginAccount() {
    return kmLoginAccount;
  }

  public void setKmLoginAccount(String kmLoginAccount) {
    this.kmLoginAccount = kmLoginAccount;
  }

  public String getLoginSalt() {
    return loginSalt;
  }

  public void setLoginSalt(String loginSalt) {
    this.loginSalt = loginSalt;
  }

  public String getPaySalt() {
    return paySalt;
  }

  public void setPaySalt(String paySalt) {
    this.paySalt = paySalt;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
  }

  @Override
  public String toString() {
    return "EraInfo [eraInfoId=" + eraInfoId + ", nLoginId=" + nLoginId + ", eraId=" + eraId
        + ", eraNo=" + eraNo + ", loginAccount=" + loginAccount + ", contactInformation="
        + contactInformation + ", expIntegralValue=" + expIntegralValue + ", eraGradeId="
        + eraGradeId + ", eraGradeName=" + eraGradeName + ", eraGradeRate=" + eraGradeRate
        + ", recommendedNo=" + recommendedNo + ", sex=" + sex + ", birthday=" + birthday
        + ", name=" + name + ", nickname=" + nickname + ", certificateNumber=" + certificateNumber
        + ", dCreateDate=" + dCreateDate + ", dModifyDate=" + dModifyDate + ", email=" + email
        + ", mobile=" + mobile + ", aiEmail=" + aiEmail + ", headSculpture=" + headSculpture
        + ", kmLoginAccount=" + kmLoginAccount + ",loginPassword="+loginPassword+"]";
  }



}
