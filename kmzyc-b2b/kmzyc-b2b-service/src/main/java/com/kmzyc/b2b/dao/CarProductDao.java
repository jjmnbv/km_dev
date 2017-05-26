package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersInfo;

public interface CarProductDao extends Dao {

  /**
   * 从数据库获取购物车产品信息 包括名称库存图片上下架状态重量等
   */
  public CarProduct findBySkuId(Long productSkuId) throws ServiceException;

  /**
   * 查询可用商品，须检查上下架、库存
   * 
   * @param productSkuId
   * @param amount
   * @return
   * @throws SQLException
   */
  public Map<String, String> queryAbleCarProduct(Long skuId, Integer amount, String channel)
      throws SQLException;

  /**
   * 查询可用套餐，须检查上下架、库存
   * 
   * @param suitId
   * @param amount
   * @return
   * @throws SQLException
   */
  public List<Map<String, String>> queryAbleComposition(Long suitId, String channel)
      throws SQLException;

  /**
   * 批量
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Long> queryAbleProductSkuForBatch(Map<Long, Integer> paramsList) throws SQLException;

  /**
   * 查询产品类型
   * 
   * @param skudId
   * @return
   * @throws ServiceException
   */
  public Integer queryProductType(Long skudId) throws SQLException;

  /**
   * 查询产品类型
   * 
   * @param skudId
   * @return
   * @throws ServiceException
   */
  public Map<Long, Integer> queryBatchProductType(List<Long> skudIds) throws SQLException;

  /**
   * 查询套餐内产品类型
   * 
   * @param comId
   * @return
   * @throws ServiceException
   */
  public Map<Long, Integer> queryProductTypeByComId(Long comId) throws SQLException;

  /**
   * 根据供应商ID查询商家名称
   * 
   * @param sellerId
   * @return
   * @throws SQLException
   */
  public String queryCorporateNameBySupplierId(Long sellerId) throws SQLException;

  /**
   * 批量查询产品最大库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  public Map<Long, Integer> queryBatchMaxStock(List<Long> skuIds) throws SQLException;

  public SuppliersInfo selectByPrimaryKey(Long sellerId) throws SQLException;

  public CarProduct queryTimeProduct(String productId) throws SQLException;
}
