package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.util.Map;

import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 商品促销信息缓存对象基类
 * 
 * @author罗涛
 * 
 */
public class NormalCartProduct extends CartProduct implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4609790631806879264L;
  Map<String, NormalCartProduct> nomalCarProductmap;// 固定层

  public Map<String, NormalCartProduct> getNomalCarProductmap() {
    return nomalCarProductmap;
  }

  public void setNomalCarProductmap(Map<String, NormalCartProduct> nomalCarProductmap) {
    this.nomalCarProductmap = nomalCarProductmap;
  }


  static String productImage;

  public String getImg() {

    String imge = this.getImagePath();
    if (imge == null) {
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
    return productImage + imge.replace(".jpg", "_5.jpg");
  }

}
