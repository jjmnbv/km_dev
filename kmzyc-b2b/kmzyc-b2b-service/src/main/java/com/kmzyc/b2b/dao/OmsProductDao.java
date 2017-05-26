package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.OmsProductData;
import com.kmzyc.b2b.vo.OmsProductSkuData;

/**
 * oms对接的相关方法,查询产品 20151028
 * 
 * @author KM
 * 
 */
public interface OmsProductDao {

  /**
   * 根据条件查询product信息
   * 
   * @param paraMap
   * @return
   * @throws SQLException
   */
  public List<OmsProductData> queryProductByMap(Map<String, Object> paraMap) throws SQLException;

  /**
   * 根据erpcode查询产品信息
   * 
   * @param paraMap
   * @return
   * @throws SQLException
   */
  public List<OmsProductData> queryProductErpCode(String erpProCode) throws SQLException;

  /**
   * 根据条件查询 sku信息
   * 
   * @param paraMap
   * @return
   * @throws SQLException
   */
  public List<OmsProductSkuData> queryProductSkuByMap(Map<String, Object> paraMap)
      throws SQLException;


  /**
   * 根据条件查询 sku信息
   * 
   * @param paraMap
   * @return
   * @throws SQLException
   */
  public List<OmsProductSkuData> queryProductSkuByProductId(Map<String, Object> paraMap)
      throws SQLException;



  /**
   * 根据条件查询product信息
   * 
   * @param paraMap
   * @return
   * @throws SQLException
   */
  public List<OmsProductData> queryProductListForOms(Map<String, String> paraMap)
      throws SQLException;



  /**
   * 根据条件查询product信息
   * 
   * @param paraMap
   * @return
   * @throws SQLException
   */
  public Integer queryCountListForOms(Map<String, String> paraMap) throws SQLException;

}
