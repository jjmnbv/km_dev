package com.kmzyc.b2b.third.model.taobao;

/**
 * 买家收货地址信息
 * 
 * @author Administrator 2014-06-13
 * 
 */
public class Buyer {
  String addressId; // 地址ID
  String receiverName; // 收货人姓名
  String mobile; // 手机号码
  String city; // 城市
  String address; // 详细地址

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
