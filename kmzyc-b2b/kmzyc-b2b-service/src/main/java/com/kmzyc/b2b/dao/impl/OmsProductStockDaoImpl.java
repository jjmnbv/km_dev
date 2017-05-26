package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.OmsProductStockDao;
import com.pltfm.app.vobject.ErpOrderLog;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;

@Repository("omsProductStockDao")
public class OmsProductStockDaoImpl implements OmsProductStockDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public List<ProductSku> queryProductSkuByErpProCode(Map<String, Object> condition)
      throws SQLException {
    return (List<ProductSku>) sqlMapClient.queryForList("PRODUCT_STOCK.querySkuByEprProCode",
        condition);
  }

  @Override
  public ProductStock selectByWareAndSkuId(Long warehouseId, Long product_sku_id)
      throws SQLException {
    Map<String, Long> condition = new HashMap<String, Long>();
    condition.put("warehouseId", warehouseId);
    condition.put("productSkuId", product_sku_id);
    return (ProductStock) sqlMapClient.queryForObject(
        "PRODUCT_STOCK.findProductStockByWareAndSkuId", condition);
  }

  @Override
  public Long insertProductStock(ProductStock stock) throws SQLException {
    return (Long) sqlMapClient.insert("PRODUCT_STOCK.ibatorgenerated_insert", stock);
  }

  @Override
  public int updateStockByStockId(Long stockId, Long stockQuantity, String remark)
      throws SQLException {
    Map<String, Object> condition = new HashMap<String, Object>();
    condition.put("stockId", stockId);
    condition.put("stockQuality", stockQuantity);
    condition.put("remark", remark);
    return sqlMapClient.update("PRODUCT_STOCK.updateStockQuantityById", condition);
  }

  @Override
  public void insert(ErpOrderLog record) throws SQLException {
    sqlMapClient.insert("PRODUCT_STOCK.insert_stockLog", record);
  }

  @Override
  public List<ProductSku> queryValidSkuByMap(Map<String, String> condition) throws SQLException {
    return (List<ProductSku>) sqlMapClient.queryForList("PRODUCT_STOCK.queryValidSkuByMap",
        condition);
  }


}
