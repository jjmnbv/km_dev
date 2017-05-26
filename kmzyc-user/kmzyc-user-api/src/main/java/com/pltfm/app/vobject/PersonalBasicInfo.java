package com.pltfm.app.vobject;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 个人基本信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class PersonalBasicInfo implements Serializable {
  /**
   * 客户类别主键
   */
  private Integer n_CustomerTypeId;

  /**
   * 客户类别名称
   */
  private String customerName;

  /**
   * 客户级别主键
   */
  private Integer n_LevelId;

  /**
   * 客户级别名称
   */
  private String levelName;

  /**
   * 客户头衔主键
   */
  private Integer n_RankId;
  /**
   * 客户头衔名称
   */
  private String rankName;

  /**
   * 登录主键
   */
  private Integer n_LoginId;

  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 经验值
   */
  private Integer n_EmpiricalValue;
  /**
   * 总积分
   */
  private Double n_TotalIntegral;

  /**
   * 可用积分
   */
  private Double n_AvailableIntegral;
  /**
   * 信用值
   */
  private Integer n_IndividualCreditValue;



  /**
   * 个人主键
   */
  private Integer n_PersonalId;

  /**
   * 姓名
   */
  private String name;

  /**
   * 性别
   */
  private String sex;

  /**
   * 年龄
   */
  private Integer n_Age;

  /**
   * 生日
   */
  private Date d_Birthday;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 电子邮箱
   */
  private String email;

  /**
   * 证件类型
   */
  private Integer n_CertificateType;

  /**
   * 证件号码
   */
  private String certificateNumber;

  /**
   * 所在地
   */
  private String location;

  /**
   * 故乡所在地
   */
  private String hometownLocation;

  /**
   * 教育状况
   */
  private String educationalStatus;

  /**
   * 工作单位
   */
  private String workUnit;

  /**
   * 职业类型
   */
  private String professionType;

  /**
   * 职业
   */
  private String profession;

  /**
   * 年收入
   */
  private Integer n_AnnualIncome;

  /**
   * 状态
   */
  private Integer n_Status;

  /**
   * 创建日期
   */
  private Date d_CreateDate;


  /**
   * 登录账号状态
   */
  private Integer login_Status;
  /**
   * 创建人
   */
  private Integer n_Created;

  /**
   * 修改日期
   */
  private Date d_ModifyDate;

  /**
   * 修改人
   */
  private Integer n_Modified;

  /**
   * 证件图片正反面
   */
  private String certificatePicture;

  /**
   * 证件有效时间(月)
   */
  private String certificateActiveTime;

  /**
   * 认证状态
   */
  private Integer authenticationStatus;

  /**
   * 终端来源
   */
  private Integer registerDevice;

  /**
   * 项目来源
   */
  private Integer registerPlatform;


  /** 收货地址所在省 **/
  private String province;
  /** 收货地址所在城市 **/
  private String city;
  /** 所在区域 **/
  private String area;
  /** 详细地址 **/
  private String detailedAddress;
  /** 居住信息 **/
  private Integer liveStatus;
  // IS_MOBILE
  private Integer isMobile;
  // IS_EMAIL
  private Integer isEmail;
  private String town;

  // 用户总部卡号
  private String cardNum;

  // 收货地址列表
  private List<Address> addressList;
  
  //返利用户名
  private String fanliUserName;
  
  //时代编码
  private String eraNo;
  //时代推荐人编码
  private String  recommendedNo;
  //是否是供应商
  private Integer isSupplier;
  //是否是普通用户
  private Integer isCommonUser;
  //是否是采购商
  private Integer isPurchaser;
  //是否是游客
  private Integer isTourist;
  //是否是时代用户
  private Integer isEra;
  //是否是云商
  private Integer isSpreader;
  //是否是机构
  private Integer isCrowder;
  //是否第三方授权登录
  private Integer isThird;
  
  Integer[] identity;

  public Integer getIsThird() {
    return isThird;
  }

  public void setIsThird(Integer isThird) {
    this.isThird = isThird;
  }

  public Integer[] getIdentity() {
    return identity;
  }

  public void setIdentity(Integer[] identity) {
    this.identity = identity;
  }

  public String getFanliUserName() {
    return fanliUserName;
  }

  public void setFanliUserName(String fanliUserName) {
    this.fanliUserName = fanliUserName;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public Integer getIsMobile() {
    return isMobile;
  }

  public void setIsMobile(Integer isMobile) {
    this.isMobile = isMobile;
  }

  public Integer getIsEmail() {
    return isEmail;
  }

  public void setIsEmail(Integer isEmail) {
    this.isEmail = isEmail;
  }

  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;


  /**
   * 结束日期
   */
  private Date endDate;



  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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



  public Integer getN_PersonalId() {
    return n_PersonalId;
  }

  public void setN_PersonalId(Integer nPersonalId) {
    n_PersonalId = nPersonalId;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer nLoginId) {
    n_LoginId = nLoginId;
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

  public Integer getN_Age() {
    return n_Age;
  }

  public void setN_Age(Integer nAge) {
    n_Age = nAge;
  }

  public Date getD_Birthday() {
    return d_Birthday;
  }

  public void setD_Birthday(Date dBirthday) {
    d_Birthday = dBirthday;
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

  public Integer getN_CertificateType() {
    return n_CertificateType;
  }

  public void setN_CertificateType(Integer nCertificateType) {
    n_CertificateType = nCertificateType;
  }

  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getHometownLocation() {
    return hometownLocation;
  }

  public void setHometownLocation(String hometownLocation) {
    this.hometownLocation = hometownLocation;
  }

  public String getEducationalStatus() {
    return educationalStatus;
  }

  public void setEducationalStatus(String educationalStatus) {
    this.educationalStatus = educationalStatus;
  }

  public String getWorkUnit() {
    return workUnit;
  }

  public void setWorkUnit(String workUnit) {
    this.workUnit = workUnit;
  }

  public String getProfessionType() {
    return professionType;
  }

  public void setProfessionType(String professionType) {
    this.professionType = professionType;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public Integer getN_AnnualIncome() {
    return n_AnnualIncome;
  }

  public void setN_AnnualIncome(Integer nAnnualIncome) {
    n_AnnualIncome = nAnnualIncome;
  }

  public Integer getN_Status() {
    return n_Status;
  }

  public void setN_Status(Integer nStatus) {
    n_Status = nStatus;
  }

  public Date getD_CreateDate() {
    return d_CreateDate;
  }

  public void setD_CreateDate(Date dCreateDate) {
    d_CreateDate = dCreateDate;
  }

  public Integer getN_Created() {
    return n_Created;
  }

  public void setN_Created(Integer nCreated) {
    n_Created = nCreated;
  }

  public Date getD_ModifyDate() {
    return d_ModifyDate;
  }

  public void setD_ModifyDate(Date dModifyDate) {
    d_ModifyDate = dModifyDate;
  }

  public Integer getN_Modified() {
    return n_Modified;
  }

  public void setN_Modified(Integer nModified) {
    n_Modified = nModified;
  }

  public Integer getN_LevelId() {
    return n_LevelId;
  }

  public void setN_LevelId(Integer nLevelId) {
    n_LevelId = nLevelId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public Integer getN_EmpiricalValue() {
    return n_EmpiricalValue;
  }

  public void setN_EmpiricalValue(Integer nEmpiricalValue) {
    n_EmpiricalValue = nEmpiricalValue;
  }

  public Double getN_AvailableIntegral() {
    return n_AvailableIntegral;
  }

  public void setN_AvailableIntegral(Double nAvailableIntegral) {
    n_AvailableIntegral = nAvailableIntegral;
  }

  public Integer getN_IndividualCreditValue() {
    return n_IndividualCreditValue;
  }

  public void setN_IndividualCreditValue(Integer nIndividualCreditValue) {
    n_IndividualCreditValue = nIndividualCreditValue;
  }

  public Integer getN_RankId() {
    return n_RankId;
  }

  public void setN_RankId(Integer nRankId) {
    n_RankId = nRankId;
  }

  public Integer getN_CustomerTypeId() {
    return n_CustomerTypeId;
  }

  public void setN_CustomerTypeId(Integer nCustomerTypeId) {
    n_CustomerTypeId = nCustomerTypeId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getRankName() {
    return rankName;
  }

  public void setRankName(String rankName) {
    this.rankName = rankName;
  }

  public String getCertificatePicture() {
    return certificatePicture;
  }

  public void setCertificatePicture(String certificatePicture) {
    this.certificatePicture = certificatePicture;
  }

  public String getCertificateActiveTime() {
    return certificateActiveTime;
  }

  public void setCertificateActiveTime(String certificateActiveTime) {
    this.certificateActiveTime = certificateActiveTime;
  }

  public Integer getAuthenticationStatus() {
    return authenticationStatus;
  }

  public void setAuthenticationStatus(Integer authenticationStatus) {
    this.authenticationStatus = authenticationStatus;
  }

  public Integer getRegisterDevice() {
    return registerDevice;
  }

  public void setRegisterDevice(Integer registerDevice) {
    this.registerDevice = registerDevice;
  }

  public Integer getRegisterPlatform() {
    return registerPlatform;
  }

  public void setRegisterPlatform(Integer registerPlatform) {
    this.registerPlatform = registerPlatform;
  }

  public Double getN_TotalIntegral() {
    return n_TotalIntegral;
  }

  public void setN_TotalIntegral(Double nTotalIntegral) {
    n_TotalIntegral = nTotalIntegral;
  }

  public Integer getLogin_Status() {
    return login_Status;
  }

  public void setLogin_Status(Integer loginStatus) {
    login_Status = loginStatus;
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

  public String getDetailedAddress() {
    return detailedAddress;
  }

  public void setDetailedAddress(String detailedAddress) {
    this.detailedAddress = detailedAddress;
  }

  public Integer getLiveStatus() {
    return liveStatus;
  }

  public void setLiveStatus(Integer liveStatus) {
    this.liveStatus = liveStatus;
  }

  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }
  public String getEraNo() {
      return eraNo;
  }

  public void setEraNo(String eraNo) {
      this.eraNo = eraNo;
  }

  public String getRecommendedNo() {
      return recommendedNo;
  }

  public void setRecommendedNo(String recommendedNo) {
      this.recommendedNo = recommendedNo;
  }

  public Integer getIsSupplier() {
      return isSupplier;
  }

  public void setIsSupplier(Integer isSupplier) {
      this.isSupplier = isSupplier;
  }

  public Integer getIsCommonUser() {
      return isCommonUser;
  }

  public void setIsCommonUser(Integer isCommonUser) {
      this.isCommonUser = isCommonUser;
  }

  public Integer getIsPurchaser() {
      return isPurchaser;
  }

  public void setIsPurchaser(Integer isPurchaser) {
      this.isPurchaser = isPurchaser;
  }

  public Integer getIsTourist() {
      return isTourist;
  }

  public void setIsTourist(Integer isTourist) {
      this.isTourist = isTourist;
  }

  public Integer getIsEra() {
      return isEra;
  }

  public void setIsEra(Integer isEra) {
      this.isEra = isEra;
  }

  public Integer getIsSpreader() {
      return isSpreader;
  }

  public void setIsSpreader(Integer isSpreader) {
      this.isSpreader = isSpreader;
  }

  public Integer getIsCrowder() {
      return isCrowder;
  }

  public void setIsCrowder(Integer isCrowder) {
      this.isCrowder = isCrowder;
  }

}
