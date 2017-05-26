package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.framework.exception.DaoException;

public interface ProductImageDao {
  ProductImage findBySkuId(Long skuId) throws SQLException, DaoException;

  public List<ProductImage> findAllImageBySku(ProductImage prodImage) throws SQLException;
  
  public List<ProductImage> findAllImageByProductId(ProductImage prodImage) throws SQLException;
}
