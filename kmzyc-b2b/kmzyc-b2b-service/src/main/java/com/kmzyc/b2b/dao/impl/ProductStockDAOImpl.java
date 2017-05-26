package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.ProductStockDAO;
import com.kmzyc.b2b.model.ProductStock;
import com.kmzyc.b2b.model.ProductStockInterface;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Repository
public class ProductStockDAOImpl extends DaoImpl implements ProductStockDAO {

  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  public Double selectBySkuId(String skuId) throws SQLException {
    Double stockAmount = (Double) sqlMapClient.queryForObject("ProductStock.findBySkuId", skuId);
    if (stockAmount == null) {
      stockAmount = 0d;
    }
    return stockAmount;
  }

  public Double selectByLongSkuId(Long skuId) throws SQLException {
    Double stockAmount =
        (Double) sqlMapClient.queryForObject("ProductStock.findByLongSkuId", skuId);
    return stockAmount;
  }

  /**
   * 根据SKUID查询库存
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public ProductStockInterface queryProductStockById(Long skuId) throws SQLException {
    return (ProductStockInterface) sqlMapClient.queryForObject(
        "ProductStock.SQL_QUERY_PRODUCT_STOCK_BY_SKU_ID", skuId);
  }

  public long queryWarehouseOfMaxproductAmount(String skuId) throws SQLException {
    return (Long) sqlMapClient.queryForObject("ProductStock.queryWarehouseOfMaxproductAmount",
        skuId);
  }

  @Override
  public List<String> checkProductStockNumISOKInOrder(String orderCode) throws SQLException {
    return (List<String>) sqlMapClient.queryForList("ProductStock.checkProductStockNumISOKInOrder",
        orderCode);

  }

  @Override
  public List<String> queryOrderProductIsOutOfShelf(String orderCode) throws SQLException {
    return (List<String>) sqlMapClient.queryForList("ProductStock.queryOrderProductIsOutOfShelf",
        orderCode);
  }

  /**
   * 查询套餐库存信息
   * 
   * @param suitId
   * @return
   * @throws ServiceException
   */
  public List<Map<String, String>> querySuitStockInfo(Long suitId) throws SQLException {
    return sqlMapClient.queryForList("ProductStock.SQL_QUERY_SUIT_STOCK_INFO", suitId);
  }

  /**
   * 批量查询套餐库存信息
   * 
   * @param suitIds
   * @return
   * @throws ServiceException
   */
  public List<Map<String, String>> querySuitStockInfoForBatch(List<Long> suitIds)
      throws SQLException {
    return sqlMapClient.queryForList("ProductStock.SQL_QUERY_BATCH_SUIT_STOCK_INFO", suitIds);
  }

  /**
   * 批量查询库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  @Override
  public List<ProductStockInterface> queryBatchStock(List<Long> skuIds) throws SQLException {
    return sqlMapClient.queryForList("ProductStock.SQL_QUERY_BATCH_STOCK_INFO", skuIds);
  }

  /**
   * 查询库存数量最大的仓库ID
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public Map<Long, BigDecimal> queryWarehouseId(List<Long> skuIds) throws SQLException {
    return (Map<Long, BigDecimal>) sqlMapClient.queryForMap("ProductStock.SQL_QUERY_WAREHOUSE_ID",
        skuIds, "SKUID", "WAREID");
  }

  /**
   * 查询sku库存
   * 
   * @param skuIds
   * @return
   * @throws SQLException
   */
  public Map<Long, Integer> querySkuStock(List<Long> skuIds) throws SQLException {
    return (Map<Long, Integer>) sqlMapClient.queryForMap("ProductStock.SQL_QUERY_SKU_STOCK",
        skuIds, "SKUID", "STOCK");
  }

  @Override
  public ProductStock queryMaxStockRecord(Long productSkuId) throws SQLException {
    return (ProductStock) sqlMapClient.queryForObject("ProductStock.queryMaxStockRecord",
        productSkuId);
  }
}
