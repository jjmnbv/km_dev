package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ProductStockInterface;
import com.kmzyc.b2b.vo.CompositionCarProduct;
import com.kmzyc.framework.exception.ServiceException;

public interface ProductStockService {
  public Double selectBySkuId(String skuId);

  public Double selectByLongSkuId(Long skuId);

  /**
   * 根据SKUID查询库存
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public ProductStockInterface queryProductStockById(Long skuId) throws ServiceException;

  /**
   * 根据SkuId查询商品的真实库存
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public int queryRealStockBySkuId(Long skuId) throws SQLException;

  /**
   * 查询库存数量最大的仓库ID
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public long queryWarehouseOfMaxproductAmount(String skuId) throws ServiceException;

  /**
   * 查询订单中商品库存数量是否满足
   * 
   * @param orderCode
   * @return 订单中商品库存不足的商品的列表
   * @throws ServiceException
   */
  public List<String> checkProductStockNumISOKInOrder(String orderCode) throws ServiceException;

  /**
   * 查询订单中已经下架的商品
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public List<String> queryOrderProductIsOutOfShelf(String orderCode) throws ServiceException;

  /**
   * 查询套餐信息
   * 
   * @param suitIds
   * @return
   * @throws ServiceException
   */
  public List<CompositionCarProduct> querySuitInfo(String suitIds) throws ServiceException;

  /**
   * 批量查询库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  public List<ProductStockInterface> queryBatchStock(List<Long> skuIds) throws ServiceException;

  /**
   * 查询库存数量最大的仓库ID
   * 
   * @param skuId
   * @return
   * @throws ServiceException
   */
  public Map<Long, BigDecimal> queryWarehouseId(List<Long> skuIds) throws ServiceException;

  /**
   * 更新订单活动库存
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public boolean updateProductOrderQuantityCache(Long userId, boolean isReduce, String orderCode)
      throws ServiceException;

  /**
   * 查询sku库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  public Map<Long, Integer> querySkuStock(List<Long> skuIds) throws ServiceException;
}
