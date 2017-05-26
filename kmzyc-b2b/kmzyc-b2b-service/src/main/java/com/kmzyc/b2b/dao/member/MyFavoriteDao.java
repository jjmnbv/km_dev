package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.ProductImage;

public interface MyFavoriteDao extends Dao {

  /**
   * 根据skuId查找产品的默认图片信息（主图）
   * 
   * @param sqlId
   * @param skuCode
   * @throws SQLException
   * @return
   */
  public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
      throws SQLException;


  /**
   * 根据Skucode删除产品收藏
   */
  public void deleteMyfavoriteBySkucode(Map<String, Object> map) throws SQLException;
}
