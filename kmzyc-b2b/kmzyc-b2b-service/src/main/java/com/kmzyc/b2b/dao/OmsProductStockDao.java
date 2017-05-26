package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ErpOrderLog;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;

/**
 * oms系统库存对接 相关dao 操作
 * 
 * @author KM
 * 
 */
public interface OmsProductStockDao {

  /**
   * 20151023 mlq add 查询对接系统产品的编号查询对应的商品
   * 
   * @param erpProCode 对接系统产品编号
   * @param sysCode 系统编号,如:捷科
   * @return
   * @throws SQLException
   */
  public List<ProductSku> queryProductSkuByErpProCode(Map<String, Object> condition)
      throws SQLException;


  /**
   * 按产品编号或者产品sku编码查询sku信息
   * 
   * @return
   * @throws SQLException
   */
  public List<ProductSku> queryValidSkuByMap(Map<String, String> condition) throws SQLException;


  /**
   * 根据仓库Id和skuId查询库存记录
   * 
   * @param warehouseId 仓库Id
   * @param product_sku_id SkuId
   * @return
   * @throws SQLException
   */
  public ProductStock selectByWareAndSkuId(Long warehouseId, Long product_sku_id)
      throws SQLException;


  /**
   * 插入库存记录
   * 
   * @param stock 库存对象
   * @return
   * @throws SQLException
   */
  public Long insertProductStock(ProductStock stock) throws SQLException;

  /**
   * 根据库存Id直接更新指定的库存量
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  public int updateStockByStockId(Long stockId, Long stockQuantity, String remark)
      throws SQLException;

  /**
   * 每推一次则录入一次日志记录
   * 
   * @param record
   * @throws SQLException
   */
  public void insert(ErpOrderLog record) throws SQLException;

}
