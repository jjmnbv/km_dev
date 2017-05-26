package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

public class Address implements Serializable {
  private static final long serialVersionUID = 1L;
  // private static Logger logger = Logger.getLogger(Address.class);
  private static Logger logger = LoggerFactory.getLogger(Address.class);
  private int addressId;
  private int accountId;
  private String name;
  private String province;
  private String city;
  private String detailedAddress;
  private String postalcode;
  private String addName;
  private Date createdate;
  private Date lastupdate;
  private int status;
  private String telephone;
  private String cellphone;
  private String area;
  private String email;
  private Boolean isChecked = false; // 判断收货地址否选中，默认不选中

  /**
   * 20151214 add 标识地址来源,可能由外部账户推入地址,1代表返利网
   */
  private int addressSource;

  /** 客户登录账号id **/
  private Integer loginId;

  public Boolean getIsChecked() {
    if (!isChecked && status == 0) {
      isChecked = true;
    }
    return isChecked;
  }

  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
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

  public Date getCreatedate() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    if (status == 0) {
      this.isChecked = true;
    } else {
      this.isChecked = false;
    }
    this.status = status;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public com.pltfm.app.vobject.Address transFormToRemoteAddress() throws ObjectTransformException {
    com.pltfm.app.vobject.Address remoteAddress = new com.pltfm.app.vobject.Address();
    // 手动转换名称不相同的属性
    remoteAddress.setN_accountId(this.accountId);
    remoteAddress.setD_createdate(this.createdate);
    remoteAddress.setD_lastupdate(this.lastupdate);
    remoteAddress.setN_addressId(this.getAddressId());

    try {
      // 转换名称相同的属性
      BeanUtils.copyProperties(remoteAddress, this);
    } catch (IllegalAccessException e) {
      logger.error("将本地Address对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地Address对象转换为远程对象出错！");
    } catch (InvocationTargetException e) {
      logger.error("将本地Address对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地Address对象转换为远程对象出错！");
    }

    return remoteAddress;
  }

  public String getDetailedAddress() {
    return detailedAddress;
  }

  public void setDetailedAddress(String detailedAddress) {
    this.detailedAddress = detailedAddress;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAddressSource() {
    return addressSource;
  }

  public void setAddressSource(int addressSource) {
    this.addressSource = addressSource;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }
}
