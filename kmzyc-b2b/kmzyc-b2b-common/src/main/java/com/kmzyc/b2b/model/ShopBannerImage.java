package com.kmzyc.b2b.model;

import java.io.Serializable;

/**
 * 店铺的首页banner轮播图 20150917 add
 * 
 * @author KM
 * 
 */
public class ShopBannerImage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  private String id;
  /**
   * 图片地址
   */
  private String imageUrl;
  /**
   * banner 信息类型
   */
  private String bannerType;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getBannerType() {
    return bannerType;
  }

  public void setBannerType(String bannerType) {
    this.bannerType = bannerType;
  }



}
