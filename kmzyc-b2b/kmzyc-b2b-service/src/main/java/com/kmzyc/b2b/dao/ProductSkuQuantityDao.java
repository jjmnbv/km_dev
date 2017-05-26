package com.kmzyc.b2b.dao;

import java.sql.SQLException;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.ProductSkuQuantity;

public interface ProductSkuQuantityDao extends Dao {
  public void updateProductSkuQuantity(ProductSkuQuantity productSkuQuantity);

  public void insertProductSkuQuantity(ProductSkuQuantity productSkuQuantity);

  public Integer selectCountProductSkuQuantity(Long skuId);

  /**
   * 增加浏览数
   * 
   * @throws SQLException
   */
  public void addBrowseQuantity(Long skuId) throws SQLException;
}
