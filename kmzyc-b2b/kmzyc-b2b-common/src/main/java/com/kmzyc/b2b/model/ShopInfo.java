package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺信息实体 20150917 mlq 暂时是为嵌合 app接口
 * 
 * @author KM
 * 
 */
public class ShopInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 接口调用返回码 0: 失败 200:成功
   */
  private String code;
  /**
   * 接口调用信息提示
   */
  private String message;

  /**
   * 店铺Id
   */
  private Long shopId;

  /**
   * 店铺名称
   */
  private String shopName;
  /**
   * 店铺logo地址
   */
  private String logoPath;
  /**
   * 店铺招牌
   */
  private String physhopImagePath;
  /**
   * 关注人数（收藏数）
   */
  private Integer collectCount;
  /**
   * 商品总数
   */
  private Integer productTotalCount;
  /**
   * 商品上新数
   */
  private Integer newProductCount;
  /**
   * 促销商品总数
   */
  private Integer promotionProductCount;
  /**
   * 综合评分
   */
  private float score;
  /**
   * 店铺介绍
   */
  private String introduce;
  /**
   * 公司名称
   */
  private String companyName;
  /**
   * 公司地址
   */
  private String companyAddress;
  /**
   * 联系电话
   */
  private String telephone;

  /**
   * 宝贝描述相符评分
   */
  private BigDecimal prodDescPoint;
  /**
   * 卖家发货速度评分
   */
  private BigDecimal sendSpeedPoint;
  /**
   * 物流配送速度评分
   */
  private BigDecimal dispSpeedPoint;
  /**
   * 售前售后服务评分
   */
  private BigDecimal servicePoint;

  /**
   * 店铺首页轮播图
   */
  private List<ShopBannerImage> bannerImages;
  /**
   * 店铺内所有店内分类
   */
  private List<ShopCategory> shopCategorysList;

  /**
   * 店铺所属的供应商
   */
  private Long supplierId;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  public String getPhyshopImagePath() {
    return physhopImagePath;
  }

  public void setPhyshopImagePath(String physhopImagePath) {
    this.physhopImagePath = physhopImagePath;
  }

  public Integer getCollectCount() {
    return collectCount;
  }

  public void setCollectCount(Integer collectCount) {
    this.collectCount = collectCount;
  }

  public Integer getProductTotalCount() {
    return productTotalCount;
  }

  public void setProductTotalCount(Integer productTotalCount) {
    this.productTotalCount = productTotalCount;
  }

  public Integer getNewProductCount() {
    return newProductCount;
  }

  public void setNewProductCount(Integer newProductCount) {
    this.newProductCount = newProductCount;
  }

  public Integer getPromotionProductCount() {
    return promotionProductCount;
  }

  public void setPromotionProductCount(Integer promotionProductCount) {
    this.promotionProductCount = promotionProductCount;
  }

  public String getIntroduce() {
    return introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyAddress() {
    return companyAddress;
  }

  public void setCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public List<ShopBannerImage> getBannerImages() {
    return bannerImages;
  }

  public void setBannerImages(List<ShopBannerImage> bannerImages) {
    this.bannerImages = bannerImages;
  }

  public List<ShopCategory> getShopCategorysList() {
    return shopCategorysList;
  }

  public void setShopCategorysList(List<ShopCategory> shopCategorysList) {
    this.shopCategorysList = shopCategorysList;
  }

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public BigDecimal getProdDescPoint() {
    return prodDescPoint;
  }

  public void setProdDescPoint(BigDecimal prodDescPoint) {
    this.prodDescPoint = prodDescPoint;
  }

  public BigDecimal getSendSpeedPoint() {
    return sendSpeedPoint;
  }

  public void setSendSpeedPoint(BigDecimal sendSpeedPoint) {
    this.sendSpeedPoint = sendSpeedPoint;
  }

  public BigDecimal getDispSpeedPoint() {
    return dispSpeedPoint;
  }

  public void setDispSpeedPoint(BigDecimal dispSpeedPoint) {
    this.dispSpeedPoint = dispSpeedPoint;
  }

  public BigDecimal getServicePoint() {
    return servicePoint;
  }

  public void setServicePoint(BigDecimal servicePoint) {
    this.servicePoint = servicePoint;
  }


}
