package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * 调查记录
 */
public class BnesFavorites implements Serializable {
  /***
   * 
   * 主键ID
   */
  private Integer FavoritesID;
  /***
   * 
   * 登录ID
   */
  private Integer loginId;
  /***
   * 
   * 收藏内容CODE如：产品SKU_CODE、商户CODE
   */
  private String content_Code;
  /***
   * 
   * 收藏类型：1、产品 2、店铺
   */
  private Integer favorites_Type;
  /***
   * 
   * 收藏创建日期
   */
  private Date create_Date;
  /***
   * 
   * 收藏创建日期
   */
  private Date modify_Date;
  /***
   * 
   * 收藏修改人ID
   */
  private Integer modifieId;
  /***
   * 
   * 物理类目唯一性标示
   */
  private Integer categoryId;

  public Integer getFavoritesID() {
    return FavoritesID;
  }

  public void setFavoritesID(Integer favoritesID) {
    FavoritesID = favoritesID;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public String getContent_Code() {
    return content_Code;
  }

  public void setContent_Code(String content_Code) {
    this.content_Code = content_Code;
  }

  public Integer getFavorites_Type() {
    return favorites_Type;
  }

  public void setFavorites_Type(Integer favorites_Type) {
    this.favorites_Type = favorites_Type;
  }

  public Date getCreate_Date() {
    return create_Date;
  }

  public void setCreate_Date(Date create_Date) {
    this.create_Date = create_Date;
  }

  public Date getModify_Date() {
    return modify_Date;
  }

  public void setModify_Date(Date modify_Date) {
    this.modify_Date = modify_Date;
  }

  public Integer getModifieId() {
    return modifieId;
  }

  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }


}
