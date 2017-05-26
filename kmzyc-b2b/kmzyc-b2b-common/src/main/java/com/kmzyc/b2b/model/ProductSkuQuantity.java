package com.kmzyc.b2b.model;

import com.kmzyc.b2b.vo.BaseProduct;

public class ProductSkuQuantity extends BaseProduct {
  private Long id;
  private Integer saleQuantity;
  private Integer browseQuantity;
  private Integer commentQuantity;
  private Integer favoritesQuantity;

  public ProductSkuQuantity() {
    saleQuantity = 0;
    browseQuantity = 0;
    commentQuantity = 0;
    favoritesQuantity = 0;
  }

  /**
   * 主键
   * 
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * 主键
   * 
   * @return
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * 销售数量
   * 
   * @return
   */
  public Integer getSaleQuantity() {
    return saleQuantity;
  }

  /**
   * 销售数量
   * 
   * @return
   */
  public void setSaleQuantity(Integer saleQuantity) {
    this.saleQuantity = saleQuantity;
  }

  /**
   * 浏览数量
   * 
   * @return
   */
  public Integer getBrowseQuantity() {
    return browseQuantity;
  }

  /**
   * 浏览数量
   * 
   * @return
   */
  public void setBrowseQuantity(Integer browseQuantity) {
    this.browseQuantity = browseQuantity;
  }

  /**
   * 评论数量
   * 
   * @return
   */
  public Integer getCommentQuantity() {
    return commentQuantity;
  }

  /**
   * 评论数量
   * 
   * @return
   */
  public void setCommentQuantity(Integer commentQuantity) {
    this.commentQuantity = commentQuantity;
  }

  /**
   * 收藏数量
   * 
   * @return
   */
  public Integer getFavoritesQuantity() {
    return favoritesQuantity;
  }

  /**
   * 收藏数量
   * 
   * @return
   */
  public void setFavoritesQuantity(Integer favoritesQuantity) {
    this.favoritesQuantity = favoritesQuantity;
  }

}
