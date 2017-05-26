package com.kmzyc.b2b.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.vo.ShopCategorys;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.km.framework.persistence.Dao;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductAttr;

@SuppressWarnings("unchecked")
public interface ProductSkuDao extends Dao {

  /**
     *
     */
  public ProductSku findProductBySkuCode(String productSkucode) throws SQLException;

  /**
   * 通过产品主键查询产品sku属性信息
   * 
   * @param productId
   * @return
   * @throws SQLException
   */
  public List<ProductAttr> queryProductAttrByProductId(Long productId) throws SQLException;

  public List<CategoryAttrValue> findByValueId(String attrValue) throws SQLException;

  /**
   * 根据productId查询产品SKU
   * 
   * @param productId
   * @return
   * @throws SQLException
   */
  public List<ProductSku> findPorductSkuByProductId(Long productId) throws SQLException;

  /**
   * 根据店铺Id 查询店铺地下所有的商品所在订单
   */
  public List<OrderItem> findOrderCodeByShopId(Map<String, Object> map) throws SQLException;

  public String queryProductWareHouse(Long productSkuId) throws SQLException;

  public List<ProductSku> queryProductDetailBySku(String productSkuCode) throws SQLException;

  public List<ProductSku> queryProductRankByCondition(Map mapCondition) throws SQLException;

  /**
   * 收藏排行
   */
  public List<ProductSku> findProductFavRankByCondition(Map mapCondition) throws SQLException;

  public List<ProductSku> findProductUpTimeRankByCondition(Map mapCondition) throws SQLException;

  /**
   * 通过供应商ID，查询店铺的类目以及推荐类目
   */
  public List<ShopCategorys> findCategorysByShopId(Map condition, String recommed)
      throws SQLException;

  /**
   * 获取产品价格信息
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public ProductSku queryPriceInfoBySkuId(Long skuId) throws SQLException;

  /**
   * 20150921 mlq add 微信组方需求,根据sku条形码查询商品,是唯一标识的
   * 
   * @param barCode 条形码
   * @return
   * @throws SQLException
   */
  public Long querySkuByBarCode(String barCode) throws SQLException;

  /** 查询商品库存、状态、价格 详情页面专用 */
  public Map<String, Object> querySkuInfoForDetailPage(String skuId) throws SQLException;

  /**
   * 查询返佣率
   * 
   * @param ids
   * @return
   * @throws SQLException
   */
  public Map<String, BigDecimal> queryBatchComRatio(List<Long> ids) throws SQLException;

  /**
   * 查询活动产品信息
   * 
   * @param ids
   * @return
   * @throws SQLException
   */
  public List<CartProduct> queryPromotionProducts(List<Long> ids) throws SQLException;

  public String getSeckillProducts(HashMap<String, String> skucondition) throws SQLException;

  public List<ProductSku> getProductBySkuIds(String skuIds) throws SQLException;

  public Pagination getRecommendProduct(Pagination page, String string) throws SQLException;

  public List<ProductSku> getWindowDatas(String jboxWindowName) throws SQLException;

  /**
   * 根据productRelationType类型及productSkuId组成Map查询套餐或组合
   * 
   * @param typeAndSkuIdMap
   * @param productRelationType
   * @return
   * @throws ServiceException
   */
  public List findProductRelation(Map typeAndSkuIdMap, String productRelationType)
      throws SQLException;


  /**
   * 20151222 add
   * 
   * @param condition
   * @return
   * @throws SQLException
   */
//  public ProductSku querySkuInfoForFanli(Map condition) throws SQLException;

  /**
   * 超级返佣金
   * 
   * @param ratioMap
   * @return
   * @throws SQLException
   */
  public Map<Long, BigDecimal> queryBatchOutComRatio(Map<String, Object> ratioMap)
      throws SQLException;

  /**
   * 查询普通返佣
   * 
   * @param ratioMap
   * @return
   * @throws ServiceException
   */
  public Map<String, BigDecimal> queryBatchOutNorComRatio(Map<String, Object> ratioMap)
      throws SQLException;

  /**
   * 查询商品类目
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  public Map<String, Integer> queryProductCate(List<Long> skuIds) throws SQLException;
  
  /**
   * 查询商家产品
   * 
   * @param page
   * @return
   * @throws ServiceException
   */
  public Pagination queryProductAndShopBySupplierId(Pagination page, String supplierId) throws SQLException;
}
