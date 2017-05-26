package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.ProductSkuQuantityDao;
import com.kmzyc.b2b.model.ProductSkuQuantity;
import com.km.framework.persistence.impl.DaoImpl;

@Component("productSkuQuantityDao")
public class ProductSkuQuantityDaoImpl extends DaoImpl implements ProductSkuQuantityDao {

  private static Logger logger= LoggerFactory.getLogger(ProductSkuQuantityDao.class);

  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public void updateProductSkuQuantity(ProductSkuQuantity productSkuQuantity) {

    try {
      sqlMapClient.update("ProductSkuQuantity.updateProductSkuQuantity", productSkuQuantity);
    } catch (SQLException e) {
      logger.error(e.getMessage(),e);
    }
  }

  @Override
  public void insertProductSkuQuantity(ProductSkuQuantity productSkuQuantity) {

    try {
      sqlMapClient.insert("ProductSkuQuantity.insertProductSkuQuantity", productSkuQuantity);
    } catch (SQLException e) {
      logger.error(e.getMessage(),e);
    }
  }

  @Override
  public Integer selectCountProductSkuQuantity(Long productSkuId) {

    Integer count = 0;
    try {
      count =
          (Integer) sqlMapClient.queryForObject("ProductSkuQuantity.selectCountProductSkuQuantity",
              productSkuId);
    } catch (SQLException e) {
      logger.error(e.getMessage(),e);
    }
    return count;
  }

  /**
   * 增加浏览数
   * 
   * @throws SQLException
   */
  public void addBrowseQuantity(Long skuId) throws SQLException {
    sqlMapClient.insert("ProductSkuQuantity.SQL_ADD_BROWSE_QUANTITY", skuId);
  }
}
