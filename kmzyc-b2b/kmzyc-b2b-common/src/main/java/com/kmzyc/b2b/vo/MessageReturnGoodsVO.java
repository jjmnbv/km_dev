package com.kmzyc.b2b.vo;

/**
 * 退货申请成功短信相关VO
 * 
 * @author luoyi
 * @createDate 2013/11/04
 */
import java.io.Serializable;

public class MessageReturnGoodsVO implements Serializable {

  private static final long serialVersionUID = -7375302943523790835L;

  private Long loginId;// 会员ID
  private String loginName;// 会员名称
  private String mobile;// 会员手机号
  private String companyAddress; // 商家地址 有默认值
  private String companyUserName; // 商家收货人 有默认值
  private String companyPhone; // 商家电话 有默认值
  private String zipCode; // 商家邮编 有默认值
  private String returnOrderCode;// 退货单号

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCompanyAddress() {
    return companyAddress;
  }

  public void setCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
  }

  public String getCompanyUserName() {
    return companyUserName;
  }

  public void setCompanyUserName(String companyUserName) {
    this.companyUserName = companyUserName;
  }

  public String getCompanyPhone() {
    return companyPhone;
  }

  public void setCompanyPhone(String companyPhone) {
    this.companyPhone = companyPhone;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getReturnOrderCode() {
    return returnOrderCode;
  }

  public void setReturnOrderCode(String returnOrderCode) {
    this.returnOrderCode = returnOrderCode;
  }

  @Override
  public String toString() {
    return "MessageReturnGoodsVO [loginId=" + loginId + ", loginName=" + loginName + ", mobile="
        + mobile + ", companyAddress=" + companyAddress + ", companyUserName=" + companyUserName
        + ", companyPhone=" + companyPhone + ", zipCode=" + zipCode + ", returnOrderCode="
        + returnOrderCode + "]";
  }

}
