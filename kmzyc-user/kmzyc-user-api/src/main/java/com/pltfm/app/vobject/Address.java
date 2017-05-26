package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 收货地址实体
 * 
 * @author zhl
 * @since 2013-07-11
 */
@SuppressWarnings("serial")
public class Address implements Serializable {
  /** 收货地址主键 **/
  @JSONField(name = "addressId")
  private Integer n_addressId;
  /** 账户号对应的收货地址 **/
  @JSONField(name = "accountId")
  private Integer n_accountId;
  /** 收货人姓名 **/
  private String name;
  /** 收货地址所在省 **/
  private String province;
  /** 收货地址所在城市 **/
  private String city;
  /** 所在区域 **/
  private String area;
  /** 详细地址 **/
  private String detailedAddress;
  /** 模糊地址查询 , 不序列化 **/
  private String fuzzyAddress;
  /** 邮编 **/
  private String postalcode;
  /** 地址别名 **/
  private String addName;
  /** 手机号码 **/
  private String cellphone;
  /** 固定电话 **/
  private String telephone;
  /** 是否默认 **/
  private Integer status;
  /** 账号（前台显示） , 不序列化 **/
  private String accountLogin;
  /** 创建时间 **/
  @JSONField(name = "createdate")
  private Date d_createdate;
  /** 最后修改日期 **/
  @JSONField(name = "lastupdate")
  private Date d_lastupdate;
  /** 开始索引值 , 不序列化 **/
  private Integer startIndex;
  /** 结束索引值 , 不序列化 **/
  private Integer endIndex;
  /** 客户登录账号id **/
  private Integer loginId;
  /** 收货邮箱地址 , 不序列化 **/
  private Date endDate;

  private Boolean isChecked = Boolean.FALSE; // 判断收货地址否选中，默认不选中
  private int addressSource;
  /** 创建时间 , 不序列化 **/
  private Date createDate;
  
 
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @JSONField(serialize = false)
  public String getFuzzyAddress() {
    return fuzzyAddress;
  }

  @JSONField(deserialize = false)
  public void setFuzzyAddress(String fuzzyAddress) {
    this.fuzzyAddress = fuzzyAddress;
  }

  @JSONField(serialize = false)
  public Date getEndDate() {
    return endDate;
  }

  @JSONField(deserialize = false)
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  private String email;

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

  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(String postalcode) {
    this.postalcode = postalcode;
  }

  public String getAddName() {
    return addName;
  }

  public void setAddName(String addName) {
    this.addName = addName;
  }

  @JSONField(serialize = false)
  public Integer getStartIndex() {
    return startIndex;
  }

  @JSONField(deserialize = false)
  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getN_addressId() {
    return n_addressId;
  }

  public void setN_addressId(Integer nAddressId) {
    n_addressId = nAddressId;
  }

  public Integer getN_accountId() {
    return n_accountId;
  }

  public void setN_accountId(Integer nAccountId) {
    n_accountId = nAccountId;
  }

  public Date getD_createdate() {
    return d_createdate;
  }

  public void setD_createdate(Date dCreatedate) {
    d_createdate = dCreatedate;
  }

  public Date getD_lastupdate() {
    return d_lastupdate;
  }

  public void setD_lastupdate(Date dLastupdate) {
    d_lastupdate = dLastupdate;
  }

  public String getDetailedAddress() {
    return detailedAddress;
  }

  public void setDetailedAddress(String detailedAddress) {
    this.detailedAddress = detailedAddress;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @JSONField(serialize = false)
  public Integer getEndIndex() {
    return endIndex;
  }

  @JSONField(deserialize = false)
  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  @JSONField(serialize = false)
  public String getAccountLogin() {
    return accountLogin;
  }

  @JSONField(deserialize = false)
  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public Boolean getIsChecked() {
    return isChecked;
  }

  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
  }

  public int getAddressSource() {
    return addressSource;
  }

  public void setAddressSource(int addressSource) {
    this.addressSource = addressSource;
  }
}
