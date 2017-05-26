package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户基本信息数据访问类
 * 
 * @author zhl
 * @since 2013-07-25
 */
public class UserInfoDO implements Serializable {
  /** 客户登录主键 **/
  private Integer loginId;
  /** 客户类别主键 **/
  private Integer customerTypeId;
  /** 客户类子类别主键 **/
  private Integer sonCustomerId;
  /** 客户登录账号 **/
  private String loginAccount;
  /** 客户真实姓名 **/
  private String name;
  /** 客户类别名称 **/
  private String customerTypeName;
  /** 电子邮箱 **/
  private String email;
  /** 客户联系方式 **/
  private String mobile;
  /** 客户登录账号状态 **/
  private Integer status;
  /** 客户等级主键 **/
  private Integer levelId;
  /** 分页开始索引值 **/
  private Integer startIndex;
  /** 分页结束索引值 **/
  private Integer endIndex;
  /**
   * 登录密码
   */
  private String loginPassword;
  /** 个人信息id */
  private Integer personalId;
  /** 个性化信息id */
  private Integer personalityId;
  /** 健康信息id */
  private Integer healthYgenericId;
  /** 登录名 */
  private String accountLogin;
  /** 真实姓名 */
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
  /** 昵称 */
  private String nickName;
  /** 头像 */
  private String headSculpture;
  /** 卡号 */
  private String cardNum;
  /** 创建时间 */
  private Date d_CreateDate;
  /**
   * 可用积分
   */
  private BigDecimal n_AvailableIntegral;
  /**
   * 总积分
   */
  private BigDecimal n_totalIntegral;
  /**
   * 积分明细描述
   */
  private String discribe;

  /**
   * 积分明细类型，积分积累和积分消费,1为积分增加0为积分减少
   */
  private Integer scoreType;

  /**
   * 变动积分数
   */
  private BigDecimal scoreNumber;



  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCustomerTypeName() {
    return customerTypeName;
  }

  public void setCustomerTypeName(String customerTypeName) {
    this.customerTypeName = customerTypeName;
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

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getSonCustomerId() {
    return sonCustomerId;
  }

  public void setSonCustomerId(Integer sonCustomerId) {
    this.sonCustomerId = sonCustomerId;
  }

  public Integer getLevelId() {
    return levelId;
  }

  public void setLevelId(Integer levelId) {
    this.levelId = levelId;
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

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  public Date getD_CreateDate() {
    return d_CreateDate;
  }

  public void setD_CreateDate(Date dCreateDate) {
    d_CreateDate = dCreateDate;
  }

  public BigDecimal getN_AvailableIntegral() {
    return n_AvailableIntegral;
  }

  public void setN_AvailableIntegral(BigDecimal n_AvailableIntegral) {
    this.n_AvailableIntegral = n_AvailableIntegral;
  }

  public BigDecimal getN_totalIntegral() {
    return n_totalIntegral;
  }

  public void setN_totalIntegral(BigDecimal n_totalIntegral) {
    this.n_totalIntegral = n_totalIntegral;
  }

  public String getDiscribe() {
    return discribe;
  }

  public void setDiscribe(String discribe) {
    this.discribe = discribe;
  }

  public Integer getScoreType() {
    return scoreType;
  }

  public void setScoreType(Integer scoreType) {
    this.scoreType = scoreType;
  }

  public BigDecimal getScoreNumber() {
    return scoreNumber;
  }

  public void setScoreNumber(BigDecimal scoreNumber) {
    this.scoreNumber = scoreNumber;
  }

  public String toKmUserString() {
    if (null == cardNum) {
      cardNum = "";
    }
    if (null == loginAccount) {
      loginAccount = "";
    }
    if (null == name) {
      name = "";
    }
    if (null == sex) {
      sex = "";
    }
    if (null == mobile) {
      mobile = "";
    }
    if (null == email) {
      email = "";
    }
    if (null == province) {
      province = "";
    }
    if (null == city) {
      city = "";
    }
    if (null == area) {
      area = "";
    }
    if (null == detailedaddress) {
      detailedaddress = "";
    }
    String birthdayString = "";
    if (null != birthday) {
      birthdayString = birthday.toString();
    }
    return new StringBuffer().append("{\"cardNum\":").append("\"").append(cardNum).append("\"")
        .append(",\"accountLogin\":").append("\"").append(loginAccount).append("\"")
        .append(",\"name\":").append("\"").append(name).append("\"").append(",\"sex\":")
        .append("\"").append(sex).append("\"").append(",\"mobile\":").append("\"").append(mobile)
        .append("\"").append(",\"d_Birthday\":").append("\"").append(birthdayString).append("\"")
        .append(",\"n_MaritalStatus\":").append("\"").append(maritalStatus).append("\"")
        .append(",\"address\":").append("\"").append(province + city + area + detailedaddress)
        .append("\"").append(",\"email\":").append("\"").append(email).append("\"")
        .append(",\"d_CreateDate\":").append("\"").append(d_CreateDate).append("\"").append("}")
        .toString();
  }



  public String toKmPersonalityString() {
    return new StringBuffer().append("{\"cardNum\":").append("\"").append(cardNum).append("\"")
        .append(",\"scoreNumber\":").append("\"").append(scoreNumber).append("\"")
        .append(",\"scoreType\":").append("\"").append(scoreType).append("\"")
        .append(",\"discribe\":").append("\"").append(discribe).append("\"")
        .append(",\"n_AvailableIntegral\":").append("\"").append(n_AvailableIntegral).append("\"")
        .append(",\"cardNum\":").append("\"").append(cardNum).append("\"").append("}").toString();
  }

}
