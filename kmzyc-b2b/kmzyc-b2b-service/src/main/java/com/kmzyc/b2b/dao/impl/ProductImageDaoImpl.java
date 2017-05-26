package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.ProductImageDao;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.framework.exception.DaoException;

@Component
public class ProductImageDaoImpl implements ProductImageDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public ProductImage findBySkuId(Long skuId) throws SQLException, DaoException {
    List list = sqlMapClient.queryForList("ProductImage.findBySkuId", skuId);
    if (list != null && list.size() > 0) {
      ProductImage productImage = (ProductImage) list.get(0);
      return productImage;
    } else {
      throw new DaoException("skuId: " + skuId + " ProductImage not find.");
    }
  }

  @Override
  public List<ProductImage> findAllImageBySku(ProductImage prodImage) throws SQLException {
    List<ProductImage> productImageList =
        sqlMapClient.queryForList("ProductImage.findAllBySkuId", prodImage);
    return productImageList;
  }

@Override
public List<ProductImage> findAllImageByProductId(ProductImage prodImage)
		throws SQLException {
	return sqlMapClient.queryForList("ProductImage.findAllImageByProductId", prodImage);
}

}
