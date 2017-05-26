package com.kmzyc.b2b.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ProductStock;
import com.kmzyc.b2b.model.ProductStockInterface;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.persistence.Dao;

public interface ProductStockDAO extends Dao {

  public Double selectBySkuId(String skuId) throws SQLException;

  public Double selectByLongSkuId(Long skuId) throws SQLException;

  /**
   * 根据SKUID查询库存
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public ProductStockInterface queryProductStockById(Long skuId) throws SQLException;

  /**
   * 查询库存数量最大的仓库ID
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public long queryWarehouseOfMaxproductAmount(String skuId) throws SQLException;

  /**
   * 库存不足的商品
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<String> checkProductStockNumISOKInOrder(String orderCode) throws SQLException;

  /**
   * 订单中已经下架的商品
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<String> queryOrderProductIsOutOfShelf(String orderCode) throws SQLException;

  /**
   * 查询套餐库存信息
   * 
   * @param suitIds
   * @return
   * @throws ServiceException
   */
  public List<Map<String, String>> querySuitStockInfo(Long suitId) throws SQLException;

  /**
   * 批量查询套餐库存信息
   * 
   * @param suitIds
   * @return
   * @throws ServiceException
   */
  public List<Map<String, String>> querySuitStockInfoForBatch(List<Long> suitIds)
      throws SQLException;

  /**
   * 批量查询库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  public List<ProductStockInterface> queryBatchStock(List<Long> skuIds) throws SQLException;

  /**
   * 查询库存数量最大的仓库ID
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public Map<Long, BigDecimal> queryWarehouseId(List<Long> skuIds) throws SQLException;

  /**
   * 查询sku库存
   * 
   * @param skuIds
   * @return
   * @throws SQLException
   */
  public Map<Long, Integer> querySkuStock(List<Long> skuIds) throws SQLException;


  /**
   * 20151222 add 查询多仓情况下 库存数-订购数 差值最大的那条库存记录,用来判断该商品是否缺货
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public ProductStock queryMaxStockRecord(Long skuId) throws SQLException;
}
