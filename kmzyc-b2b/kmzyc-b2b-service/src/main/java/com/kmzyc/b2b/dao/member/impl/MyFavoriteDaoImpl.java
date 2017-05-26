package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.MyFavoriteDao;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.km.framework.persistence.impl.DaoImpl;

@Component
public class MyFavoriteDaoImpl extends DaoImpl implements MyFavoriteDao {

  //static Logger logger = Logger.getLogger(MyFavoriteDaoImpl.class);
  private static Logger logger = LoggerFactory.getLogger(MyFavoriteDaoImpl.class);
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 根据skuId查找产品的默认图片信息（主图）
   * 
   * @param sqlId
   * @param skuCode
   * @return
   */
  public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
      throws SQLException {
    ProductImage productImage = null;
    try {
      productImage = (ProductImage) this.sqlMapClient.queryForObject(sqlId, skuCode);
    } catch (Exception e) {
      logger.error("查找产品图片信息出现异常，异常原因：" + e.getMessage(), e);
      throw new SQLException(e.getMessage(), e);
    }
    return productImage;
  }

  /**
   * 根据Skucode删除产品收藏
   */
  public void deleteMyfavoriteBySkucode(Map<String, Object> map) throws SQLException {
    this.sqlMapClient.delete("Favorite.deleteFavoriteByCode", map);
  }

}
