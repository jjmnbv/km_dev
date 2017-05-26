package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.ProductsOrderBySale;
import com.kmzyc.b2b.model.ViewProductRelationPurchase;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.DaoException;
import com.km.framework.persistence.Dao;

public interface ProductRelationDao extends Dao {
  ProductRelation findById(Long Id) throws SQLException, DaoException;

  List<CarProduct> findProductRelationDetailAllByRelationId(Long Id) throws SQLException;

  List<ViewProductRelationPurchase> findViewProductRelationPurchaseByRelationid(Integer Id,
      Integer size) throws SQLException;

  List<ViewProductRelationPurchase> findBuyViewProductRelationPurchaseArray(Long[] skuids)
      throws SQLException;

  List<ViewProductRelationPurchase> findBrowseViewProductRelationPurchaseArray(Long[] skuids)
      throws SQLException;

  /**
   * 查询关联商品
   * 
   * @param params
   * @return
   */
  public List<ViewProductRelationPurchase> findRelationProduct(Map<String, Object> params)
      throws SQLException;

  /**
   * 查询前*商品
   * 
   * @param params
   * @return
   */
  public List<ViewProductRelationPurchase> findTopProduct(Map<String, Object> params)
      throws SQLException;

  /**
   * 根据ID查套餐
   * 
   * @param ID
   * @return ProductRelation
   */
  public ProductRelation findByProductRelationId(Long id) throws SQLException;

  /**
   * 查询主SKUID的所有组合产品
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public List<ProductRelation> queryProductRelationBySkuId(Long skuId) throws SQLException;
  
  /**
   * 查询skuid所属的二级物理类目
   * @param skuid
   * @return
   * @throws SQLException
   */
  public String queryCategoryParentId(String skuid) throws SQLException;
  
  /**
   * 按销量降序查询商品信息
   * @param params
   * @return
   * @throws SQLException
   */
  public List<ProductsOrderBySale> findProductOrderBySalequantity(Map<String, Object> params)
      throws SQLException;
}
