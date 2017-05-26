package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 赠品 加价购 券
 * 
 * @author hwf
 * 
 */
public class Gift implements Comparable<Gift>, Serializable {
  private static final long serialVersionUID = -2996415974852980536L;
  private String dataId;
  private String id;
  private String name;
  private String img;
  private Integer stockCount;
  private BigDecimal price;
  private Integer amount;
  private Integer maxAmount;
  private Integer meetData;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getStockCount() {
    return stockCount;
  }

  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  static String productImage;

  public String getAppImg() {
    if (img == null) {
      return null;
    }
    if (productImage == null) {
      synchronized (ShopCartProduct.class) {
        if (productImage == null) {
          productImage = ConfigurationUtil.getString("picPath");
          if (productImage == null) {
            throw new NullPointerException("picPath 配置为空");
          }
        }
      }
    }
    return productImage + img.replace(".jpg", "_5.jpg");
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }


  public Integer getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(Integer maxAmount) {
    this.maxAmount = maxAmount;
  }

  public Integer getMeetData() {
    return meetData;
  }

  public void setMeetData(Integer meetData) {
    this.meetData = meetData;
  }

  @Override
  public int compareTo(Gift o) {
    int i = this.price.compareTo(o.getPrice());
    if (i == 0) {
      i = this.id.hashCode() - o.getId().hashCode();
    }
    return i * (-1);
  }


  public String getDataId() {
    return dataId;
  }

  public void setDataId(String dataId) {
    this.dataId = dataId;
  }

  @Override
  public int hashCode() {
    return ("com.kmzyc.b2b.shopcart.vo.Gift" + this.dataId).hashCode();
  }
}
