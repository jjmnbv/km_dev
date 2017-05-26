package com.kmzyc.b2b.model;

import com.google.common.base.Strings;
import com.kmzyc.b2b.vo.BaseProduct;
import com.kmzyc.framework.constants.FavoriteTypeEnums;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Favorite extends BaseProduct {

  /**
   * 序列化
   */
  private static final long serialVersionUID = -2787550599247922291L;

  private Long favoriteId;

  private Long loginId;

  private String contentCode;

  private Integer favoritesType;

  private Date createDate;

  private Date modifyDate;

  private Long modifieId;

  private String productName;

  private String imgPath;

  private List<Map<String, String>> categoryAttrValueList;

  private String categoryAttrValueStr;

  /**
   * 保存收藏记录时的销售价格
   */
  private BigDecimal priceCopy;

  private Integer realStock;

  private BigDecimal spreadPrice;
  private String imgPath5;
  /**
   * 店铺名
   */
  private String shopName;
  /**
   * 供应商ID
   */
  private Long supplierId;
  /**
   * 店铺URL
   */
  private String defaultDomainUrl;
  /**
   * 省
   */
  private String province;
  /**
   * 市
   */
  private String city;
  /**
   * 区
   */
  private String area;
  /**
   * 综合评分
   */
  private Float point;
  /**
   * pv值
   */
  private BigDecimal pValue;
  /**
   * 产品上下架状态
   */
  private String pStatus;

  public Favorite() {}

  /**
   * 产品收藏构造方法
   * 
   * @param userId
   * @param productSkuCode
   * @param priceCopy
   */
  public Favorite(Long userId, String productSkuCode, BigDecimal priceCopy) {
    this.loginId = userId;
    this.contentCode = productSkuCode;
    this.favoritesType = FavoriteTypeEnums.PRODUCT_SKU.getValue();
    this.createDate = new Date();
    this.priceCopy = priceCopy;
  }

  public Favorite(Long userId, Long productId, BigDecimal priceCopy) {
    this.loginId = userId;
    this.contentCode = productId.toString();
    this.favoritesType = FavoriteTypeEnums.PRODUCT_SKU.getValue();
    this.createDate = new Date();
    this.priceCopy = priceCopy;
  }

  public Favorite(Long userId, String shopCode) {
    this.loginId = userId;
    this.contentCode = shopCode;
    this.favoritesType = FavoriteTypeEnums.SHOP.getValue();
    this.createDate = new Date();

  }

  public Long getFavoriteId() {
    return favoriteId;
  }

  public void setFavoriteId(Long favoriteId) {
    this.favoriteId = favoriteId;
  }

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public String getContentId() {
    return contentCode;
  }

  public void setContentId(String contentCode) {
    this.contentCode = contentCode;
  }

  public Integer getFavoritesType() {
    return favoritesType;
  }

  public void setFavoritesType(Integer favoritesType) {
    this.favoritesType = favoritesType;
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

  public Long getModifieId() {
    return modifieId;
  }

  public void setModifieId(Long modifieId) {
    this.modifieId = modifieId;
  }

  public String getContentCode() {
    return contentCode;
  }

  public void setContentCode(String contentCode) {
    this.contentCode = contentCode;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public void setCategoryAttrValueList(List<Map<String, String>> categoryAttrValueList) {
    this.categoryAttrValueList = categoryAttrValueList;
    // String temp = "";
    // if(categoryAttrValueList != null && categoryAttrValueList.size() >
    // 0){
    // for(int i=0; i<categoryAttrValueList.size(); i++){
    // temp += categoryAttrValueList.get(i).get("categoryAttrValue") + " ";
    // }
    // temp.substring(0, temp.length()-1);
    // this.setCategoryAttrValueStr(temp);
    // }
  }

  public List<Map<String, String>> getCategoryAttrValueList() {
    return categoryAttrValueList;
  }

  public String getCategoryAttrValueStr() {
    return categoryAttrValueStr;
  }

  public void setCategoryAttrValueStr(String categoryAttrValueStr) {
    this.categoryAttrValueStr = categoryAttrValueStr;
  }

  public BigDecimal getPriceCopy() {
    return priceCopy;
  }

  public void setPriceCopy(BigDecimal priceCopy) {
    this.priceCopy = priceCopy;
  }

  @Override
  public String toString() {
    return "Favorite{" + "favoriteId=" + favoriteId + ", loginId=" + loginId + ", contentCode='"
        + contentCode + '\'' + ", favoritesType=" + favoritesType + ", createDate=" + createDate
        + ", modifyDate=" + modifyDate + ", modifieId=" + modifieId + '}';
  }

  public Integer getRealStock() {
    return realStock;
  }

  public void setRealStock(Integer realStock) {
    this.realStock = realStock;
  }

  public BigDecimal getSpreadPrice() {
    return spreadPrice;
  }

  public void setSpreadPrice(BigDecimal spreadPrice) {
    this.spreadPrice = spreadPrice;
  }

  public String getImgPath5() {
    if (Strings.isNullOrEmpty(imgPath)) return "";
    if (!imgPath.endsWith("_7.jpg")) return imgPath;
    return imgPath.substring(0, imgPath.length() - 6) + "_5.jpg";
  }

  public void setImgPath5(String imgPath5) {
    this.imgPath5 = imgPath5;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
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

  public String getDefaultDomainUrl() {
    return defaultDomainUrl;
  }

  public void setDefaultDomainUrl(String defaultDomainUrl) {
    this.defaultDomainUrl = defaultDomainUrl;
  }

  public Float getPoint() {
    return point;
  }

  public void setPoint(Float point) {
    this.point = point;
  }
  public BigDecimal getpValue() {
      return pValue;
  }

  public void setpValue(BigDecimal pValue) {
      this.pValue = pValue;
  }
  
  public String getpStatus() {
      return pStatus;
  }

  public void setpStatus(String pStatus) {
      this.pStatus = pStatus;
  }

}
