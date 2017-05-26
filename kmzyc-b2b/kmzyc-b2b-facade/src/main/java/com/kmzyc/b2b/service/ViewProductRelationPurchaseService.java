package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ProductsOrderBySale;
import com.kmzyc.b2b.model.ViewProductRelationPurchase;

public interface ViewProductRelationPurchaseService {

  /**
   * 查询关联商品，不足数据条数时当前商品同品类补足
   * 
   * @param params
   * @return
   */
  public List<ViewProductRelationPurchase> findRelationProduct(Map<String, Object> params)
      throws SQLException;
  
  /**
   * 商品按上月销量排序
   * 
   * @param params
   * @return
   */
  public List<ProductsOrderBySale> findProductOrderBySalequantity(Map<String, Object> params)
      throws SQLException;
}
