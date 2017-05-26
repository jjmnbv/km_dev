package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.ProductSku;

public interface SectionsService {

  /**
   * 根据栏目标识查询栏目下的商品信息
   * 
   * @param identification
   * @return
   * @throws SQLException
   */
  public List<ProductSku> queryProductSkuBySections(String identification) throws SQLException;
}
