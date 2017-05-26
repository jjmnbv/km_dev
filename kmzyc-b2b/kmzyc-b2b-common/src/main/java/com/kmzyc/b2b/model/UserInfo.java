package com.kmzyc.b2b.model;

import java.util.Date;

public class UserInfo {
  /** 登录id */
  private Integer loginId;
  /** 个人信息id */
  private Integer personalId;
  /** 个性化信息id */
  private Integer personalityId;
  /** 健康信息id */
  private Integer healthYgenericId;
  /** 登录名 */
  private String accountLogin;
  /** 邮箱 */
  private String email;
  /** 真实姓名 */
  private String name;
  /** 性别 */
  private String sex;
  /** 生日 */
  private Date birthday;
  /** 省 */
  private String province;
  /** 市 */
  private String city;
  /** 区 */
  private String area;
  /** 详细地址 */
  private String detailedaddress;
  /** 居住状态 */
  private Integer liveStatus;
  /** 职业身份 */
  private String professionType;
  /** 婚姻状态 */
  private Integer maritalStatus;
  /** 手机号 */
  private String mobile;
  /** 昵称 */
  private String nickName;
  /** 头像 */
  private String headSculpture;

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public Integer getPersonalId() {
    return personalId;
  }

  public void setPersonalId(Integer personalId) {
    this.personalId = personalId;
  }

  public Integer getPersonalityId() {
    return personalityId;
  }

  public void setPersonalityId(Integer personalityId) {
    this.personalityId = personalityId;
  }

  public Integer getHealthYgenericId() {
    return healthYgenericId;
  }

  public void setHealthYgenericId(Integer healthYgenericId) {
    this.healthYgenericId = healthYgenericId;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getDetailedaddress() {
    return detailedaddress;
  }

  public void setDetailedaddress(String detailedaddress) {
    this.detailedaddress = detailedaddress;
  }

  public Integer getLiveStatus() {
    return liveStatus;
  }

  public void setLiveStatus(Integer liveStatus) {
    this.liveStatus = liveStatus;
  }

  public String getProfessionType() {
    return professionType;
  }

  public void setProfessionType(String professionType) {
    this.professionType = professionType;
  }

  public Integer getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(Integer maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getHeadSculpture() {
    return headSculpture;
  }

  public void setHeadSculpture(String headSculpture) {
    this.headSculpture = headSculpture;
  }

  @Override
  public String toString() {
    return "UserInfo [loginId=" + loginId + ", personalId=" + personalId + ", personalityId="
        + personalityId + ", healthYgenericId=" + healthYgenericId + ", accountLogin="
        + accountLogin + ", email=" + email + ", name=" + name + ", sex=" + sex + ", birthday="
        + birthday + ", province=" + province + ", city=" + city + ", area=" + area
        + ", detailedaddress=" + detailedaddress + ", liveStatus=" + liveStatus
        + ", professionType=" + professionType + ", maritalStatus=" + maritalStatus + ", mobile="
        + mobile + ", nickName=" + nickName + ", headSculpture=" + headSculpture + "]";
  }

}
