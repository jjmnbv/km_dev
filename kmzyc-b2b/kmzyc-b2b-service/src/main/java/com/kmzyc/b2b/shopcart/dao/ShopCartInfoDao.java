package com.kmzyc.b2b.shopcart.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.supplier.model.SupplierFare;

public interface ShopCartInfoDao extends Dao {


  /**
   * 循环遍历套餐
   * 
   * @param ids
   * @return
   * @throws SQLException
   */


  public List<Map<String, String>> getComsiotionList(String[] ids) throws SQLException;


  /**
   * 循环遍历cp
   * 
   * @param skuIds
   * @return
   * @throws SQLException
   */

  public List<NormalCartProduct> getProductList(String[] skuIds) throws SQLException;


  /** 批量获取skuid 的库存量 */
  public Map<String, Integer> queryStockBatch(List<?> skuIds) throws SQLException;

  /**
   * 批量获取入驻商家运费
   * 
   * @param ids
   * @throws SQLException
   */
  public List<SupplierFare> selectSupplierFareInfoList(List<?> ids) throws SQLException;

}
