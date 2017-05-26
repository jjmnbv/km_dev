package com.kmzyc.b2b.third.model.wxCard;

/**
 * 微信卡券礼品券 康美人生门店指定信息表
 * 
 * @author maliqun
 * 
 */
public class WxKmrsShopInfo {

  /**
   * 门店id
   */
  private String shopId;
  /**
   * 门店名称
   */
  private String branchName;
  /**
   * 门店所在省份
   */
  private String branchProvince;
  /**
   * 门店详情地址
   */
  private String detailAddress;
  /**
   * 门店联系电话
   */
  private String telephone;

  public String getShopId() {
    return shopId;
  }

  public void setShopId(String shopId) {
    this.shopId = shopId;
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getBranchProvince() {
    return branchProvince;
  }

  public void setBranchProvince(String branchProvince) {
    this.branchProvince = branchProvince;
  }

  public String getDetailAddress() {
    return detailAddress;
  }

  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

}
